package com.example.rentalservice.controller.bill;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.model.bill.CreateBillReqDTO;
import com.example.rentalservice.service.bill.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
@Slf4j
public class BillController {

    private final BillService billService;

    @Secured
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody CreateBillReqDTO req) {
        billService.createContract(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
