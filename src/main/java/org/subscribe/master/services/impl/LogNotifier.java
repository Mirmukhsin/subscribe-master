package org.subscribe.master.services.impl;

import org.springframework.stereotype.Component;
import org.subscribe.master.entities.UserSubscription;
import org.subscribe.master.services.NotificationService;

import java.util.List;

@Component
public class LogNotifier implements NotificationService {
    @Override
    public void sendNotification(Long subscriberId) {

    }

    @Override
    public List<UserSubscription> checkExpiration(Long subscriberId) {
        return List.of();
    }
}
