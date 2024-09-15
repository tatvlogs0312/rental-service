package com.example.rentalservice.controller.room;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.room.RoomUploadReqDTO;
import com.example.rentalservice.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room-image")
@RequiredArgsConstructor
public class RoomImageController {

    private final RoomService roomService;

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadRoomImage(RoomUploadReqDTO req) {
        roomService.uploadRoomImage(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteRoomImage(@RequestBody RoomUploadReqDTO req) {
        roomService.deleteRoomImage(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
