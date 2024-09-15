package com.example.rentalservice.model.utilities;

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
public class UtilitiesSearchReqDTO {
    private String id;
    private String name;
    private int page;
    private int size;
}
