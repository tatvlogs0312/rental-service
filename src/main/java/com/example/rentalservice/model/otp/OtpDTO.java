package com.example.rentalservice.model.otp;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OtpDTO {
    private String username;
    private String otp;
    private Integer numberOtp;
}
