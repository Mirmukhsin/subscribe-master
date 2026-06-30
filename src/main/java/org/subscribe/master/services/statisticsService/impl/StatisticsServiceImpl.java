package org.subscribe.master.services.statisticsService.impl;

import org.springframework.stereotype.Service;
import org.subscribe.master.dtos.statisticsDTOs.MonthlyExpenseDTO;
import org.subscribe.master.dtos.statisticsDTOs.MonthlyTotalDTO;
import org.subscribe.master.dtos.statisticsDTOs.MonthlyTrendDTO;
import org.subscribe.master.dtos.statisticsDTOs.MostExpensiveDTO;
import org.subscribe.master.exceptionHandling.customExceptions.ResourceNotFoundException;
import org.subscribe.master.repositories.PaymentHistoryRepository;
import org.subscribe.master.services.statisticsService.StatisticsService;
import org.subscribe.master.utility.SecurityUtility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final SecurityUtility securityUtility;

    public StatisticsServiceImpl(PaymentHistoryRepository paymentHistoryRepository, SecurityUtility securityUtility) {
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.securityUtility = securityUtility;
    }

    @Override
    public MostExpensiveDTO getMostExpSubs(LocalDate month) {
        LocalDateTime from = month.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime to = month.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();

        List<MostExpensiveDTO> results = paymentHistoryRepository.getMostExp(securityUtility.getCurrentUserId(), from, to);

        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No payment found");
        }

        return results.getFirst();
    }

    @Override
    public MonthlyTotalDTO getMonthlyExpense(LocalDate month) {
        LocalDateTime from = month.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime to = month.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();

        List<MonthlyExpenseDTO> expenseDTOS = paymentHistoryRepository.getMonthlyExpense(securityUtility.getCurrentUserId(), from, to);

        if (expenseDTOS.isEmpty()) {
            throw new ResourceNotFoundException("Payment history not found for given month");
        }

        return new MonthlyTotalDTO(
                expenseDTOS.getFirst().date().getMonth(),
                expenseDTOS,
                expenseDTOS.stream().mapToDouble(MonthlyExpenseDTO::totalInUzs).sum()
        );
    }

    @Override
    public MonthlyTrendDTO getMonthlyTrend(int period) {
        LocalDateTime from = LocalDate.now().minusMonths(period).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime to = LocalDateTime.now();

        Map<Month, List<MonthlyExpenseDTO>> monthlyExpense = paymentHistoryRepository
                .getMonthlyExpense(securityUtility.getCurrentUserId(), from, to)
                .stream()
                .collect(Collectors.groupingBy(monthlyExpenseDTO -> monthlyExpenseDTO.date().getMonth()));

        if (monthlyExpense.isEmpty()) {
            throw new ResourceNotFoundException("Payment history not found for given period");
        }

        System.err.println(monthlyExpense);

        List<MonthlyTotalDTO> monthlyTotal = new ArrayList<>();

        monthlyExpense.forEach((date, dtos) -> {
            monthlyTotal.add(new MonthlyTotalDTO(
                    date,
                    dtos,
                    dtos.stream().mapToDouble(MonthlyExpenseDTO::totalInUzs).sum()
            ));
        });

        return new MonthlyTrendDTO(
                period,
                monthlyTotal.stream().sorted(Comparator.comparing(MonthlyTotalDTO::month)).toList(),
                monthlyTotal.stream().mapToDouble(MonthlyTotalDTO::monthlyTotalExpense).sum()
        );

    }
}
