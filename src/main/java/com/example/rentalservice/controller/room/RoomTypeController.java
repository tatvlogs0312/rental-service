package com.example.rentalservice.controller.room;

import com.example.rentalservice.model.search.req.RoomTypeSearchReqDTO;
import com.example.rentalservice.service.room.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room-type")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @PostMapping("search")
    public ResponseEntity<Object> searchRoomType(@RequestBody RoomTypeSearchReqDTO req) {
        return new ResponseEntity<>(roomTypeService.searchRoomType(req), HttpStatus.OK);
    }
}
