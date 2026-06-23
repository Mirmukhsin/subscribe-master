package org.subscribe.master.services;

import org.subscribe.master.enums.SubscriptionStatus;
import org.subscribe.master.enums.SubscriptionType;

public interface UserSubscriptionService {
    void subscribe(Long subscriptionId, SubscriptionType subscriptionType, Long subscriberId);


    void changeStatus(Long subscriptionId, Long subscriberId, SubscriptionStatus status);
}
