package com.example.rentalservice.controller.bill;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.bill.CreateBillReqDTO;
import com.example.rentalservice.model.search.req.BillSearchReqDTO;
import com.example.rentalservice.service.bill.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Secured
    @PostMapping("/send-user/{id}")
    public ResponseEntity<Object> sendUser(@PathVariable String id) {
        billService.sendUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @PostMapping("/update-payment/{id}")
    public ResponseEntity<Object> updatePayment(@PathVariable String id) {
        billService.updatePayment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> viewDetail(@PathVariable String id) {
        return new ResponseEntity<>(billService.viewDetail(id), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/search-for-lessor")
    public ResponseEntity<Object> searchForLessor(@RequestBody BillSearchReqDTO req) {
        return new ResponseEntity<>(billService.searchForLessor(req), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.TENANT})
    @PostMapping("/search-for-tenant")
    public ResponseEntity<Object> searchForTenant(@RequestBody BillSearchReqDTO req) {
        return new ResponseEntity<>(billService.searchForTenant(req), HttpStatus.OK);
    }

    @Secured
    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBill(@PathVariable String id) {
        billService.deleteBill(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
