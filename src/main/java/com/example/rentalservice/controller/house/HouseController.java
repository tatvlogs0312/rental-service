package com.example.rentalservice.controller.house;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.house.CreateHouseReqDTO;
import com.example.rentalservice.service.house.HouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/house")
@RequiredArgsConstructor
@Slf4j
public class HouseController {

    private final HouseService houseService;

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/create")
    public ResponseEntity<Object> createHouse(@RequestBody CreateHouseReqDTO req) {
        houseService.createHouse(req);
        return ResponseEntity.ok().build();
    }
}
