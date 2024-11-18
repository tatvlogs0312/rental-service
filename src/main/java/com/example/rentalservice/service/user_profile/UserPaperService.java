package com.example.rentalservice.service.user_profile;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.entity.UserProfileUpload;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.exception.ExceptionEnums;
import com.example.rentalservice.model.user_profile.UserPaperReqDTO;
import com.example.rentalservice.model.user_profile.UserPaperResDTO;
import com.example.rentalservice.proxy.StorageServiceProxy;
import com.example.rentalservice.repository.UserProfileUploadRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPaperService {

    private final UserProfileUploadRepository userProfileUploadRepository;

    private final StorageServiceProxy storageServiceProxy;

    //Download giấy tờ
    public byte[] getPaperById(String id) {
        String username = JwtUtils.getUsername();
        Optional<UserProfileUpload> userProfileUploadOtp = userProfileUploadRepository.findFirstByUsernameAndType(username, id);
        if (userProfileUploadOtp.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_PAPER_NOT_VALID);
        }

        String fileUrl = userProfileUploadOtp.get().getUrl();
        return storageServiceProxy.downloadFile(fileUrl);
    }

    public void uploadPaper(UserPaperReqDTO req) {
        String username = JwtUtils.getUsername();

        UserPaperResDTO userPaperResDTO = storageServiceProxy.uploadFile(req.getFile());

        UserProfileUpload userProfileUpload = UserProfileUpload.builder()
                .id(UUID.randomUUID().toString())
                .type(req.getType())
                .username(username)
                .url(userPaperResDTO.getFile())
                .build();
        userProfileUploadRepository.save(userProfileUpload);
    }

    public String uploadAvatar(MultipartFile file) {
        String username = JwtUtils.getUsername();

        UserPaperResDTO userPaperResDTO = storageServiceProxy.uploadFile(file);

        Optional<UserProfileUpload> avatarOtp = userProfileUploadRepository.findFirstByUsernameAndType(username, "AVATAR");
        if (avatarOtp.isPresent()) {
            UserProfileUpload userProfileUpload = avatarOtp.get();
            userProfileUpload.setUrl(userPaperResDTO.getFile());
        } else {
            UserProfileUpload userProfileUpload = new UserProfileUpload();
            userProfileUpload.setId(UUID.randomUUID().toString());
            userProfileUpload.setUsername(username);
            userProfileUpload.setType("AVATAR");
            userProfileUpload.setUrl(userPaperResDTO.getFile());
        }

        return userPaperResDTO.getFile();
    }
}
