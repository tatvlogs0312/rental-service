package com.example.rentalservice.service.common;

import com.example.rentalservice.entity.*;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final ContractRepository contractRepository;
    private final UtilitiesRepository utilitiesRepository;
    private final PostRepository postRepository;
    private final BookingRepository bookingRepository;

    //Lấy data phòng theo id
    public Room getRoom(String roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ApplicationException("Phòng không hợp lệ");
        }
        return room.get();
    }

    //Lấy data hợp đồng theo id
    public Contract getContract(String contractId) {
        Optional<Contract> contract = contractRepository.findById(contractId);
        if (contract.isEmpty()) {
            throw new ApplicationException("Hợp đồng không hợp lệ");
        }
        return contract.get();
    }

    //Lấy dịch vụ theo id
    public Utilities getUtility(String id) {
        Optional<Utilities> utilities = utilitiesRepository.findById(id);
        if (utilities.isEmpty()) {
            throw new ApplicationException("Loại dịch vụ không hợp lệ");
        }

        return utilities.get();
    }

    //Lấy loại phòng theo id
    public RoomType getRoomType(String roomTypeId) {
        Optional<RoomType> roomType = roomTypeRepository.findById(roomTypeId);
        if (roomType.isEmpty()) {
            throw new ApplicationException("Loại phòng không hợp lệ");
        }
        return roomType.get();
    }

    //Lay bai viet theo id
    public Post getPost(String postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new ApplicationException("Bai viet không hợp lệ");
        }
        return post.get();
    }

    //Lay lich xem phong theo id
    public Booking getBooking(String bookId) {
        Optional<Booking> booking = bookingRepository.findById(bookId);
        if (booking.isEmpty()) {
            throw new ApplicationException("Lich xem phong khong hop le");
        }
        return booking.get();
    }
}
