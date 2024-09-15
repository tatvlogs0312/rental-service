package com.example.rentalservice.service.booking;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.common.Utils;
import com.example.rentalservice.entity.Booking;
import com.example.rentalservice.entity.Room;
import com.example.rentalservice.enums.BookingEnum;
import com.example.rentalservice.model.booking.BookingReqDTO;
import com.example.rentalservice.repository.BookingRepository;
import com.example.rentalservice.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DataService dataService;


    //Đặt xem phòng
    public void bookingRoom(BookingReqDTO req) {
        Room room = dataService.getRoom(req.getRoomId());

        Booking booking = new Booking();
        booking.setId(Utils.generateId());
        booking.setRoomId(room.getId());
        booking.setDateBooking(LocalDate.now());
        booking.setTenant(JwtUtils.getUsername());
        booking.setStatus(BookingEnum.BOOKED.name());

        bookingRepository.save(booking);
    }
}
