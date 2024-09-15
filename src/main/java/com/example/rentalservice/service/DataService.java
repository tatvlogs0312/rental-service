package com.example.rentalservice.service;

import com.example.rentalservice.entity.Contract;
import com.example.rentalservice.entity.Room;
import com.example.rentalservice.entity.RoomType;
import com.example.rentalservice.entity.Utilities;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.repository.ContractRepository;
import com.example.rentalservice.repository.RoomRepository;
import com.example.rentalservice.repository.RoomTypeRepository;
import com.example.rentalservice.repository.UtilitiesRepository;
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
}
