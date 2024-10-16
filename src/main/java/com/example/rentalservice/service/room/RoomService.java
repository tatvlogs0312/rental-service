package com.example.rentalservice.service.room;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.common.RepositoryUtils;
import com.example.rentalservice.entity.*;
import com.example.rentalservice.enums.RoomStatusEnum;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.mapper.Mapper;
import com.example.rentalservice.model.room.RoomReqDTO;
import com.example.rentalservice.model.room.RoomUploadReqDTO;
import com.example.rentalservice.model.room.RoomUtilityReqDTO;
import com.example.rentalservice.model.room.IRoomData;
import com.example.rentalservice.model.room.detail.PositionDTO;
import com.example.rentalservice.model.room.detail.RoomDetailDTO;
import com.example.rentalservice.model.room.detail.UtilityDTO;
import com.example.rentalservice.model.search.res.RoomDataDTO;
import com.example.rentalservice.model.search.req.RoomSearchReqDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.user_profile.UserPaperResDTO;
import com.example.rentalservice.proxy.StorageServiceProxy;
import com.example.rentalservice.repository.RoomImageRepository;
import com.example.rentalservice.repository.RoomPositionRepository;
import com.example.rentalservice.repository.RoomRepository;
import com.example.rentalservice.repository.RoomUtilityRepository;
import com.example.rentalservice.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
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


    //Upload hình ảnh phòng trọ
    public String uploadRoomImage(RoomUploadReqDTO req) {
        Room room = dataService.getRoom(req.getRoomId());

        //Upload image to storage-service
        UserPaperResDTO uploadFile = storageServiceProxy.uploadFile(req.getImage());

        RoomImage roomImage = new RoomImage();
        roomImage.setId(UUID.randomUUID().toString());
        roomImage.setRoomId(room.getId());
        roomImage.setUrl(uploadFile.getFile());

        roomImageRepository.save(roomImage);

        return uploadFile.getFile();
    }


    //Xóa hình ảnh phòng trọ
    public void deleteRoomImage(RoomUploadReqDTO req) {
        Optional<RoomImage> roomImageOptional = roomImageRepository.findFirstByUrl(req.getFileName());
        if (roomImageOptional.isEmpty()) {
            throw new ApplicationException("File không tồn tại");
        }

        roomImageRepository.deleteById(roomImageOptional.get().getId());
    }


    //Thêm tiện ích cho phòng trọ
    public UtilityDTO addUtilityForRoom(RoomUtilityReqDTO req) {
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

        return UtilityDTO.builder().utilityId(roomUtility.getId())
                .utilityName(utility.getName())
                .utilityUnit(roomUtility.getUnit())
                .utilityPrice(roomUtility.getPrice())
                .build();
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

        Optional<RoomPosition> roomPositionOpt = roomPositionRepository.findFirstByRoomId(roomId);
        if (roomPositionOpt.isPresent()) {
            RoomPosition roomPosition = roomPositionOpt.get();
            roomDetailDTO.setPosition(PositionDTO.builder()
                    .detail(roomPosition.getDetail())
                    .ward(roomPosition.getWard())
                    .district(roomPosition.getDistrict())
                    .province(roomPosition.getDistrict())
                    .build());
        }

        List<RoomImage> roomImages = roomImageRepository.findAllByRoomId(roomId);
        if (!CollectionUtils.isEmpty(roomImages)) {
            roomDetailDTO.setImage(roomImages.stream().map(RoomImage::getUrl).toList());
        }

        List<Object[]> roomUtility = roomUtilityRepository.findAllByRoomId(roomId);
        if (!CollectionUtils.isEmpty(roomUtility)) {
            roomDetailDTO.setUtility(
                    roomUtility.stream()
                            .map(x -> UtilityDTO.builder()
                                    .utilityId(RepositoryUtils.setValueForField(String.class, x[0]))
                                    .utilityName(RepositoryUtils.setValueForField(String.class, x[1]))
                                    .utilityPrice(RepositoryUtils.setValueForField(Long.class, x[2]))
                                    .utilityUnit(RepositoryUtils.setValueForField(String.class, x[3]))
                                    .build())
                            .toList()
            );
        }

        return roomDetailDTO;
    }
}
