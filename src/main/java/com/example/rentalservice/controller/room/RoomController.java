package com.example.rentalservice.controller.room;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.room.RoomReqDTO;
import com.example.rentalservice.model.search.req.RoomSearchReqDTO;
import com.example.rentalservice.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/create")
    public ResponseEntity<Object> createRoom(@RequestBody RoomReqDTO req) {
        return new ResponseEntity<>(roomService.createRoom(req), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/create/v2")
    public ResponseEntity<Object> createRoomV2(@RequestBody RoomReqDTO req) {
        return new ResponseEntity<>(roomService.createRoomV2(req), HttpStatus.OK);
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
        log.info("call /rental-service/room/search - req: {}", JsonUtils.toJson(req));
        return new ResponseEntity<>(roomService.searchRoom(req), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/search/v2")
    public ResponseEntity<Object> searchRoomV2(@RequestParam String houseId,
                                               @RequestParam String status,
                                               @RequestParam Integer page,
                                               @RequestParam Integer size) {
        log.info("call /rental-service/room/search/v2");
        return new ResponseEntity<>(roomService.search(houseId, status, page, size), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/search/v3")
    public ResponseEntity<Object> searchRoomV3(@RequestParam (required = false) String houseId,
                                               @RequestParam (required = false) String roomStatus,
                                               @RequestParam Integer page,
                                               @RequestParam Integer size) {
        log.info("call /rental-service/room/search/v3");
        return new ResponseEntity<>(roomService.search(houseId, roomStatus, page, size), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        log.info("call");
        return new ResponseEntity<>(roomService.getDetailById(id), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        log.info("call delete");
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.TENANT})
    @GetMapping("/get-room-rented")
    public ResponseEntity<Object> getRented() {
        return new ResponseEntity<>(roomService.getRoomRented(), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/get-tenant-rented")
    public ResponseEntity<Object> getTenantRented() {
        return new ResponseEntity<>(roomService.getTenantRented(), HttpStatus.OK);
    }
}
