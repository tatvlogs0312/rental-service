package com.example.rentalservice.controller.authentication;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.model.auth.login.LoginFaceReqDTO;
import com.example.rentalservice.model.auth.login.LoginReqDTO;
import com.example.rentalservice.model.auth.register.RegisterReqDTO;
import com.example.rentalservice.model.auth.update_password.UpdatePasswordReqDTO;
import com.example.rentalservice.service.user_profile.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final UserProfileService service;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReqDTO req) {
        return new ResponseEntity<>(service.login(req), HttpStatus.OK);
    }

    @PostMapping("/login-face")
    public ResponseEntity<Object> loginFace(LoginFaceReqDTO req) throws Exception {
        return new ResponseEntity<>(service.loginFace(req), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterReqDTO req) {
        return new ResponseEntity<>(service.register(req), HttpStatus.OK);
    }

    @Secured
    @PostMapping("/update-password")
    public ResponseEntity<Object> updatePassword(@RequestBody UpdatePasswordReqDTO req) {
        service.updatePassword(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
