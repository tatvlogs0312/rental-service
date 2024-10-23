package com.example.rentalservice.controller.book;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.model.booking.BookingReqDTO;
import com.example.rentalservice.model.search.req.BookSearchReqDTO;
import com.example.rentalservice.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookingService bookingService;

    @Secured
    @PostMapping("/create")
    public ResponseEntity<Object> bookRoom(@RequestBody BookingReqDTO req) {
        bookingService.bookingRoom(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @PostMapping("/search-for-tenant")
    public ResponseEntity<Object> searchForTenant(@RequestBody BookSearchReqDTO req) {
        return new ResponseEntity<>(bookingService.searchForTenant(req), HttpStatus.OK);
    }

    @Secured
    @PostMapping("/search-for-lessor")
    public ResponseEntity<Object> searchForLessor(@RequestBody BookSearchReqDTO req) {
        return new ResponseEntity<>(bookingService.searchForLessor(req), HttpStatus.OK);
    }
}
