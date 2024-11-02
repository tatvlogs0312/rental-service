package com.example.rentalservice.common;

import com.example.rentalservice.model.search.req.PostSearchReqDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class InputUtils {

    public static void handleInputSearchPost(PostSearchReqDTO req) {
        if (StringUtils.isBlank(req.getDetail())) {
            req.setDetail("");
        }

        if (StringUtils.isBlank(req.getProvince())) {
            req.setProvince("");
        }

        if (StringUtils.isBlank(req.getDistrict())) {
            req.setDistrict("");
        }

        if (StringUtils.isBlank(req.getWard())) {
            req.setWard("");
        }

        if (Objects.isNull(req.getPriceFrom())) {
            req.setPriceFrom(0L);
        }

        if (Objects.isNull(req.getPriceTo()) || Objects.equals(req.getPriceTo(), 0L)) {
            req.setPriceTo(100000000L);
        }
    }
}
