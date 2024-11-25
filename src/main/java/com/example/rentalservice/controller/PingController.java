package com.example.rentalservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
@Slf4j
public class PingController {

    @GetMapping
    public ResponseEntity<Object> getPing() {
        return new ResponseEntity<>("OK - GET", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> postPing() {
        return new ResponseEntity<>("OK - POST", HttpStatus.OK);
    }
}
