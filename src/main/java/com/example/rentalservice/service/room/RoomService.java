package com.example.rentalservice.service.room;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.entity.*;
import com.example.rentalservice.enums.RoomStatusEnum;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.room.RoomReqDTO;
import com.example.rentalservice.model.room.RoomUploadReqDTO;
import com.example.rentalservice.model.room.RoomUtilityReqDTO;
import com.example.rentalservice.model.user_profile.UserPaperResDTO;
import com.example.rentalservice.proxy.StorageServiceProxy;
import com.example.rentalservice.repository.RoomImageRepository;
import com.example.rentalservice.repository.RoomPositionRepository;
import com.example.rentalservice.repository.RoomRepository;
import com.example.rentalservice.repository.RoomUtilityRepository;
import com.example.rentalservice.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomPositionRepository roomPositionRepository;
    private final RoomUtilityRepository roomUtilityRepository;
    private final RoomImageRepository roomImageRepository;
    private final StorageServiceProxy storageServiceProxy;
    private final DataService dataService;


    //Tạo mới thông tin phòng
    public void createRoom(RoomReqDTO req) {
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
        room.setRoomTypeId(req.getRoomTypeId());

        RoomPosition roomPosition = new RoomPosition();
        roomPosition.setId(UUID.randomUUID().toString());
        roomPosition.setRoomId(roomId);
        roomPosition.setProvince(req.getProvince());
        roomPosition.setDistrict(req.getDistrict());
        roomPosition.setWard(req.getWard());
        roomPosition.setDetail(req.getPositionDetail());

        roomRepository.save(room);
        roomPositionRepository.save(roomPosition);
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


    //Upload hình ảnh phòng trọ
    public void uploadRoomImage(RoomUploadReqDTO req) {
        Room room = dataService.getRoom(req.getRoomId());

        //Upload image to storage-service
        UserPaperResDTO uploadFile = storageServiceProxy.uploadFile(req.getImage());

        RoomImage roomImage = new RoomImage();
        roomImage.setId(UUID.randomUUID().toString());
        roomImage.setRoomId(room.getId());
        roomImage.setUrl(uploadFile.getFile());

        roomImageRepository.save(roomImage);
    }


    //Xóa hình ảnh phòng trọ
    public void deleteRoomImage(RoomUploadReqDTO req) {
        Optional<RoomImage> roomImageOptional = roomImageRepository.findById(req.getImageId());
        if (roomImageOptional.isEmpty()) {
            throw new ApplicationException("File không tồn tại");
        }

        roomImageRepository.deleteById(req.getImageId());
    }


    //Thêm tiện ích cho phòng trọ
    public void addUtilityForRoom(RoomUtilityReqDTO req) {
        Utilities utility = dataService.getUtility(req.getUtilityId());

        Optional<RoomUtility> roomUtilityOptional = roomUtilityRepository
                .findFirstByRoomIdAndUtilityIdAndIsActive(req.getRoomId(), req.getUtilityId(), true);
        if (roomUtilityOptional.isPresent()) {
            throw new ApplicationException("Loại dịch vụ đã được thêm");
        }

        RoomUtility roomUtility = new RoomUtility();
        roomUtility.setId(UUID.randomUUID().toString());
        roomUtility.setUtilityId(utility.getId());
        roomUtility.setRoomId(req.getRoomId());
        roomUtility.setPrice(req.getPrice());
        roomUtility.setUnit(req.getUnit());
        roomUtility.setIsActive(true);

        roomUtilityRepository.save(roomUtility);
    }


    //Cập nhật tiện ích cho phòng trọ
    public void updateUtilityForRoom(RoomUtilityReqDTO req) {
        Utilities utility = dataService.getUtility(req.getUtilityId());

        Optional<RoomUtility> roomUtilityOptional = roomUtilityRepository
                .findFirstByRoomIdAndUtilityIdAndIsActive(req.getRoomId(), req.getUtilityId(), true);
        if (roomUtilityOptional.isEmpty()) {
            throw new ApplicationException("Loại dịch vụ chưa được thêm");
        }

        RoomUtility roomUtility = new RoomUtility();
        roomUtility.setId(UUID.randomUUID().toString());
        roomUtility.setUtilityId(utility.getId());
        roomUtility.setRoomId(req.getRoomId());
        roomUtility.setPrice(req.getPrice());
        roomUtility.setUnit(req.getUnit());
        roomUtility.setIsActive(true);
        roomUtilityRepository.save(roomUtility);

        roomUtilityRepository.updateInactiveByRoomId(req.getRoomId());
    }


    //Xóa dịch vụ
    public void inactivateUtilityForRoom(RoomUtilityReqDTO req) {
        Optional<RoomUtility> roomUtilityOptional = roomUtilityRepository.findById(req.getId());
        if (roomUtilityOptional.isEmpty()) {
            throw new ApplicationException("Loại dịch vụ không hợp lệ");
        }

        roomUtilityRepository.updateIsActiveById(req.getId(), false);
    }
}
