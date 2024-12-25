package com.example.rentalservice.model.provinces;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProvinceResDTO {
    @JsonProperty("province_id")
    private String provinceId;
    @JsonProperty("province_name")
    private String provinceName;
    @JsonProperty("province_type")
    private String provinceType;
}
