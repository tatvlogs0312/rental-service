package com.example.rentalservice.service.province;

import com.example.rentalservice.model.provinces.BaseProvinceResDTO;
import com.example.rentalservice.model.provinces.ProvinceResDTO;
import com.example.rentalservice.repository.custom.ProvinceCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProvinceServiceV2 {

    private final ProvinceCustomRepository repository;

    public BaseProvinceResDTO getProvince() {
        List<ProvinceResDTO> provinceResDTOS = repository.getProvince();

        BaseProvinceResDTO baseProvinceResDTO = new BaseProvinceResDTO();
        baseProvinceResDTO.setResults(provinceResDTOS);
        return baseProvinceResDTO;
    }
}
