package com.example.rentalservice.controller.fcm;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.service.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
@Slf4j
public class FcmController {

    private final FcmService fcmService;

    @Secured
    @PostMapping("/subscribe/{token}")
    public ResponseEntity<Object> subscribe(@PathVariable String token) {
        return new ResponseEntity<>(fcmService.subscribe(JwtUtils.getUsername(), token), HttpStatus.OK);
    }

    @Secured
    @PostMapping("/unsubscribe/{token}")
    public ResponseEntity<Object> unsubscribe(@PathVariable String token) {
        fcmService.unSubscribe(JwtUtils.getUsername(), token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @GetMapping("/read/{id}")
    public ResponseEntity<Object> read(@PathVariable String id) {
        fcmService.read(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam(defaultValue = "0", required = false) Integer page,
                                         @RequestParam(defaultValue = "10", required = false) Integer size) {
        return new ResponseEntity<>(fcmService.getNotifications(page, size), HttpStatus.OK);
    }
}
