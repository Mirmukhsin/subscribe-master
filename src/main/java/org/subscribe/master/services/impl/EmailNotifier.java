package org.subscribe.master.services.impl;

import org.springframework.stereotype.Service;
import org.subscribe.master.entities.UserSubscription;
import org.subscribe.master.repositories.UserSubscriptionRepository;
import org.subscribe.master.services.NotificationService;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailNotifier implements NotificationService {
    private final UserSubscriptionRepository userSubscriptionRepository;

    public EmailNotifier(UserSubscriptionRepository userSubscriptionRepository) {
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    @Override
    public void sendNotification(Long subscriberId) {

    }

    @Override
    public List<UserSubscription> checkExpiration(Long subscriberId) {
        List<UserSubscription> expiredSubs = userSubscriptionRepository
                .findExpiredInDays(subscriberId, LocalDate.now().plusDays(2), false);
        return expiredSubs;
    }
}
