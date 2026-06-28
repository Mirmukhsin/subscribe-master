package org.subscribe.master.services.notificationService;

import org.subscribe.master.dtos.UserSubsNotificationDTO;

import java.util.List;

public interface NotificationService {

    void sendNotification(List<UserSubsNotificationDTO> notificationDTOS);

    void checkExpiration();
}
