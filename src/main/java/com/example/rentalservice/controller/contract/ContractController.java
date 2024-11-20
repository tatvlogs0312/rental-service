package com.example.rentalservice.controller.contract;

import com.example.rentalservice.aop.Secured;
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
    @GetMapping("/sign")
    public ResponseEntity<Object> signContractOtp(@RequestBody ContractSignReqDTO req) {
        contractService.signContract(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
