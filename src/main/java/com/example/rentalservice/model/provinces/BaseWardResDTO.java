package com.example.rentalservice.model.provinces;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseWardResDTO {
    private List<WardResDTO> results = new ArrayList<>();
}
