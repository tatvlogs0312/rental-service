package com.example.rentalservice.service.fcm;

import com.example.rentalservice.common.JwtUtils;
import com.example.rentalservice.entity.UserDevice;
import com.example.rentalservice.entity.UserNotification;
import com.example.rentalservice.model.fcm.FCMReq;
import com.example.rentalservice.model.fcm.NotificationReqDTO;
import com.example.rentalservice.model.search.PagingResponse;
import com.example.rentalservice.repository.UserDeviceRepository;
import com.example.rentalservice.repository.UserNotificationRepository;
import com.example.rentalservice.service.common.DataService;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserDeviceRepository userDeviceRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final DataService dataService;

    public void send(FCMReq req) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(req.getToken())
                .putData("priority", "high")
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .build())
                .setNotification(Notification.builder()
                        .setTitle("Hello")
                        .setBody("Test from spring")
                        .build())
                .build();
        firebaseMessaging.send(message);
    }

    public void sendNotificationToUser(NotificationReqDTO req) {
        try {
            log.info("Send notification");
            List<UserDevice> userDevices = userDeviceRepository.findAllByUsername(req.getUserReceive());
            if (!CollectionUtils.isEmpty(userDevices)) {
                List<String> tokenDevices = userDevices.stream().map(UserDevice::getDevice).filter(Objects::nonNull).distinct().toList();
                if (!CollectionUtils.isEmpty(tokenDevices)) {
                    MulticastMessage multicastMessage = MulticastMessage.builder()
                            .addAllTokens(tokenDevices)
                            .putData("priority", "high")
                            .setAndroidConfig(AndroidConfig.builder()
                                    .setPriority(AndroidConfig.Priority.HIGH)
                                    .build())
                            .setNotification(Notification.builder()
                                    .setTitle(req.getTitle())
                                    .setBody(req.getContent())
                                    .build())
                            .build();

                    firebaseMessaging.sendEachForMulticast(multicastMessage);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        UserNotification userNotification = new UserNotification();
        userNotification.setId(UUID.randomUUID().toString());
        userNotification.setUsername(req.getUserReceive());
        userNotification.setTitle(req.getTitle());
        userNotification.setContent(req.getContent());
        userNotification.setData(req.getData());
        userNotification.setIsRead(false);
        userNotification.setTimeSend(LocalDateTime.now());
        userNotificationRepository.save(userNotification);
    }

    public Long subscribe(String user, String token) {
        Optional<UserDevice> userDeviceOtp = userDeviceRepository
                .findFirstByUsernameAndDevice(user, token);
        if (userDeviceOtp.isEmpty()) {
            UserDevice userDevice = new UserDevice();
            userDevice.setId(UUID.randomUUID().toString());
            userDevice.setUsername(user);
            userDevice.setDevice(token);
            userDeviceRepository.save(userDevice);
        }

        return userNotificationRepository.countAllByUsernameAndIsRead(user, false);
    }

    public void unSubscribe(String user, String token) {
        Optional<UserDevice> userDeviceOtp = userDeviceRepository
                .findFirstByUsernameAndDevice(user, token);
        userDeviceOtp.ifPresent(userDevice -> userDeviceRepository.deleteById(userDevice.getId()));
    }

    public PagingResponse<UserNotification> getNotifications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        PagingResponse<UserNotification> res = new PagingResponse<>();
        Page<UserNotification> userNotifications = userNotificationRepository.findAllByUsernameOrderByTimeSendDesc(
                JwtUtils.getUsername(), pageable);
        if (userNotifications.hasContent()) {
            res.setData(userNotifications.getContent());
        } else {
            res.setData(new ArrayList<>());
        }
        res.setTotalPage(userNotifications.getTotalPages());
        res.setTotalData(userNotifications.getTotalElements());
        return res;
    }

    public void read(String id) {
        UserNotification userNotification = dataService.getUserNotification(id);
        userNotification.setIsRead(true);
        userNotificationRepository.save(userNotification);
    }
}
