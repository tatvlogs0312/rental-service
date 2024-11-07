package com.example.rentalservice.service.booking;

import com.example.rentalservice.common.DateUtils;
import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.common.RepositoryUtils;
import com.example.rentalservice.common.Utils;
import com.example.rentalservice.entity.Booking;
import com.example.rentalservice.entity.Room;
import com.example.rentalservice.enums.BookingEnum;
import com.example.rentalservice.model.booking.ApproveRejectBookRepDTO;
import com.example.rentalservice.model.booking.BookingReqDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.search.req.BookSearchReqDTO;
import com.example.rentalservice.model.search.res.BookSearchResDTO;
import com.example.rentalservice.repository.BookingRepository;
import com.example.rentalservice.service.common.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DataService dataService;


    //Đặt xem phòng
    public void bookingRoom(BookingReqDTO req) {
        LocalDateTime dateTime = DateUtils.convertToLocalDateTime(req.getDateWatch(), DateUtils.F_HHMMDDMMYYYY);
        Room room = dataService.getRoom(req.getRoomId());

        Booking booking = new Booking();
        booking.setId(Utils.generateId());
        booking.setRoomId(room.getId());
        booking.setDateBooking(LocalDateTime.now());
        booking.setDateWatch(dateTime);
        booking.setTenant(JwtUtils.getUsername());
        booking.setStatus(BookingEnum.BOOKED.name());

        bookingRepository.save(booking);
    }

    public PagingResponse<BookSearchResDTO> searchForTenant(BookSearchReqDTO req) {
        String tenant = JwtUtils.getUsername();
        Pageable pageable = RepositoryUtils.createPageable(req.getPage(), req.getSize());

        Page<Object[]> data = bookingRepository.findAllBookByUser(null, tenant, req.getStatus(), null, pageable);
        if (data.hasContent()) {
            List<BookSearchResDTO> models = data.get().map(BookSearchResDTO::new).toList();
            return new PagingResponse<>(models, data.getTotalElements(), data.getTotalPages());
        }

        return new PagingResponse<>(new ArrayList<>(), 0, 0);
    }

    public PagingResponse<BookSearchResDTO> searchForLessor(BookSearchReqDTO req) {
        String lessor = JwtUtils.getUsername();
        Pageable pageable = RepositoryUtils.createPageable(req.getPage(), req.getSize());

        LocalDate date = Objects.nonNull(req.getDate()) ? DateUtils.convertToLocalDate(req.getDate(), DateUtils.YYYY_MM_DD) : null;
        Page<Object[]> data = bookingRepository.findAllBookByUser(lessor, null, req.getStatus(), date.toString(), pageable);
        if (data.hasContent()) {
            List<BookSearchResDTO> models = data.get().map(BookSearchResDTO::new).toList();
            return new PagingResponse<>(models, data.getTotalElements(), data.getTotalPages());
        }

        return new PagingResponse<>(new ArrayList<>(), 0, 0);
    }

    public void approveRejectBook(ApproveRejectBookRepDTO req) {
        Booking booking = dataService.getBooking(req.getBookingId());
        booking.setStatus(req.getStatus());
        booking.setBookingMessage(req.getMessage());
        bookingRepository.save(booking);
    }

    public List<String> getBookInMonth(Integer month, Integer year) {
        List<Booking> bookings = bookingRepository.findAllByMonthAndYear(month, year, JwtUtils.getUsername());
        if (!CollectionUtils.isEmpty(bookings)) {
            return bookings.stream().map(b -> DateUtils.toStr(b.getDateWatch(), DateUtils.YYYY_MM_DD)).distinct().toList();
        }

        return new ArrayList<>();
    }
}
