package com.example.rentalservice.service.house;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.entity.House;
import com.example.rentalservice.model.house.CreateHouseReqDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseService {

    private final HouseRepository houseRepository;

    public void createHouse (CreateHouseReqDTO req) {
        House house = new House();
        house.setId(UUID.randomUUID().toString());
        house.setHouseName(req.getHouseName());
        house.setPositionDetail(req.getPositionDetail());
        house.setWard(req.getWard());
        house.setDistrict(req.getDistrict());
        house.setProvince(req.getProvince());
        house.setLessor(JwtUtils.getUsername());
        houseRepository.save(house);
    }

    public PagingResponse<House> search(Pageable pageable) {
        Page<House> housePage = houseRepository.findAllByLessor(JwtUtils.getUsername(), pageable);
        return new PagingResponse<>(housePage);
    }
}
