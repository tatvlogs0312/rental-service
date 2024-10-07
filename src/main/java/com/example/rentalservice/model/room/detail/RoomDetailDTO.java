package com.example.rentalservice.model.room.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDetailDTO {
    private String roomId;
    private String roomCode;
    private String roomType;
    private Integer acreage;
    private Integer numberOfRoom;
    private Long price;
    private String status;
    private List<UtilityDTO> utility = new ArrayList<>();
    private List<String> image = new ArrayList<>();
    private PositionDTO position;
}
