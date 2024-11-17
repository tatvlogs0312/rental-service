package com.example.rentalservice.model.post.detail;

import com.example.rentalservice.model.room.detail.PositionDTO;
import com.example.rentalservice.model.room.detail.RoomDetailDTO;
import com.example.rentalservice.model.room.detail.UtilityDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostDetailDTO {
    private String postId;

    private String title;

    private String content;

    //Lessor info
    private String lessorName;

    private String lessorNumber;

    //Room info
    private String roomId;

    private Integer acreage;

    private Integer numberOfRoom;

    private Long price;

    private List<UtilityDTO> utility = new ArrayList<>();

    private List<String> image = new ArrayList<>();

    private PositionDTO position;

    private String roomTypeId;

    private String roomTypeName;
}
