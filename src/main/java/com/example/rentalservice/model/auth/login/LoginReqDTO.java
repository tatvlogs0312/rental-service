package com.example.rentalservice.model.auth.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginReqDTO {
    private String username;
    private String password;
    private String device;
}
