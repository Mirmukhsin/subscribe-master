package org.subscribe.master.dtos.statisticsDTOs;

import java.util.List;

public record MonthlyTrendDTO(
        Integer period,
        List<MonthlyTotalDTO> monthlyTotalDTOS,
        Double totalPeriodExpense

) {
}
