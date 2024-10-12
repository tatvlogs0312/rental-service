package com.example.rentalservice.controller.utility;

import com.example.rentalservice.model.utilities.UtilitiesSearchReqDTO;
import com.example.rentalservice.service.utilities.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utility")
@RequiredArgsConstructor
public class UtilityController {

    private final UtilityService utilityService;

    @PostMapping("/search")
    public ResponseEntity<Object> searchRoomType(@RequestBody UtilitiesSearchReqDTO req) {
        return new ResponseEntity<>(utilityService.searchUtility(req), HttpStatus.OK);
    }
}
