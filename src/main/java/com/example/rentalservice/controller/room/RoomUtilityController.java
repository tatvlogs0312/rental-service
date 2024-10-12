package com.example.rentalservice.controller.room;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.room.RoomUtilityReqDTO;
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
@RequestMapping("/room-utility")
@RequiredArgsConstructor
@Slf4j
public class RoomUtilityController {

    private final RoomService roomService;

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/add")
    public ResponseEntity<Object> addUtilityForRoom(@RequestBody RoomUtilityReqDTO req) {
        log.info("/room-utility/add - req: {}", JsonUtils.toJson(req));
        return new ResponseEntity<>(roomService.addUtilityForRoom(req), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/update")
    public ResponseEntity<Object> updateUtilityForRoom(@RequestBody RoomUtilityReqDTO req) {
        roomService.updateUtilityForRoom(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/inactive")
    public ResponseEntity<Object> inactivateUtilityForRoom(@RequestBody RoomUtilityReqDTO req) {
        roomService.inactivateUtilityForRoom(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
