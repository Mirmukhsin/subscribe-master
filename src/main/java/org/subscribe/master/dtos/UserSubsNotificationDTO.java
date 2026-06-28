package org.subscribe.master.dtos;

import org.subscribe.master.enums.Currency;
import org.subscribe.master.enums.SubscriptionType;

import java.time.LocalDateTime;

public record UserSubsNotificationDTO(
        String username,
        String email,
        String subName,
        Currency currency,
        Double price,
        Double priceInUzs,
        LocalDateTime startedDate,
        LocalDateTime nextPaymentDate,
        SubscriptionType type
) {
}
