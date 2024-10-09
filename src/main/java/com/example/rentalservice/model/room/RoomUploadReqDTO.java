package com.example.rentalservice.model.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomUploadReqDTO {
    private String imageId;
    private String fileName;
    private String roomId;
    private MultipartFile image;
}
