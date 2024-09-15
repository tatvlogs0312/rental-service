package com.example.rentalservice.proxy;

import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.face_matching.FaceMatchingReqDTO;
import com.example.rentalservice.model.face_matching.FaceMatchingResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class EkycServiceProxy extends BaseProxy {

    @Value("${custom.properties.ekyc-service-url}")
    private String ekycServiceUrl;

    public FaceMatchingResDTO compareFace(FaceMatchingReqDTO req) {
        try {
            String url = ekycServiceUrl + "/face-matching";

            MultiValueMap<Object, Object> params = new LinkedMultiValueMap<>();
            params.add("face1", new FileSystemResource(req.getFace1()));
            params.add("face2", new FileSystemResource(req.getFace2()));
            params.add("score", req.getScore());

            return this.postMultiValueMap(url, initHeaderFormDataV2(), params, FaceMatchingResDTO.class);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
