package com.example.rentalservice.service.house;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.entity.House;
import com.example.rentalservice.entity.Room;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.house.CreateHouseReqDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.search.res.HouseSearchResDTO;
import com.example.rentalservice.model.user_profile.UserPaperResDTO;
import com.example.rentalservice.proxy.StorageServiceProxy;
import com.example.rentalservice.repository.HouseRepository;
import com.example.rentalservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseService {

    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final StorageServiceProxy storageServiceProxy;

    public void createHouse(CreateHouseReqDTO req) {
        House house = new House();
        house.setId(UUID.randomUUID().toString());
        house.setHouseName(req.getHouseName());
        house.setPositionDetail(req.getPositionDetail());
        house.setWard(req.getWard());
        house.setDistrict(req.getDistrict());
        house.setProvince(req.getProvince());
        house.setLessor(JwtUtils.getUsername());
        house.setDeleted(false);
        houseRepository.save(house);
    }

    public void createHouseV2(CreateHouseReqDTO req) {
        House house = new House();
        house.setId(UUID.randomUUID().toString());
        house.setHouseName(req.getHouseName());
        house.setPositionDetail(req.getPositionDetail());
        house.setWard(req.getWard());
        house.setDistrict(req.getDistrict());
        house.setProvince(req.getProvince());
        house.setLessor(JwtUtils.getUsername());
        house.setDeleted(false);

        UserPaperResDTO userPaperResDTO = storageServiceProxy.uploadFile(req.getImage());
        house.setImage(userPaperResDTO.getFile());

        houseRepository.save(house);
    }

    public PagingResponse<HouseSearchResDTO> search(Pageable pageable) {
        Page<House> housePage = houseRepository.findAllByLessorAndDeleted(JwtUtils.getUsername(), false, pageable);
        List<HouseSearchResDTO> data = new ArrayList<>();
        if (housePage.hasContent()) {
            data = housePage.getContent().stream().map(HouseSearchResDTO::new).toList();

            List<Room> rooms = roomRepository.findAllByHouseIdInAndDeleted(data.stream().map(HouseSearchResDTO::getId).toList(), false);
            data.forEach(x -> {
                List<Room> roomOfHouse = rooms.stream().filter(room -> Objects.equals(room.getHouseId(), x.getId())).toList();
                List<Room> roomEmptyOfHouse = rooms.stream().filter(room ->
                        Objects.equals(room.getHouseId(), x.getId())
                                && Objects.equals(room.getRoomStatus(), "EMPTY")).toList();
                x.setTotalRoom(roomOfHouse.size());
                x.setTotalEmptyRoom(roomEmptyOfHouse.size());
            });
        }
        return new PagingResponse<>(data, housePage.getTotalElements(), housePage.getTotalPages());
    }

    public void delete(String houseId) {
        Optional<House> houseOtp = houseRepository.findById(houseId);
        if (houseOtp.isEmpty()) {
            throw new ApplicationException("Nhà không tồn tại");
        }

        Long roomNotEmpty = roomRepository.countAllByHouseIdAndRoomStatusAndDeleted(houseId, "RENTED", false);
        if (roomNotEmpty > 0L) {
            throw new ApplicationException("Còn phòng đang cho thuê, không thể xóa");
        }

        House house = houseOtp.get();
        house.setDeleted(true);
        houseRepository.save(house);
        roomRepository.updateRoomDeleted(houseId);
    }
}
