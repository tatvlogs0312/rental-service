package com.example.rentalservice.model.auth.forgot_password;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ForgotPasswordReqDTO {
    private String username;
    private String newPassword;
}
