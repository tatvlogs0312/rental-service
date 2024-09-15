package com.example.rentalservice.model.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewPostReqDTO {
    private String title;
    private String content;
    private String roomId;
}
