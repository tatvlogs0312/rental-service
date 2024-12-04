package com.example.rentalservice.model.user_profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompleteInformationReqDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
