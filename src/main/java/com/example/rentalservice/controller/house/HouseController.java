package com.example.rentalservice.controller.house;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.house.CreateHouseReqDTO;
import com.example.rentalservice.service.house.HouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/create/v2")
    public ResponseEntity<Object> createHouseV2(CreateHouseReqDTO req) {
        houseService.createHouseV2(req);
        return ResponseEntity.ok().build();
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam Integer page, @RequestParam Integer size) {
        return new ResponseEntity<>(houseService.search(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        log.info("call house delete");
        houseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
