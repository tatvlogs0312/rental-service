package com.example.rentalservice.service.fcm;

import com.example.rentalservice.entity.UserDevice;
import com.example.rentalservice.entity.UserNotification;
import com.example.rentalservice.model.fcm.FCMReq;
import com.example.rentalservice.model.fcm.NotificationReqDTO;
import com.example.rentalservice.repository.UserDeviceRepository;
import com.example.rentalservice.repository.UserNotificationRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserDeviceRepository userDeviceRepository;
    private final UserNotificationRepository userNotificationRepository;

    public void send(FCMReq req) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(req.getToken())
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

    public void subscribe(String user, String token) {
        Optional<UserDevice> userDeviceOtp = userDeviceRepository
                .findFirstByUsernameAndDevice(user, token);
        if (userDeviceOtp.isEmpty()) {
            UserDevice userDevice = new UserDevice();
            userDevice.setId(UUID.randomUUID().toString());
            userDevice.setUsername(user);
            userDevice.setDevice(token);
            userDeviceRepository.save(userDevice);
        }
    }

    public void unSubscribe(String user, String token) {
        Optional<UserDevice> userDeviceOtp = userDeviceRepository
                .findFirstByUsernameAndDevice(user, token);
        userDeviceOtp.ifPresent(userDevice -> userDeviceRepository.deleteById(userDevice.getId()));
    }
}
