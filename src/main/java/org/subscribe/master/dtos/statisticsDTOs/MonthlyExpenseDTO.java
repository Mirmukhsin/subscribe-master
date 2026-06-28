package org.subscribe.master.dtos.statisticsDTOs;

import org.subscribe.master.enums.Currency;

import java.time.LocalDateTime;

public record MonthlyExpenseDTO(
        LocalDateTime date,
        String subName,
        Currency currency,
        Double subPrice,
        Double total,
        Double totalInUzs
) {
}
