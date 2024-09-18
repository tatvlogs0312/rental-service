package com.example.rentalservice.model.search.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomTypeSearchReqDTO {
    private String name;
    private int page;
    private int size;
}
