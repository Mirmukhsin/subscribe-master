package org.subscribe.master.services.notificationService.impl;

import org.springframework.stereotype.Component;
import org.subscribe.master.dtos.UserSubsNotificationDTO;
import org.subscribe.master.services.notificationService.NotificationService;

import java.util.List;

@Component
public class LogNotifier implements NotificationService {
    @Override
    public void sendNotification(List<UserSubsNotificationDTO> notificationDTOS) {

    }

    @Override
    public void checkExpiration() {

    }
}
