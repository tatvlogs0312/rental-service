package com.example.rentalservice.model.user_profile;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateInformationReqDTO {
    private String gender;
    private String birthDate;
    private String identityNumber;
    private String phoneNumber;
    private String email;
}
