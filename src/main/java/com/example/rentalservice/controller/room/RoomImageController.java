package com.example.rentalservice.controller.room;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.room.RoomUploadReqDTO;
import com.example.rentalservice.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room-image")
@RequiredArgsConstructor
@Slf4j
public class RoomImageController {

    private final RoomService roomService;

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadRoomImage(RoomUploadReqDTO req) {
        log.info("call /room-image/upload - req: {}", JsonUtils.toJson(req));
        return new ResponseEntity<>(roomService.uploadRoomImage(req), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteRoomImage(@RequestBody RoomUploadReqDTO req) {
        log.info("call /room-image/delete - req: {}", JsonUtils.toJson(req));
        roomService.deleteRoomImage(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
