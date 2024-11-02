package com.example.rentalservice.model.search;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class KeywordDTO {
    private String roomType;
    private String province;
    private String district;
    private String ward;
    private Long priceIs;
    private Long priceFrom;
    private Long priceTo;
}
