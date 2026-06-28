package org.subscribe.master.services.subscriptionService;

import org.springframework.data.domain.Page;
import org.subscribe.master.entities.Subscription;
import org.subscribe.master.enums.Currency;

public interface SubscriptionService {

    Page<Subscription> getAllSubscriptions(int size, int page, Currency currency, Double maxPrice, Double lowPrice);

    Subscription get(Long id);
}
