package com.example.rentalservice.proxy;

import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.user_profile.UserPaperResDTO;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageServiceProxy extends BaseProxy {

    @Value("${custom.properties.storage-service-url}")
    private String storageServiceUrl;

    public byte[] downloadFile(String fileName) {
        try {
            String url = storageServiceUrl + "/file/download/" + fileName;
            return this.get(url, initHeader(), byte[].class);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public UserPaperResDTO uploadFile(MultipartFile file) {
        try {
            String url = storageServiceUrl + "/file/upload";

            Map<Object, Object> maps = new HashMap<>();
            maps.put("file", file.getResource());

            return this.postMap(url, initHeaderFormData(), maps, UserPaperResDTO.class);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
