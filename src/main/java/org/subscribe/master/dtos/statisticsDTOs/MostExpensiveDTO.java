package org.subscribe.master.dtos.statisticsDTOs;

import org.subscribe.master.enums.Currency;

import java.time.LocalDateTime;

public record MostExpensiveDTO(
        String name,
        String category,
        Double price,
        Currency currency,
        Double priceInUzs,
        LocalDateTime paymentDate
) {
}
