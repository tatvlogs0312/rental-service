package com.example.rentalservice.service.house;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.entity.House;
import com.example.rentalservice.model.house.CreateHouseReqDTO;
import com.example.rentalservice.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseService {

    private final HouseRepository houseRepository;

    public void createHouse (CreateHouseReqDTO req) {
        House house = new House();
        house.setHouseName(req.getHouseName());
        house.setPositionDetail(req.getPositionDetail());
        house.setWard(req.getWard());
        house.setDistrict(req.getDistrict());
        house.setProvince(req.getProvince());
        house.setLessor(JwtUtils.getUsername());
        houseRepository.save(house);
    }
}
