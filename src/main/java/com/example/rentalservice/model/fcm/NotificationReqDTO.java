package com.example.rentalservice.model.fcm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationReqDTO {
    private String title;
    private String content;
    private String data;
    private String userReceive;
}
