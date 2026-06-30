package org.subscribe.master.dtos;

import org.subscribe.master.enums.Currency;
import org.subscribe.master.enums.SubscriptionStatus;
import org.subscribe.master.enums.SubscriptionType;

import java.time.LocalDateTime;

public record UserSubscriptionsResponseDTO(
        String subscriptionName,
        Double price,
        Currency currency,
        String category,
        SubscriptionStatus status,
        SubscriptionType type,
        LocalDateTime startedDate,
        LocalDateTime nextPaymentDate
) {
}
