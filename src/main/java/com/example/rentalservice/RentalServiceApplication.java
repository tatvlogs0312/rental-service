package com.example.rentalservice;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "com")
public class RentalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentalServiceApplication.class, args);
    }

}
