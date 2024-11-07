package com.example.rentalservice.controller.book;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.enums.RoleEnum;
import com.example.rentalservice.model.booking.BookingReqDTO;
import com.example.rentalservice.model.search.req.BookSearchReqDTO;
import com.example.rentalservice.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookingService bookingService;

    @Secured
    @PostMapping("/create")
    public ResponseEntity<Object> bookRoom(@RequestBody BookingReqDTO req) {
        bookingService.bookingRoom(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.TENANT})
    @PostMapping("/search-for-tenant")
    public ResponseEntity<Object> searchForTenant(@RequestBody BookSearchReqDTO req) {
        return new ResponseEntity<>(bookingService.searchForTenant(req), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @PostMapping("/search-for-lessor")
    public ResponseEntity<Object> searchForLessor(@RequestBody BookSearchReqDTO req) {
        log.info("Start api /rental-service/book/search-for-lessor - req: {}", JsonUtils.toJson(req));
        return new ResponseEntity<>(bookingService.searchForLessor(req), HttpStatus.OK);
    }

    @Secured(roles = {RoleEnum.LESSOR})
    @GetMapping("/get-book-in-month")
    public ResponseEntity<Object> getBookInMonth(@RequestParam("month") Integer month, @RequestParam("year") Integer year) {
        return new ResponseEntity<>(bookingService.getBookInMonth(month, year), HttpStatus.OK);
    }
}
