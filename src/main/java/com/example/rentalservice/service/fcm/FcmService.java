package com.example.rentalservice.service.fcm;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    public void send() {
        FirebaseApp firebaseApp = FirebaseApp.getInstance();

        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("Hello")
                        .setBody("Test from spring")
                        .build())
                .setToken("dKnisgs0SfSmxns9r726kP:APA91bHzZ7whlO_YmPOmcN6oEANuAvvacI88jr9cr84KPzOEQeF3Xv5RENvO2hCivGR_JAMRqFHBEnvzE6PjhuSZc9lFECAWBkVHV2wB5xyGxz_4_W3eICw")
                .build();
        String response = null;
        try {
            response = FirebaseMessaging.getInstance(firebaseApp).send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
