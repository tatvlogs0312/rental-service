package com.example.rentalservice.controller.malfunction_warning;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.warning.WarningCreateReqDTO;
import com.example.rentalservice.service.malfunction_warning.MalfunctionWarningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/malfunction-warning")
@RequiredArgsConstructor
@Slf4j
public class MalfunctionWarningController {

    private final MalfunctionWarningService service;

    @Secured(roles = {RoleEnum.TENANT})
    @PostMapping("/create")
    public ResponseEntity<Object> create(WarningCreateReqDTO req) {
        service.create(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/search-for-lessor")
    public ResponseEntity<Object> searchForLessor(@RequestParam(required = false) String status,
                                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size) {
        return new ResponseEntity<>(service.searchForLessor(status, page, size), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.TENANT})
    @GetMapping("/search-for-tenant")
    public ResponseEntity<Object> searchForTenant(@RequestParam(required = false) String status,
                                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size) {
        return new ResponseEntity<>(service.searchForTenant(status, page, size), HttpStatus.OK);
    }
}
