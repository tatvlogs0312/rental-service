package com.example.rentalservice.service.fcm;

import com.example.rentalservice.model.FCMReq;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public void send(FCMReq req) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(req.getToken())
                .setNotification(Notification.builder()
                        .setTitle("Hello")
                        .setBody("Test from spring")
                        .build())
                .build();
        firebaseMessaging.send(message);
    }
}
