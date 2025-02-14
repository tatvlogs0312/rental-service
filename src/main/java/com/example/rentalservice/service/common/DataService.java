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

    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final ContractRepository contractRepository;
    private final UtilitiesRepository utilitiesRepository;
    private final PostRepository postRepository;
    private final BookingRepository bookingRepository;
    private final UserProfileRepository userProfileRepository;
    private final BillRepository billRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final MalfunctionWarningRepository malfunctionWarningRepository;

    //Lấy data phòng theo id
    public Room getRoom(String roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ApplicationException("Phòng không hợp lệ");
        }
        return room.get();
    }

    public House getHouse(String houseId) {
        Optional<House> house = houseRepository.findById(houseId);
        if (house.isEmpty()) {
            throw new ApplicationException("Nhà không hợp lệ");
        }
        return house.get();
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

    public UserProfile getUserByUsername(String username) {
        Optional<UserProfile> userProfile = userProfileRepository.findFirstByUsername(username);
        if (userProfile.isEmpty()) {
            throw new ApplicationException("Người dùng không tồn tại");
        }

        return userProfile.get();
    }

    public Bill getBill(String billId) {
        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isEmpty()) {
            throw new ApplicationException("");
        }

        return bill.get();
    }

    public UserNotification getUserNotification(String id) {
        Optional<UserNotification> userNotification = userNotificationRepository.findById(id);
        if (userNotification.isEmpty()) {
            throw new ApplicationException("Thông báo không hợp lệ");
        }

        return userNotification.get();
    }

    public MalfunctionWarning getMalfunctionWarning(String id) {
        Optional<MalfunctionWarning> malfunctionWarning = malfunctionWarningRepository.findById(id);
        if (malfunctionWarning.isEmpty()) {
            throw new ApplicationException("Sự cố không hợp lệ");
        }

        return malfunctionWarning.get();
    }
}
