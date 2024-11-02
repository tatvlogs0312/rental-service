package com.example.rentalservice.model.search.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostSearchReqDTO {
    private String roomTypeId;
    private String province;
    private String district;
    private String ward;
    private String detail;
    private String keyword;
    private Long priceFrom;
    private Long priceTo;
    private int page = 0;
    private int size = 10;
}
