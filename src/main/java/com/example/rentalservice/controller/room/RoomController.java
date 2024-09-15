package com.example.rentalservice.controller.room;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.room.RoomReqDTO;
import com.example.rentalservice.model.room.search.RoomSearchReqDTO;
import com.example.rentalservice.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/create")
    public ResponseEntity<Object> createRoom(@RequestBody RoomReqDTO req) {
        roomService.createRoom(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/update")
    public ResponseEntity<Object> updateRoom(@RequestBody RoomReqDTO req) {
        roomService.updateRoomInfo(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/search")
    public ResponseEntity<Object> searchRoom(@RequestBody RoomSearchReqDTO req) {
        return new ResponseEntity<>(roomService.searchRoom(req), HttpStatus.OK);
    }
}
