package com.example.rentalservice.model.auth.update_password;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePasswordReqDTO {
    private String oldPassword;
    private String newPassword;
}
