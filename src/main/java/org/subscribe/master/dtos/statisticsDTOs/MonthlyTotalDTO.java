package org.subscribe.master.dtos.statisticsDTOs;

import java.time.Month;
import java.util.List;

public record MonthlyTotalDTO(
        Month month,
        List<MonthlyExpenseDTO> monthlyExpenseDTOS,
        Double monthlyTotalExpense
) {
}
