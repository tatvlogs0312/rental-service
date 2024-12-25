package com.example.rentalservice.service.user_profile;

import com.example.rentalservice.common.*;
import com.example.rentalservice.constants.Constants;
import com.example.rentalservice.entity.UserDevice;
import com.example.rentalservice.entity.UserNotification;
import com.example.rentalservice.entity.UserProfile;
import com.example.rentalservice.entity.UserProfileUpload;
import com.example.rentalservice.enums.PaperEnum;
import com.example.rentalservice.model.MailDTO;
import com.example.rentalservice.model.auth.forgot_password.ForgotOtpReqDTO;
import com.example.rentalservice.model.auth.forgot_password.ForgotPasswordReqDTO;
import com.example.rentalservice.model.auth.login.LoginFaceReqDTO;
import com.example.rentalservice.model.face_matching.FaceMatchingReqDTO;
import com.example.rentalservice.model.face_matching.FaceMatchingResDTO;
import com.example.rentalservice.model.otp.OtpDTO;
import com.example.rentalservice.model.user_profile.*;
import com.example.rentalservice.proxy.EkycServiceProxy;
import com.example.rentalservice.proxy.StorageServiceProxy;
import com.example.rentalservice.repository.UserDeviceRepository;
import com.example.rentalservice.repository.UserNotificationRepository;
import com.example.rentalservice.repository.UserProfileUploadRepository;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.auth.login.LoginReqDTO;
import com.example.rentalservice.model.auth.login.LoginResDTO;
import com.example.rentalservice.model.auth.register.RegisterReqDTO;
import com.example.rentalservice.model.auth.update_password.UpdatePasswordReqDTO;
import com.example.rentalservice.model.key_cloak.KeyCloakCredentialsDTO;
import com.example.rentalservice.model.key_cloak.KeyCloakNewUserReqDTO;
import com.example.rentalservice.model.key_cloak.KeyCloakTokenReqDTO;
import com.example.rentalservice.model.key_cloak.KeyCloakTokenResDTO;
import com.example.rentalservice.model.key_cloak.KeyCloakUserResDTO;
import com.example.rentalservice.model.key_cloak.KeycloakUpdatePasswordReqDTO;
import com.example.rentalservice.proxy.KeyCloakProxy;
import com.example.rentalservice.redis.KeycloakCacheService;
import com.example.rentalservice.redis.RedisService;
import com.example.rentalservice.repository.UserProfileRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import com.example.rentalservice.service.common.DataService;
import com.example.rentalservice.service.common.MailService;
import com.example.rentalservice.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserDeviceRepository userDeviceRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserProfileUploadRepository userProfileUploadRepository;
    private final UserProfileRepository userProfileRepository;
    private final KeyCloakProxy keyCloakProxy;
    private final KeycloakCacheService keycloakCacheService;
    private final RedisService redisService;
    private final MailService mailService;
    private final RsaUtils rsaUtils;
    private final StorageServiceProxy storageServiceProxy;
    private final EkycServiceProxy ekycServiceProxy;
    private final DataService dataService;

    //Đăng nhập hệ thống
    public LoginResDTO login(LoginReqDTO req) {
        KeyCloakTokenReqDTO keyCloakTokenReqDTO = KeyCloakTokenReqDTO.builder()
                .username(req.getUsername()).password(req.getPassword()).build();
        KeyCloakTokenResDTO keyCloakTokenResDTO = null;
        try {
            keyCloakTokenResDTO = keyCloakProxy.loginKeyCloak(keyCloakTokenReqDTO);
        } catch (Exception e) {
            //handle login error
            handleLoginError(req);
        }

        assert keyCloakTokenResDTO != null;

        LoginResDTO res = new LoginResDTO();
        res.setToken(keyCloakTokenResDTO.getAccessToken());
        Optional<UserProfile> userOptional = userProfileRepository.findFirstByUsername(req.getUsername());
        userOptional.ifPresent(user -> {
            res.setRole(user.getRole());
            res.setStatus(user.getStatus());
            res.setName(user.getFirstName() + " " + user.getLastName());
            if (StringUtils.isNotBlank(user.getAvatar())) {
                res.setAvatar(user.getAvatar());
            } else {
                res.setAvatar("edf593c8-9666-4a55-b25e-5fa833bb10d2.png");
            }

            redisService.setValue(req.getUsername() + "_role", user.getRole());
        });

        Optional<UserDevice> userDeviceOtp = userDeviceRepository
                .findFirstByUsernameAndDevice(req.getUsername(), req.getDevice());
        if (userDeviceOtp.isEmpty()) {
            UserDevice userDevice = new UserDevice();
            userDevice.setId(UUID.randomUUID().toString());
            userDevice.setUsername(req.getUsername());
            userDevice.setDevice(req.getDevice());
            userDeviceRepository.save(userDevice);
        }

        return res;
    }

    //Login bằng khuôn mặt
    public LoginResDTO loginFace(LoginFaceReqDTO req) throws Exception {
        Optional<UserProfile> userOptional = userProfileRepository.findFirstByUsername(req.getUsername());
        if (userOptional.isEmpty()) {
            throw new ApplicationException("Tài khoản không tồn tại");
        }

        UserProfile userProfile = userOptional.get();

        Optional<UserProfileUpload> userProfileUploadOtp = userProfileUploadRepository
                .findFirstByUsernameAndType(req.getUsername(), PaperEnum.SELFIE.name());
        if (userProfileUploadOtp.isEmpty()) {
            throw new ApplicationException("Tài khoản chưa đăng kí tính năng đăng nhập bằng khuôn mặt");
        }

        String fileUrl = userProfileUploadOtp.get().getUrl();
        byte[] faceOfUser = storageServiceProxy.downloadFile(fileUrl);
        this.checkFace(req, faceOfUser);

        String password = rsaUtils.decryptData(userProfile.getPassword());
        KeyCloakTokenReqDTO keyCloakTokenReqDTO = KeyCloakTokenReqDTO.builder()
                .username(req.getUsername())
                .password(password)
                .build();
        KeyCloakTokenResDTO keyCloakTokenResDTO = keyCloakProxy.loginKeyCloak(keyCloakTokenReqDTO);

        LoginResDTO res = new LoginResDTO();
        res.setToken(keyCloakTokenResDTO.getAccessToken());
        res.setRole(userProfile.getRole());

        redisService.setValue(req.getUsername() + "_role", userProfile.getRole());

        return res;
    }

    private void checkFace(LoginFaceReqDTO req, byte[] faceOfUser) {
        File face1 = FileUtils.convertFileV2(req.getFace());
        File face2 = FileUtils.convertFileV2(faceOfUser);

        try {
            FaceMatchingReqDTO faceMatchingReqDTO = FaceMatchingReqDTO.builder()
                    .face1(face1)
                    .face2(face2)
                    .score(0.9)
                    .build();
            FaceMatchingResDTO faceMatchingResDTO = ekycServiceProxy.compareFace(faceMatchingReqDTO);
            if (!Boolean.TRUE.equals(faceMatchingResDTO.getVerified())) {
                throw new ApplicationException("Xác thực khuôn mặt không thành công");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            FileUtils.cleanFileNotNull(face1);
            FileUtils.cleanFileNotNull(face2);
        }
    }

    //Đăng kí tài khoản
    public String register(RegisterReqDTO req) {
        Optional<UserProfile> userOptional = userProfileRepository.findFirstByUsername(
                req.getUsername());
        if (userOptional.isPresent()) {
            throw new ApplicationException("Người dùng đã tồn tại");
        }

//        checkExistEmail(req.getEmail());
//
//        checkExistPhoneNumber(req.getPhoneNumber());

        //Tạo user trên keycloak
        KeyCloakNewUserReqDTO keyCloakNewUserReqDTO = KeyCloakNewUserReqDTO.builder()
                .enabled(true)
                .username(req.getUsername())
                .credentials(List.of(KeyCloakCredentialsDTO.builder()
                        .temporary(false)
                        .type(Constants.PASSWORD)
                        .value(req.getPassword())
                        .build()))
                .build();
        keyCloakProxy.createUser(keyCloakNewUserReqDTO, keycloakCacheService.getToken());

        String id = UUID.randomUUID().toString();

        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        userProfile.setUsername(req.getUsername());
        userProfile.setPhoneNumber(req.getPhoneNumber());
//        userProfile.setEmail(req.getEmail());
//        userProfile.setRole(req.getRole());
        userProfile.setStatus("PENDING");
        userProfile.setPassword(rsaUtils.encryptData(req.getPassword()));
        userProfileRepository.save(userProfile);

        return id;
    }

    //Cập nhật mật khẩu
    public void updatePassword(UpdatePasswordReqDTO req) {
        String username = JwtUtils.getUsername();
        Optional<UserProfile> userOptional = userProfileRepository.findFirstByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ApplicationException("Người dùng không tồn tại");
        }

        List<KeyCloakUserResDTO> keyCloakUserResDTOs = keyCloakProxy.searchByUsername(username,
                keycloakCacheService.getToken());
        if (CollectionUtils.isEmpty(keyCloakUserResDTOs)) {
            throw new ApplicationException("Người dùng không tồn tại");
        }

        UserProfile userProfile = userOptional.get();
        String oldPass = rsaUtils.decryptData(userProfile.getPassword());
        if (!Objects.equals(req.getOldPassword(), oldPass)) {
            throw new ApplicationException("Mật khẩu cũ không chính xác");
        }

        if (Objects.equals(req.getNewPassword(), oldPass)) {
            throw new ApplicationException("Trùng với mật khẩu cũ");
        }

        KeyCloakUserResDTO keyCloakUserResDTO = keyCloakUserResDTOs.get(0);
        KeycloakUpdatePasswordReqDTO keycloakUpdatePasswordReqDTO = KeycloakUpdatePasswordReqDTO.builder()
                .temporary(false)
                .type(Constants.PASSWORD)
                .value(req.getNewPassword())
                .build();
        keyCloakProxy.updatePassword(keycloakUpdatePasswordReqDTO, keyCloakUserResDTO.getId(),
                keycloakCacheService.getToken());

        userProfile.setPassword(rsaUtils.encryptData(req.getNewPassword()));
        userProfileRepository.save(userProfile);
    }

    //Hoàn thiện thông tin cá nhân
    public void completeInformation(CompleteInformationReqDTO req) {
        String username = JwtUtils.getUsername();
        Optional<UserProfile> userOptional = userProfileRepository.findFirstByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ApplicationException("Người dùng không tồn tại");
        }

        checkExistEmail(req.getEmail());

        checkExistPhoneNumber(req.getPhoneNumber());

        UserProfile userProfile = userOptional.get();
        userProfile.setFirstName(req.getFirstName());
        userProfile.setLastName(req.getLastName());
        userProfile.setEmail(req.getEmail());
        userProfile.setPhoneNumber(req.getPhoneNumber());
        userProfile.setStatus("ACTIVE");
        userProfileRepository.save(userProfile);
    }

    //Lấy thông tin cá nhân
    public UserProfileDTO getInformation() {
        String username = JwtUtils.getUsername();
        Optional<UserProfile> userOptional = userProfileRepository.findFirstByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ApplicationException("Người dùng không tồn tại");
        }

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        BeanUtils.copyProperties(userOptional.get(), userProfileDTO);

        List<UserProfileUpload> uploads = userProfileUploadRepository.findAllByUsername(username);
        if (!CollectionUtils.isEmpty(uploads)) {
            userProfileDTO.setIdentities(uploads.stream()
                    .map(x -> UserIdentityDTO.builder()
                            .id(x.getId())
                            .type(x.getType())
                            .url(x.getUrl())
                            .build())
                    .toList());
        }

        if (StringUtils.isNotBlank(userOptional.get().getAvatar())) {
            userProfileDTO.setAvatar(userOptional.get().getAvatar());
        } else {
            userProfileDTO.setAvatar("edf593c8-9666-4a55-b25e-5fa833bb10d2.png");
        }

        return userProfileDTO;
    }

    public void updateInformation(UpdateInformationReqDTO req) {
        UserProfile userProfile = dataService.getUserByUsername(JwtUtils.getUsername());

        if (StringUtils.isNotBlank(req.getEmail())) {
            Validator.emailValidator(req.getEmail());
            userProfile.setEmail(req.getEmail());
        }

        if (StringUtils.isNotBlank(req.getPhoneNumber())) {
            Validator.phoneNumberValidator(req.getPhoneNumber());
            userProfile.setPhoneNumber(req.getPhoneNumber());
        }

        if (StringUtils.isNotBlank(req.getIdentityNumber())) {
            Validator.identityNumberValidator(req.getIdentityNumber());
            userProfile.setIdentityNumber(req.getIdentityNumber());
        }

        if (StringUtils.isNotBlank(req.getBirthDate())) {
            userProfile.setBirthdate(LocalDate.parse(req.getBirthDate()));
        }

        if (StringUtils.isNotBlank(req.getGender())) {
            Validator.genderValidator(req.getGender());
            userProfile.setGender(req.getGender());
        }

        userProfileRepository.save(userProfile);
    }

    private void handleLoginError(LoginReqDTO req) {
        String token = keycloakCacheService.getToken();
        var users = keyCloakProxy.searchByUsername(req.getUsername(), token);
        if (CollectionUtils.isEmpty(users)) {
            throw new ApplicationException("Tài khoản không tồn tại");
        } else {
            throw new ApplicationException("Mật khẩu không chính xác");
        }
    }

    public UserProfile getUser(String user) {
        UserProfile userProfile = dataService.getUserByUsername(user);
        if (StringUtils.isBlank(userProfile.getEmail())) {
            throw new ApplicationException("Người dùng chưa đăng ký email");
        }
        return userProfile;
    }

    public UserProfile getUserV2(String keyword, String role) {
        Optional<UserProfile> userProfile = userProfileRepository.findByKeyword(keyword, role);
        if (userProfile.isEmpty()) {
            throw new ApplicationException("Người dùng không tồn tại");
        }
        return userProfile.get();
    }

    public UserProfile getUser(String user, String role) {
        Optional<UserProfile> userProfileOTP = userProfileRepository.findFirstByUsernameAndRole(user, role);
        if (userProfileOTP.isEmpty()) {
            throw new ApplicationException("Người dùng không tồn tại");
        }

        return userProfileOTP.get();
    }

    public void forgotPassword(ForgotPasswordReqDTO req) {
        String username = req.getUsername();
        Optional<UserProfile> userOptional = userProfileRepository.findFirstByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ApplicationException("Người dùng không tồn tại");
        }

        List<KeyCloakUserResDTO> keyCloakUserResDTOs = keyCloakProxy.searchByUsername(username,
                keycloakCacheService.getToken());
        if (CollectionUtils.isEmpty(keyCloakUserResDTOs)) {
            throw new ApplicationException("Người dùng không tồn tại");
        }

        UserProfile userProfile = userOptional.get();
        KeyCloakUserResDTO keyCloakUserResDTO = keyCloakUserResDTOs.get(0);
        KeycloakUpdatePasswordReqDTO keycloakUpdatePasswordReqDTO = KeycloakUpdatePasswordReqDTO.builder()
                .temporary(false)
                .type(Constants.PASSWORD)
                .value(req.getNewPassword())
                .build();
        keyCloakProxy.updatePassword(keycloakUpdatePasswordReqDTO, keyCloakUserResDTO.getId(),
                keycloakCacheService.getToken());

        userProfile.setPassword(rsaUtils.encryptData(req.getNewPassword()));
        userProfileRepository.save(userProfile);
    }

    public void requestOTP(ForgotOtpReqDTO req) {
        String username = req.getUsername();
        UserProfile userProfile = dataService.getUserByUsername(username);

        String otp = Utils.generateOTP(6);

        OtpDTO otpDTO = OtpDTO.builder()
                .username(username)
                .otp(otp)
                .numberOtp(0)
                .build();
        String keyOTPCache = "CHANGE-PASSWORD-OTP-" + username;
        redisService.setValue(keyOTPCache, JsonUtils.toJson(otpDTO), 300L);

        MailDTO mailDTO = new MailDTO();
        mailDTO.setMailTo(List.of(userProfile.getEmail()));
        mailDTO.setMailCc(new ArrayList<>());
        mailDTO.setSubject("Mã OTP xác nhận tài khoản");
        mailDTO.setContent(String.format("""
                    <p>Mã OTP của bạn là <b style="color: blue">%s</b></p>
                    <p>Mã OTP này có hiệu lực trong vòng 5 phút.</p>
                    <p>Vui lòng không tiết lộ cho người lạ.</p>
                """, otp));
        mailService.sendMailHtml(mailDTO);
    }

    public void verifyOtp(ForgotOtpReqDTO req) {
        String username = req.getUsername();
        String otpCache = redisService.getValue("CHANGE-PASSWORD-OTP-" + username);
        if (StringUtils.isNotBlank(otpCache)) {
            OtpDTO otpDTO = JsonUtils.fromJson(otpCache, OtpDTO.class);
            assert otpDTO != null;
            if (otpDTO.getNumberOtp() >= 5) {
                throw new ApplicationException("Đã nhập sai 5 lần vui lòng lấy mã otp mới");
            }

            if (!StringUtils.equals(otpDTO.getOtp(), req.getOtp())) {
                otpDTO.setNumberOtp(otpDTO.getNumberOtp() + 1);
                String keyOTPCache = "CHANGE-PASSWORD-OTP-" + username;
                redisService.setValue(keyOTPCache, JsonUtils.toJson(otpDTO), 300L);
                throw new ApplicationException("OTP không chính xác vui lòng nhập lại");
            }
        } else {
            throw new ApplicationException("OTP đã hết hạn vui lòng lấy mã OTP mới");
        }
    }

    public String uploadAvatar(MultipartFile file) {
        String username = JwtUtils.getUsername();
        UserProfile userProfile = dataService.getUserByUsername(username);

        UserPaperResDTO userPaperResDTO = storageServiceProxy.uploadFile(file);
        userProfile.setAvatar(userPaperResDTO.getFile());

        userProfileRepository.save(userProfile);

        return userPaperResDTO.getFile();
    }

    public void checkExistEmail(String email) {
        if (StringUtils.isNotBlank(email)) {
            Boolean isExist = userProfileRepository.existsAllByEmail(email);
            if (BooleanUtils.isTrue(isExist)) {
                throw new ApplicationException("Email đã được đăng ký, vui lòng điền email khác");
            }
        }
    }

    public void checkExistPhoneNumber(String phoneNumber) {
        if (StringUtils.isNotBlank(phoneNumber)) {
            Boolean isExist = userProfileRepository.existsAllByPhoneNumber(phoneNumber);
            if (BooleanUtils.isTrue(isExist)) {
                throw new ApplicationException("Số điện thoại đã được đăng ký, vui lòng điền số điện thoại khác");
            }
        }
    }
}
