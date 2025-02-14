package com.example.rentalservice.service.contract;

import com.example.rentalservice.common.DateUtils;
import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.common.Utils;
import com.example.rentalservice.entity.*;
import com.example.rentalservice.enums.ContractStatusEnum;
import com.example.rentalservice.enums.NotificationTypeEnum;
import com.example.rentalservice.enums.RoomStatusEnum;
import com.example.rentalservice.exception.ApplicationException;
import com.example.rentalservice.model.MailDTO;
import com.example.rentalservice.model.contract.*;
import com.example.rentalservice.model.fcm.NotificationReqDTO;
import com.example.rentalservice.model.fcm.NotificationType;
import com.example.rentalservice.model.room.detail.PositionDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.model.search.res.ContractSearchResDTO;
import com.example.rentalservice.redis.RedisService;
import com.example.rentalservice.repository.ContractRepository;
import com.example.rentalservice.repository.ContractUtilityRepository;
import com.example.rentalservice.repository.RoomRepository;
import com.example.rentalservice.repository.UtilitiesRepository;
import com.example.rentalservice.service.common.DataService;
import com.example.rentalservice.service.common.MailService;
import com.example.rentalservice.service.fcm.FcmService;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractUtilityRepository contractUtilityRepository;
    private final UtilitiesRepository utilitiesRepository;
    private final RoomRepository roomRepository;
    private final DataService dataService;
    private final RedisService redisService;
    private final MailService mailService;
    private final FcmService fcmService;

    public void createContract(CreateContractReqDTO req) {
        UserProfile tenant = dataService.getUserByUsername(req.getTenant());
        House house = dataService.getHouse(req.getHouseId());
        Room room = dataService.getRoom(req.getRoomId());

        String lessor = JwtUtils.getUsername();

        if (StringUtils.equals(room.getRoomStatus(), RoomStatusEnum.RENTED.name())) {
            throw new ApplicationException("Phòng đang cho thuê, không thể tạo hợp đồng");
        }

        String contractId = UUID.randomUUID().toString();
        Contract contract = new Contract();
        contract.setId(contractId);
        contract.setLessor(lessor);
        contract.setTenant(tenant.getUsername());
        contract.setHouseId(house.getId());
        contract.setRoomId(room.getId());
        String contractCode = "HD-" + contractRepository.getSeqContract().toString() + "-" + req.getStartDate();
        contract.setContractCode(contractCode);
        contract.setStatus(ContractStatusEnum.DRAFT.name());
        contract.setEffectDate(LocalDate.parse(req.getStartDate()));
        contract.setActualPrice(req.getPrice());
        contract.setCreatedDate(LocalDate.now());
        contract.setCreatedTime(LocalDateTime.now());

        List<ContractUtility> contractUtilities = new ArrayList<>();
        req.getUtilities().forEach(u -> {
            ContractUtility contractUtility = new ContractUtility();
            contractUtility.setId(UUID.randomUUID().toString());
            contractUtility.setContractId(contractId);
            contractUtility.setUtilityId(u.getUtilityId());
            contractUtility.setPrice(u.getPrice());
            contractUtility.setUnit(u.getUnit());
            contractUtilities.add(contractUtility);
        });

        contractRepository.save(contract);
        if (!contractUtilities.isEmpty()) {
            contractUtilityRepository.saveAll(contractUtilities);
        }

        room.setRoomStatus(RoomStatusEnum.RENTED.name());
        roomRepository.save(room);
    }

    public void sendUser(String contractId) {
        Contract contract = dataService.getContract(contractId);
        contract.setStatus(ContractStatusEnum.PENDING_SIGNED.name());
        contractRepository.save(contract);

        NotificationReqDTO notificationReqDTO = NotificationReqDTO.builder()
                .title("Yêu cầu ký hợp đồng " + contract.getContractCode())
                .content("""
                        Bạn đã được gửi một bản hợp đồng thuê trọ
                        Bấm để xem chi tiết
                        """)
                .data(JsonUtils.toJson(new NotificationType(NotificationTypeEnum.CONTRACT.name(), contractId)))
                .userReceive(contract.getTenant())
                .build();
        fcmService.sendNotificationToUser(notificationReqDTO);
    }

    public void getContractOtp(String contractId) {
        String otp = Utils.generateOTP(6);

        Contract contract = dataService.getContract(contractId);
        UserProfile userProfile = dataService.getUserByUsername(contract.getTenant());

        contract.setOtp(otp);
        contract.setNumberOtp(0);
        contract.setOtpExpiredTime(LocalDateTime.now().plusMinutes(5));

        contractRepository.save(contract);

        String keyOTPCache = "OTP-" + contractId;
        redisService.setValue(keyOTPCache, otp, 300L);

        MailDTO mailDTO = new MailDTO();
        mailDTO.setMailTo(List.of(userProfile.getEmail()));
        mailDTO.setMailCc(new ArrayList<>());
        mailDTO.setSubject("Mã OTP ký hợp đồng " + contract.getContractCode());
        mailDTO.setContent(String.format("""
                    <p>Mã OTP của bạn là <b style="color: blue">%s</b></p>
                    <p>Mã OTP này có hiệu lực trong vòng 5 phút.</p>
                    <p>Vui lòng không tiết lộ cho người lạ.</p>
                """, otp));
        mailService.sendMailHtml(mailDTO);
    }

    public void signContract(ContractSignReqDTO req) {
        Contract contract = dataService.getContract(req.getContractId());
        if (contract.getNumberOtp() >= 5) {
            throw new ApplicationException("Đã nhập sai 5 lần vui lòng lấy mã otp mới");
        }

        String keyOTPCache = "OTP-" + req.getContractId();
        String otpCache = redisService.getValue(keyOTPCache);
        if (StringUtils.isBlank(otpCache)) {
            throw new ApplicationException("OTP đã hết hạn vui lòng lấy mã OTP mới");
        }

        if (!StringUtils.equals(otpCache, req.getOtp())) {
            contract.setNumberOtp(contract.getNumberOtp() + 1);
            contractRepository.save(contract);
            throw new ApplicationException("OTP không chính xác vui lòng nhập lại");
        }

        contract.setStatus(ContractStatusEnum.SIGNED.name());
        contract.setSignedTime(LocalDateTime.now());
        contractRepository.save(contract);

        NotificationReqDTO notificationReqDTO = NotificationReqDTO.builder()
                .title("Hợp đồng " + contract.getContractCode() + " đã được ký thành công")
                .content("""
                        Bấm để xem thông tin hợp đồng
                        """)
                .data(JsonUtils.toJson(new NotificationType(NotificationTypeEnum.CONTRACT.name(), req.getContractId())))
                .userReceive(contract.getLessor())
                .build();
        fcmService.sendNotificationToUser(notificationReqDTO);
    }

    public void cancelContract(ContractReqDTO req) {
        Contract contract = dataService.getContract(req.getId());
        if (Objects.equals(contract.getStatus(), ContractStatusEnum.PENDING_SIGNED.name())) {
            throw new ApplicationException("Không thể hủy hợp đồng đã gửi khách thuê ký");
        }
        contract.setStatus(ContractStatusEnum.CANCEL.name());
        contract.setStatusMessage(req.getMessage());
        contractRepository.save(contract);

        Room room = dataService.getRoom(contract.getRoomId());
        room.setRoomStatus(RoomStatusEnum.EMPTY.name());
        roomRepository.save(room);
    }

    public void rejectContract(ContractReqDTO req) {
        if (StringUtils.isBlank(req.getMessage())) {
            throw new ApplicationException("Vui lòng nhập lý do từ chối");
        }
        Contract contract = dataService.getContract(req.getId());
        if (Objects.equals(contract.getStatus(), ContractStatusEnum.SIGNED.name())) {
            throw new ApplicationException("Không thể hủy hợp đồng đã gửi khách thuê ký");
        }
        contract.setStatus(ContractStatusEnum.REJECT.name());
        contract.setStatusMessage(req.getMessage());
        contractRepository.save(contract);

        Room room = dataService.getRoom(contract.getRoomId());
        room.setRoomStatus(RoomStatusEnum.EMPTY.name());
        roomRepository.save(room);

        NotificationReqDTO notificationReqDTO = NotificationReqDTO.builder()
                .title("Yêu cầu ký hợp đồng " + contract.getContractCode() + " đã bị từ chối")
                .content("""
                        Hợp đồng đã bị từ chối bởi khách thuê
                        Bấm để xem chi tiết lý do
                        """)
                .data(JsonUtils.toJson(new NotificationType(NotificationTypeEnum.CONTRACT.name(), req.getId())))
                .userReceive(contract.getLessor())
                .build();
        fcmService.sendNotificationToUser(notificationReqDTO);
    }

    public PagingResponse<ContractSearchResDTO> searchForLessor(String status, int page, int size) {
        String lessor = JwtUtils.getUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<Object[]> data = contractRepository.searchContract(status, lessor, null, pageable);
        List<ContractSearchResDTO> models = new ArrayList<>();
        if (data.hasContent()) {
            models = data.getContent().stream().map(ContractSearchResDTO::new).toList();
        }

        return new PagingResponse<>(models, data.getTotalElements(), data.getTotalPages());
    }

    public PagingResponse<ContractSearchResDTO> searchForTenant(String status, int page, int size) {
        String tenant = JwtUtils.getUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        List<String> contractStatus = List.of(ContractStatusEnum.PENDING_SIGNED.name(), ContractStatusEnum.SIGNED.name());
        if (StringUtils.isNotBlank(status)) {
            contractStatus = List.of(status);
        }

        Page<Object[]> data = contractRepository.searchContractV2(contractStatus, null, tenant, pageable);
        List<ContractSearchResDTO> models = new ArrayList<>();
        if (data.hasContent()) {
            models = data.getContent().stream().map(ContractSearchResDTO::new).toList();
        }

        return new PagingResponse<>(models, data.getTotalElements(), data.getTotalPages());
    }

    public ContractDetailDTO getContractDetail(String contractId) {
        Contract contract = dataService.getContract(contractId);

        ContractDetailDTO contractDetailDTO = new ContractDetailDTO();
        contractDetailDTO.setContractId(contract.getId());
        contractDetailDTO.setContractCode(contract.getContractCode());
        contractDetailDTO.setContractStatusCode(contract.getStatus());
        contractDetailDTO.setContractStatusName(ContractStatusEnum.from(contract.getStatus()).getTitle());
        contractDetailDTO.setStatusMessage(contract.getStatusMessage());
        contractDetailDTO.setCreatedTime(DateUtils.toStr(contract.getCreatedTime(), DateUtils.F_HHMMSSDDMMYYYY));
        contractDetailDTO.setStartDate(DateUtils.toStr(contract.getEffectDate(), DateUtils.F_DDMMYYYY));
        contractDetailDTO.setEndDate(DateUtils.toStr(contract.getEndDate(), DateUtils.F_DDMMYYYY));
        contractDetailDTO.setSignTime(DateUtils.toStr(contract.getSignedTime(), DateUtils.F_HHMMSSDDMMYYYY));
        contractDetailDTO.setPrice(contract.getActualPrice());

        UserProfile lessor = dataService.getUserByUsername(contract.getLessor());
        contractDetailDTO.setLessorFirstName(lessor.getFirstName());
        contractDetailDTO.setLessorLastName(lessor.getLastName());
        contractDetailDTO.setLessorPhoneNumber(lessor.getPhoneNumber());

        UserProfile tenant = dataService.getUserByUsername(contract.getTenant());
        contractDetailDTO.setTenantFirstName(tenant.getFirstName());
        contractDetailDTO.setTenantLastName(tenant.getLastName());
        contractDetailDTO.setTenantPhoneNumber(tenant.getPhoneNumber());

        House house = dataService.getHouse(contract.getHouseId());
        Room room = dataService.getRoom(contract.getRoomId());
        PositionDTO positionDTO = PositionDTO.builder()
                .detail(house.getPositionDetail())
                .ward(house.getWard())
                .district(house.getDistrict())
                .province(house.getProvince())
                .build();
        contractDetailDTO.setHouseId(house.getId());
        contractDetailDTO.setHouseName(house.getHouseName());
        contractDetailDTO.setRoomId(room.getId());
        contractDetailDTO.setRoomName(room.getRoomName());
        contractDetailDTO.setPosition(positionDTO);

        List<Utilities> utilities = utilitiesRepository.findAll();
        Map<String, String> utilityMap = utilities.stream().collect(Collectors.toMap(Utilities::getId, Utilities::getName));

        List<ContractUtilityDTO> contractUtilityDTOS = new ArrayList<>();
        List<ContractUtility> contractUtilities = contractUtilityRepository.findAllByContractId(contract.getId());
        if (!CollectionUtils.isEmpty(contractUtilities)) {
            contractUtilityDTOS = contractUtilities.stream()
                    .map(u -> ContractUtilityDTO.builder()
                            .utilityId(u.getUtilityId())
                            .utilityName(utilityMap.get(u.getUtilityId()))
                            .utilityPrice(u.getPrice())
                            .utilityUnit(u.getUnit())
                            .build())
                    .toList();
        }
        contractDetailDTO.setUtilities(contractUtilityDTOS);

        return contractDetailDTO;
    }

    public List<ContractUtilityDTO> getContractUtility(String contractId) {
        List<ContractUtilityDTO> contractUtilityDTOS = new ArrayList<>();
        List<ContractUtility> contractUtilities = contractUtilityRepository.findAllByContractId(contractId);
        if (!CollectionUtils.isEmpty(contractUtilities)) {
            List<Utilities> utilities = utilitiesRepository.findAll();
            Map<String, String> utilityMap = utilities.stream().collect(Collectors.toMap(Utilities::getId, Utilities::getName));
            contractUtilityDTOS = contractUtilities.stream()
                    .map(u -> ContractUtilityDTO.builder()
                            .utilityId(u.getUtilityId())
                            .utilityName(utilityMap.get(u.getUtilityId()))
                            .utilityPrice(u.getPrice())
                            .utilityUnit(u.getUnit())
                            .numberUsed(0)
                            .build())
                    .toList();
        }

        return contractUtilityDTOS;
    }

    public void requestEndContract(ContractEndReqDTO req) {
        Contract contract = dataService.getContract(req.getContractId());
        NotificationReqDTO notificationReqDTO = NotificationReqDTO.builder()
                .title("Yêu cầu kết thúc hợp đồng " + contract.getContractCode())
                .content(String.format("""
                        Bạn đã được yêu cầu kết thúc hợp đồng vào ngày %s
                        Lý do: %s
                        Bấm để xem chi tiết hợp đồng
                        """, req.getDateEnd(), req.getReason()))
                .data(JsonUtils.toJson(new NotificationType(NotificationTypeEnum.CONTRACT.name(), contract.getId())))
                .userReceive(contract.getLessor())
                .build();
        fcmService.sendNotificationToUser(notificationReqDTO);
    }

    public void endContract(ContractEndReqDTO req) {
        LocalDate now = LocalDate.now();
        Contract contract = dataService.getContract(req.getContractId());
        contract.setEndDate(req.getEndDate());
        if (req.getEndDate().isBefore(now) || req.getEndDate().equals(now)) {
            contract.setStatus(ContractStatusEnum.END.name());

            Room room = dataService.getRoom(contract.getRoomId());
            room.setRoomStatus(RoomStatusEnum.EMPTY.name());

            contractRepository.save(contract);
            roomRepository.save(room);
        }

        NotificationReqDTO notificationReqDTO = NotificationReqDTO.builder()
                .title("Hợp đồng " + contract.getContractCode() + " đã được kết thúc")
                .content(String.format("""
                        Hợp đồng %s của bạn đã được chủ trọ kết thúc vào ngày %s .
                        Bấm để xem chi tiết hợp đồng
                        """, contract.getContractCode(), DateUtils.toStr(req.getEndDate(), DateUtils.F_DDMMYYYY)))
                .data(JsonUtils.toJson(new NotificationType(NotificationTypeEnum.CONTRACT.name(), contract.getId())))
                .userReceive(contract.getTenant())
                .build();
        fcmService.sendNotificationToUser(notificationReqDTO);
    }
}
