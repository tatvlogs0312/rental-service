package com.example.rentalservice.controller.common;

import com.example.rentalservice.service.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    private final FcmService fcmService;

    @PostMapping("/send-notification")
    public ResponseEntity<Object> sendNotification() {
        fcmService.send();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
