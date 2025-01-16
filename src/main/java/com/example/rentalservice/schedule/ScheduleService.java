package com.example.rentalservice.schedule;

import com.example.rentalservice.common.DateUtils;
import com.example.rentalservice.common.JsonUtils;
import com.example.rentalservice.entity.Contract;
import com.example.rentalservice.entity.Room;
import com.example.rentalservice.enums.ContractStatusEnum;
import com.example.rentalservice.enums.NotificationTypeEnum;
import com.example.rentalservice.enums.RoomStatusEnum;
import com.example.rentalservice.model.fcm.NotificationReqDTO;
import com.example.rentalservice.model.fcm.NotificationType;
import com.example.rentalservice.repository.ContractRepository;
import com.example.rentalservice.repository.RoomRepository;
import com.example.rentalservice.service.common.DataService;
import com.example.rentalservice.service.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ContractRepository contractRepository;
    private final RoomRepository roomRepository;
    private final DataService dataService;
    private final FcmService fcmService;

    @Scheduled(fixedDelay = 1000 * 60)
    public void runUpdateRoomEndContract() {
        log.info("Run job update contract");
        LocalDate now = LocalDate.now();
        List<Contract> contracts = contractRepository.getContractEnd(now);
        log.info("Number of contract expire: " + contracts.size());
        if (!contracts.isEmpty()) {
            contracts.forEach(x -> {
                x.setStatus(ContractStatusEnum.END.name());
            });

            List<Room> rooms = roomRepository.findAllById(contracts.stream().map(Contract::getRoomId).toList());
            if (!rooms.isEmpty()) {
                rooms.forEach(x -> {
                    x.setRoomStatus(RoomStatusEnum.EMPTY.name());
                });

                roomRepository.saveAll(rooms);
            }

            contracts.forEach(c -> {
                NotificationReqDTO notificationReqDTO = NotificationReqDTO.builder()
                        .title("Hợp đồng " + c.getContractCode() + " đã hết hạn")
                        .content(String.format("""
                        Hợp đồng %s của bạn đã hết hạn vào ngày hôm nay.
                        Bấm để xem chi tiết hợp đồng
                        """, c.getContractCode()))
                        .data(JsonUtils.toJson(new NotificationType(NotificationTypeEnum.CONTRACT.name(), c.getId())))
                        .userReceive(c.getTenant())
                        .build();
                fcmService.sendNotificationToUser(notificationReqDTO);

                NotificationReqDTO notificationReqDTO2 = NotificationReqDTO.builder()
                        .title("Hợp đồng " + c.getContractCode() + " đã hết hạn")
                        .content(String.format("""
                        Hợp đồng %s đã hết hạn vào ngày hôm nay.
                        Bấm để xem chi tiết hợp đồng
                        """, c.getContractCode()))
                        .data(JsonUtils.toJson(new NotificationType(NotificationTypeEnum.CONTRACT.name(), c.getId())))
                        .userReceive(c.getLessor())
                        .build();
                fcmService.sendNotificationToUser(notificationReqDTO2);
            });

            contractRepository.saveAll(contracts);
        }
    }
}
