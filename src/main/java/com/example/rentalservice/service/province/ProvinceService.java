package com.example.rentalservice.service.province;

import com.example.rentalservice.model.provinces.BaseDistrictResDTO;
import com.example.rentalservice.model.provinces.BaseProvinceResDTO;
import com.example.rentalservice.model.provinces.BaseWardResDTO;
import com.example.rentalservice.model.provinces.DistrictResDTO;
import com.example.rentalservice.model.provinces.ProvinceResDTO;
import com.example.rentalservice.model.provinces.WardResDTO;
import com.example.rentalservice.proxy.ProvinceProxy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProvinceService {

    private final ProvinceProxy provinceProxy;

    public List<ProvinceResDTO> getProvince() {
        BaseProvinceResDTO provinceResDTO = provinceProxy.getProvince();
        if (!CollectionUtils.isEmpty(provinceResDTO.getResults())) {
            return provinceResDTO.getResults().stream().sorted(Comparator.comparing(ProvinceResDTO::getProvinceId)).toList();
        }
        return new ArrayList<>();
    }

    public List<DistrictResDTO> getDistrictsByProvinceId(String provinceId) {
        BaseDistrictResDTO baseDistrictResDTO = provinceProxy.getDistrict(provinceId);
        if (!CollectionUtils.isEmpty(baseDistrictResDTO.getResults())) {
            return baseDistrictResDTO.getResults().stream().sorted(Comparator.comparing(DistrictResDTO::getDistrictId)).toList();
        }
        return new ArrayList<>();
    }

    public List<WardResDTO> getWardByDistrictId(String districtId) {
        BaseWardResDTO baseWardResDTO = provinceProxy.getWard(districtId);
        if (!CollectionUtils.isEmpty(baseWardResDTO.getResults())) {
            return baseWardResDTO.getResults().stream().sorted(Comparator.comparing(WardResDTO::getWardId)).toList();
        }
        return new ArrayList<>();
    }
}