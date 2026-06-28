package org.subscribe.master.services.statisticsService;

import org.subscribe.master.dtos.statisticsDTOs.MonthlyTotalDTO;
import org.subscribe.master.dtos.statisticsDTOs.MonthlyTrendDTO;
import org.subscribe.master.dtos.statisticsDTOs.MostExpensiveDTO;

import java.time.LocalDate;

public interface StatisticsService {

    MostExpensiveDTO getMostExpSubs(LocalDate month);

    MonthlyTotalDTO getMonthlyExpense(LocalDate month);

    MonthlyTrendDTO getMonthlyTrend(int period);
}
