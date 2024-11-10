package com.example.rentalservice.model.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewPostReqDTO {
    private String title;
    private String content;
    private String positionDetail;
    private String ward;
    private String district;
    private String province;
    private Long price;
    private String roomType;
    private List<MultipartFile> files;
}
