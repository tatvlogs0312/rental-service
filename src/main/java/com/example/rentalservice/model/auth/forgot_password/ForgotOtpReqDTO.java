package com.example.rentalservice.model.auth.forgot_password;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForgotOtpReqDTO {
    private String username;
    private String email;
    private String otp;
}
