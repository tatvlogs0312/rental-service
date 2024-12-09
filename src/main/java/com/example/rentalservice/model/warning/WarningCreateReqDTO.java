package com.example.rentalservice.model.warning;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WarningCreateReqDTO {
    private String title;
    private String content;
    private String roomId;
    private List<MultipartFile> files;
}
