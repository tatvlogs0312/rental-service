package com.example.rentalservice.repository.custom;

import com.example.rentalservice.common.RepositoryUtils;
import com.example.rentalservice.model.provinces.ProvinceResDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProvinceCustomRepository {

    private final EntityManager entityManager;

    public List<ProvinceResDTO> getProvince() {
        String sql = "select code, name, type from province order by code";

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> result = (List<Object[]>) query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            List<ProvinceResDTO> provinceResDTOS = new ArrayList<>();
            result.forEach(x -> {
                ProvinceResDTO provinceResDTO = ProvinceResDTO
                        .builder()
                        .provinceId(RepositoryUtils.setValueForField(String.class, x[0]))
                        .provinceName(RepositoryUtils.setValueForField(String.class, x[1]))
                        .provinceType(RepositoryUtils.setValueForField(String.class, x[2]))
                        .build();

                provinceResDTOS.add(provinceResDTO);
            });

            return provinceResDTOS;
        }

        return new ArrayList<>();
    }
}
