package com.example.rentalservice.model.key_cloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class KeycloakUpdatePasswordReqDTO {
    private String type;
    private Boolean temporary;
    private String value;
}
