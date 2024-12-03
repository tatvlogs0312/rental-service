package com.example.rentalservice.service.room;

import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.entity.Room;
import com.example.rentalservice.entity.RoomType;
import com.example.rentalservice.enums.RoomStatusEnum;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.mapper.Mapper;
import com.example.rentalservice.model.room.IRoomData;
import com.example.rentalservice.model.room.RoomReqDTO;
import com.example.rentalservice.model.room.RoomUploadReqDTO;
import com.example.rentalservice.model.room.RoomUtilityReqDTO;
import com.example.rentalservice.model.room.detail.RoomDetailDTO;
import com.example.rentalservice.model.room.detail.UtilityDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.search.req.RoomSearchReqDTO;
import com.example.rentalservice.model.search.res.RoomDataDTO;
import com.example.rentalservice.model.search.res.RoomRentedResDTO;
import com.example.rentalservice.proxy.StorageServiceProxy;
import com.example.rentalservice.repository.RoomRepository;
import com.example.rentalservice.service.common.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final StorageServiceProxy storageServiceProxy;
    private final DataService dataService;
    private final Mapper mapper;


    //Tạo mới thông tin phòng
    public String createRoom(RoomReqDTO req) {
        RoomType roomType = dataService.getRoomType(req.getRoomTypeId());

        String lessor = JwtUtils.getUsername();

        String roomId = UUID.randomUUID().toString();

        Room room = new Room();
        room.setId(roomId);
        room.setLessor(lessor);
        room.setRoomCode(roomType.getCode() + roomRepository.getSeqRoomCode().toString());
        room.setNumberOfRom(req.getNumberOfRoom());
        room.setAcreage(req.getAcreage());
        room.setPrice(req.getPrice());
        room.setRoomStatus(RoomStatusEnum.EMPTY.name());
        room.setDeleted(false);

        roomRepository.save(room);
        return roomId;
    }

    public String createRoomV2(RoomReqDTO req) {

        String lessor = JwtUtils.getUsername();

        String roomId = UUID.randomUUID().toString();

        Room room = new Room();
        room.setId(roomId);
        room.setHouseId(req.getHouseId());
        room.setLessor(lessor);
        room.setRoomName(req.getRoomName());
        room.setNumberOfRom(req.getNumberOfRoom());
        room.setAcreage(req.getAcreage());
        room.setRoomStatus(RoomStatusEnum.EMPTY.name());
        room.setDeleted(false);

        log.info("insert room: {}", JsonUtils.toJson(room));
        roomRepository.save(room);
        return roomId;
    }


    //Cập nhật thông tin phòng
    public void updateRoomInfo(RoomReqDTO req) {
        Room room = dataService.getRoom(req.getId());

        room.setLessor(JwtUtils.getUsername());
        room.setNumberOfRom(req.getNumberOfRoom());
        room.setAcreage(req.getAcreage());
        room.setPrice(req.getPrice());

        roomRepository.save(room);
    }


    //Tìm phòng
    public PagingResponse<RoomDataDTO> searchRoom(RoomSearchReqDTO req) {
        PagingResponse<RoomDataDTO> response = new PagingResponse<>();

        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        if (StringUtils.isBlank(req.getStatus())) {
            req.setStatus(null);
        }
        if (StringUtils.isBlank(req.getRoomTypeId())) {
            req.setRoomTypeId(null);
        }
        if (StringUtils.isBlank(req.getPosition())) {
            req.setPosition("");
        }
        if (StringUtils.isBlank(req.getWard())) {
            req.setWard("");
        }
        if (StringUtils.isBlank(req.getDistrict())) {
            req.setDistrict("");
        }
        if (StringUtils.isBlank(req.getProvince())) {
            req.setProvince("");
        }

        Page<IRoomData> iRoomData = roomRepository.findAllByCondition(JwtUtils.getUsername(), req.getStatus(),
                req.getRoomTypeId(), req.getPosition(), req.getWard(), req.getDistrict(), req.getProvince(), pageable);
        List<RoomDataDTO> models = iRoomData.getContent().stream().map(RoomDataDTO::new).toList();
        response.setData(models);
        response.setTotalData(iRoomData.getTotalElements());
        response.setTotalPage(iRoomData.getTotalPages());

        return response;
    }

    public PagingResponse<Room> search(String houseId, Integer page, Integer size) {
        Page<Room> roomPage = roomRepository.findAllByHouseIdAndDeleted(houseId, false, PageRequest.of(page, size));
        return new PagingResponse<>(roomPage);
    }

    public PagingResponse<Room> search(String houseId, String roomStatus, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepository.findAllByHouseIdAndRoomStatus(houseId, roomStatus, false, pageable);
        return new PagingResponse<>(roomPage);
    }


    //Upload hình ảnh phòng trọ
    public String uploadRoomImage(RoomUploadReqDTO req) {
        return null;
    }


    //Xóa hình ảnh phòng trọ
    public void deleteRoomImage(RoomUploadReqDTO req) {

    }


    //Thêm tiện ích cho phòng trọ
    public UtilityDTO addUtilityForRoom(RoomUtilityReqDTO req) {
        return new UtilityDTO();
    }


    //Cập nhật tiện ích cho phòng trọ
    public void updateUtilityForRoom(RoomUtilityReqDTO req) {

    }


    //Xóa dịch vụ
    public void inactivateUtilityForRoom(RoomUtilityReqDTO req) {

    }


    //Lay chi tiet theo id
    public RoomDetailDTO getDetailById(String roomId) {
        RoomDetailDTO roomDetailDTO = new RoomDetailDTO();

        Room room = dataService.getRoom(roomId);
        roomDetailDTO.setRoomId(roomId);
        roomDetailDTO.setRoomCode(room.getRoomCode());
        roomDetailDTO.setAcreage(room.getAcreage());
        roomDetailDTO.setNumberOfRoom(room.getNumberOfRom());
        roomDetailDTO.setPrice(room.getPrice());
        roomDetailDTO.setStatus(room.getRoomStatus());

        return roomDetailDTO;
    }

    public void deleteRoom(String roomId) {
        Room room = dataService.getRoom(roomId);
        if (Objects.equals(room.getRoomStatus(), "EMPTY")) {
            room.setDeleted(true);
            roomRepository.save(room);
        } else {
            throw new ApplicationException("Phòng đang cho thuê, không thể xóa phòng");
        }
    }

    public List<RoomRentedResDTO> getRoomRented() {
        String tenant = JwtUtils.getUsername();
        List<Object[]> roomsRented = roomRepository.getRoomRented(tenant, null);
        if (!CollectionUtils.isEmpty(roomsRented)) {
            return roomsRented.stream().map(RoomRentedResDTO::new).toList();
        }
        return new ArrayList<>();
    }

    public List<RoomRentedResDTO> getTenantRented() {
        String lessor = JwtUtils.getUsername();
        List<Object[]> roomsRented = roomRepository.getRoomRented(null, lessor);
        if (!CollectionUtils.isEmpty(roomsRented)) {
            return roomsRented.stream().map(RoomRentedResDTO::new).toList();
        }
        return new ArrayList<>();
    }
}
