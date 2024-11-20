package com.example.rentalservice.controller.common;

import com.example.rentalservice.model.FCMReq;
import com.example.rentalservice.service.fcm.FcmService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    private final FcmService fcmService;

    @PostMapping("/send-notification")
    public ResponseEntity<Object> sendNotification(@RequestBody FCMReq req) throws FirebaseMessagingException {
        fcmService.send(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
