package com.example.rentalservice.service.malfunction_warning;

import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.entity.*;
import com.example.rentalservice.enums.ContractStatusEnum;
import com.example.rentalservice.enums.MalfunctionWarningEnum;
import com.example.rentalservice.enums.NotificationTypeEnum;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.fcm.NotificationReqDTO;
import com.example.rentalservice.model.fcm.NotificationType;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.search.res.WarningSearchResDTO;
import com.example.rentalservice.model.user_profile.UserPaperResDTO;
import com.example.rentalservice.model.warning.WarningCreateReqDTO;
import com.example.rentalservice.model.warning.WarningDetailDTO;
import com.example.rentalservice.proxy.StorageServiceProxy;
import com.example.rentalservice.repository.ContractRepository;
import com.example.rentalservice.repository.MalfunctionWarningImageRepository;
import com.example.rentalservice.repository.MalfunctionWarningRepository;
import com.example.rentalservice.service.common.DataService;
import com.example.rentalservice.service.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
@Slf4j
public class MalfunctionWarningService {

    private final MalfunctionWarningRepository malfunctionWarningRepository;
    private final MalfunctionWarningImageRepository malfunctionWarningImageRepository;
    private final StorageServiceProxy storageServiceProxy;
    private final ContractRepository contractRepository;
    private final DataService dataService;
    private final FcmService fcmService;

    @Qualifier("cachedThreadPool")
    @Autowired
    private Executor executor;

    public void create(WarningCreateReqDTO req) {
        Room room = dataService.getRoom(req.getRoomId());
        House house = dataService.getHouse(room.getHouseId());

        String tenant = JwtUtils.getUsername();
        Optional<Contract> contractOtp = contractRepository.findFirstByRoomIdAndTenantAndStatus(
                req.getRoomId(), tenant, ContractStatusEnum.SIGNED.name());
        if (contractOtp.isEmpty()) {
            throw new ApplicationException("Bạn không còn thuê phòng này");
        }

        Contract contract = contractOtp.get();

        String id = UUID.randomUUID().toString();
        MalfunctionWarning malfunctionWarning = new MalfunctionWarning();
        malfunctionWarning.setId(id);
        malfunctionWarning.setContent(req.getContent());
        malfunctionWarning.setTitle(req.getTitle());
        malfunctionWarning.setCreateTime(LocalDateTime.now());
        malfunctionWarning.setTenant(tenant);
        malfunctionWarning.setLessor(contract.getLessor());
        malfunctionWarning.setRoomId(contract.getRoomId());
        malfunctionWarning.setHouseId(contract.getHouseId());
        malfunctionWarning.setStatus(MalfunctionWarningEnum.PENDING.name());

        List<MalfunctionWarningImage> malfunctionWarningImages = new ArrayList<>();
        if (!CollectionUtils.isEmpty(req.getFiles())) {
            req.getFiles().forEach(file -> {
                try {
                    var pathAsync = CompletableFuture.supplyAsync(() -> {
                        UserPaperResDTO userPaperResDTO = storageServiceProxy.uploadFile(file);

                        MalfunctionWarningImage malfunctionWarningImage = new MalfunctionWarningImage();
                        malfunctionWarningImage.setId(UUID.randomUUID().toString());
                        malfunctionWarningImage.setMalfunctionWarningId(id);
                        malfunctionWarningImage.setUrl(userPaperResDTO.getFile());
                        return malfunctionWarningImage;
                    }, executor).get();

                    malfunctionWarningImages.add(pathAsync);

                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        malfunctionWarningRepository.save(malfunctionWarning);
        if (!CollectionUtils.isEmpty(malfunctionWarningImages)) {
            malfunctionWarningImageRepository.saveAll(malfunctionWarningImages);
        }

        NotificationReqDTO notificationReqDTO = NotificationReqDTO.builder()
                .title("Thông báo sự cố")
                .content(String.format("""
                        Khách thuê phòng %s - nhà %s đã thông báo có sự cố.
                        Bấm để xem chi tiết.
                        """, room.getRoomName(), house.getHouseName()))
                .data(JsonUtils.toJson(new NotificationType(NotificationTypeEnum.WARNING.name(), id)))
                .userReceive(contract.getLessor())
                .build();
        fcmService.sendNotificationToUser(notificationReqDTO);
    }

    public PagingResponse<WarningSearchResDTO> searchForTenant(String status, Integer page, Integer size) {
        String tenant = JwtUtils.getUsername();
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> data = malfunctionWarningRepository.search(status, tenant, null, pageable);

        PagingResponse<WarningSearchResDTO> res = new PagingResponse<>();
        List<WarningSearchResDTO> models = new ArrayList<>();
        if (data.hasContent()) {
            models = data.getContent().stream().map(WarningSearchResDTO::new).toList();
        }

        res.setData(models);
        res.setTotalData(data.getTotalElements());
        res.setTotalPage(data.getTotalPages());
        return res;
    }

    public PagingResponse<WarningSearchResDTO> searchForLessor(String status, Integer page, Integer size) {
        String lessor = JwtUtils.getUsername();
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> data = malfunctionWarningRepository.search(status, null, lessor, pageable);

        PagingResponse<WarningSearchResDTO> res = new PagingResponse<>();
        List<WarningSearchResDTO> models = new ArrayList<>();
        if (data.hasContent()) {
            models = data.getContent().stream().map(WarningSearchResDTO::new).toList();
        }

        res.setData(models);
        res.setTotalData(data.getTotalElements());
        res.setTotalPage(data.getTotalPages());
        return res;
    }

    public WarningDetailDTO viewDetail(String id) {
        List<Object[]> warnings = malfunctionWarningRepository.findWarningById(id);
        if (CollectionUtils.isEmpty(warnings)) {
            throw new ApplicationException("Cảnh báo không hợp lệ");
        }

        WarningDetailDTO detailDTO = new WarningDetailDTO(warnings.get(0));

        List<MalfunctionWarningImage> warningImages = malfunctionWarningImageRepository.findAllByMalfunctionWarningId(id);
        List<String> images = warningImages.stream().map(MalfunctionWarningImage::getUrl).toList();
        detailDTO.setImages(images);

        return detailDTO;
    }

    public void cancel(String id) {
        MalfunctionWarning malfunctionWarning = dataService.getMalfunctionWarning(id);
        malfunctionWarning.setStatus(MalfunctionWarningEnum.CANCEL.name());
        malfunctionWarningRepository.save(malfunctionWarning);
    }

    public void complete(String id) {
        MalfunctionWarning malfunctionWarning = dataService.getMalfunctionWarning(id);
        malfunctionWarning.setStatus(MalfunctionWarningEnum.COMPLETE.name());
        malfunctionWarningRepository.save(malfunctionWarning);
    }
}
