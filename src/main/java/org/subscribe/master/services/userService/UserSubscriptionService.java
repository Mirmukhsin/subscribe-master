package org.subscribe.master.services.userService;

import org.subscribe.master.enums.SubscriptionStatus;
import org.subscribe.master.enums.SubscriptionType;

public interface UserSubscriptionService {
    void subscribe(Long subscriptionId, SubscriptionType subscriptionType);


    void changeStatus(Long subscriptionId, SubscriptionStatus status);
}
