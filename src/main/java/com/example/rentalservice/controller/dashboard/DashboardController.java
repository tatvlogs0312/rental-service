package com.example.rentalservice.controller.dashboard;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/lessor")
    public ResponseEntity<Object> getLessorDashboard() {
        return new ResponseEntity<>(dashboardService.getDashboard(), HttpStatus.OK);
    }
}
