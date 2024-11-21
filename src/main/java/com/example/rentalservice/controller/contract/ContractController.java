package com.example.rentalservice.controller.contract;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.contract.ContractSignReqDTO;
import com.example.rentalservice.model.contract.CreateContractReqDTO;
import com.example.rentalservice.service.contract.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
@Slf4j
public class ContractController {

    private final ContractService contractService;

    @Secured
    @PostMapping("/create")
    public ResponseEntity<Object> createContract(@RequestBody CreateContractReqDTO req) {
        contractService.createContract(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @PostMapping("/send-user/{id}")
    public ResponseEntity<Object> sendUser(@PathVariable String id) {
        contractService.sendUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @GetMapping("/get-otp/{id}")
    public ResponseEntity<Object> getContractOtp(@PathVariable String id) {
        contractService.getContractOtp(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @PostMapping("/sign")
    public ResponseEntity<Object> signContractOtp(@RequestBody ContractSignReqDTO req) {
        contractService.signContract(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/search-for-lessor")
    public ResponseEntity<Object> searchForLessor(@RequestParam(required = false) String status,
                                                  @RequestParam int page,
                                                  @RequestParam int size) {
        return new ResponseEntity<>(contractService.searchForLessor(status, page, size), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.TENANT})
    @GetMapping("/search-for-tenant")
    public ResponseEntity<Object> searchForTenant(@RequestParam(required = false) String status,
                                                  @RequestParam int page,
                                                  @RequestParam int size) {
        return new ResponseEntity<>(contractService.searchForTenant(status, page, size), HttpStatus.OK);
    }
}