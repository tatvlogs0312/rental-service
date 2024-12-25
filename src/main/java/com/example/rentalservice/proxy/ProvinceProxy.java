package com.example.rentalservice.proxy;

import com.example.rentalservice.model.provinces.BaseDistrictResDTO;
import com.example.rentalservice.model.provinces.BaseProvinceResDTO;
import com.example.rentalservice.model.provinces.BaseWardResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProvinceProxy {

    private final BaseProxy baseProxy;

    @Value("${custom.properties.province-url}")
    private String PROVINCE_URL;

    public BaseProvinceResDTO getProvince() {
        try {
            String url = PROVINCE_URL + "/api/v2/province/";
            return (BaseProvinceResDTO) baseProxy.get(url, BaseProvinceResDTO.class);
        } catch (Exception e) {
            log.info("ProvinceProxy getProvince - Exception: {}", e.getMessage());
        }
        return new BaseProvinceResDTO();
    }

    public BaseDistrictResDTO getDistrict(String provinceId) {
        try {
            String url = PROVINCE_URL + "/api/v2/province/district/" + provinceId;
            return (BaseDistrictResDTO) baseProxy.get(url, BaseDistrictResDTO.class);
        } catch (Exception e) {
            log.info("ProvinceProxy getDistrict - Exception: {}", e.getMessage());
        }
        return new BaseDistrictResDTO();
    }

    public BaseWardResDTO getWard(String districtId) {
        try {
            String url = PROVINCE_URL + "/api/v2/province/ward/" + districtId;
            return (BaseWardResDTO) baseProxy.get(url, BaseWardResDTO.class);
        } catch (Exception e) {
            log.info("ProvinceProxy getWard - Exception: {}", e.getMessage());
        }
        return new BaseWardResDTO();
    }
}
