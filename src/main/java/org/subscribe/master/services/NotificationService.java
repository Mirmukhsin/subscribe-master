package org.subscribe.master.services;

import org.subscribe.master.entities.UserSubscription;

import java.util.List;

public interface NotificationService {

    void sendNotification(Long subscriberId);

    List<UserSubscription> checkExpiration(Long subscriberId);
}
