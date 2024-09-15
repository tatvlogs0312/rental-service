package com.example.rentalservice.model.user_profile;

import java.util.List;
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
public class UserProfileDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
    private String status;
    private List<UserIdentityDTO> identities;
}
