package com.example.rentalservice.controller.statistical;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.service.statistical.StatisticalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistical")
@RequiredArgsConstructor
@Slf4j
public class StatisticalController {

    private final StatisticalService service;

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/room")
    public ResponseEntity<Object> getRoom() {
        return new ResponseEntity<>(service.getRoomStatistical(), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/bill")
    public ResponseEntity<Object> getBill() {
        return new ResponseEntity<>(service.getBillStatistical(), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/bill-year")
    public ResponseEntity<Object> getBillYear(@RequestParam Integer year) {
        return new ResponseEntity<>(service.getBillYear(year), HttpStatus.OK);
    }
}
