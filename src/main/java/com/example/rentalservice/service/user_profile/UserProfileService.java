package com.example.rentalservice.service.user_profile;

import com.example.rentalservice.common.FileUtils;
import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.common.RsaUtils;
import com.example.rentalservice.constants.Constants;
import com.example.rentalservice.entity.UserDevice;
import com.example.rentalservice.entity.UserNotification;
import com.example.rentalservice.entity.UserProfile;
import com.example.rentalservice.entity.UserProfileUpload;
import com.example.rentalservice.enums.PaperEnum;
import com.example.rentalservice.model.auth.login.LoginFaceReqDTO;
import com.example.rentalservice.model.face_matching.FaceMatchingReqDTO;
import com.example.rentalservice.model.face_matching.FaceMatchingResDTO;
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
import com.example.rentalservice.model.user_profile.CompleteInformationReqDTO;
import com.example.rentalservice.model.user_profile.UserIdentityDTO;
import com.example.rentalservice.model.user_profile.UserProfileDTO;
import com.example.rentalservice.proxy.KeyCloakProxy;
import com.example.rentalservice.redis.KeycloakCacheService;
import com.example.rentalservice.redis.RedisService;
import com.example.rentalservice.repository.UserProfileRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.rentalservice.service.common.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        userProfile.setRole(req.getRole());
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

        KeyCloakUserResDTO keyCloakUserResDTO = keyCloakUserResDTOs.get(0);
        KeycloakUpdatePasswordReqDTO keycloakUpdatePasswordReqDTO = KeycloakUpdatePasswordReqDTO.builder()
                .temporary(false)
                .type(Constants.PASSWORD)
                .value(req.getNewPassword())
                .build();
        keyCloakProxy.updatePassword(keycloakUpdatePasswordReqDTO, keyCloakUserResDTO.getId(),
                keycloakCacheService.getToken());
    }

    //Hoàn thiện thông tin cá nhân
    public void completeInformation(CompleteInformationReqDTO req) {
        String username = JwtUtils.getUsername();
        Optional<UserProfile> userOptional = userProfileRepository.findFirstByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ApplicationException("Người dùng không tồn tại");
        }

        UserProfile userProfile = userOptional.get();
        userProfile.setFirstName(req.getFirstName());
        userProfile.setLastName(req.getLastName());
        userProfile.setEmail(req.getEmail());
        userProfile.setIdentityNumber(req.getIdentityNumber());
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

        Optional<UserProfileUpload> avatarOtp = userProfileUploadRepository.findFirstByUsernameAndType(username, "AVATAR");
        if (avatarOtp.isPresent()) {
            userProfileDTO.setAvatar(avatarOtp.get().getUrl());
        } else {
            userProfileDTO.setAvatar("edf593c8-9666-4a55-b25e-5fa833bb10d2.png");
        }

        return userProfileDTO;
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
        return dataService.getUserByUsername(user);
    }

    public UserProfile getUser(String user, String role) {
        Optional<UserProfile> userProfileOTP = userProfileRepository.findFirstByUsernameAndRole(user, role);
        if (userProfileOTP.isEmpty()) {
            throw new ApplicationException("Người dùng không tồn tại");
        }

        return userProfileOTP.get();
    }
}
