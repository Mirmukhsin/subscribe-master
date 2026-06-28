package org.subscribe.master.services.statisticsService.impl;

import org.springframework.stereotype.Service;
import org.subscribe.master.dtos.statisticsDTOs.MonthlyExpenseDTO;
import org.subscribe.master.dtos.statisticsDTOs.MonthlyTotalDTO;
import org.subscribe.master.dtos.statisticsDTOs.MonthlyTrendDTO;
import org.subscribe.master.dtos.statisticsDTOs.MostExpensiveDTO;
import org.subscribe.master.exceptionHandling.customExceptions.ResourceNotFoundException;
import org.subscribe.master.repositories.PaymentHistoryRepository;
import org.subscribe.master.services.statisticsService.StatisticsService;
import org.subscribe.master.utility.CurrencyConverter;
import org.subscribe.master.utility.SecurityUtility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final CurrencyConverter currencyConverter;
    private final SecurityUtility securityUtility;

    public StatisticsServiceImpl(PaymentHistoryRepository paymentHistoryRepository, CurrencyConverter currencyConverter, SecurityUtility securityUtility) {
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.currencyConverter = currencyConverter;
        this.securityUtility = securityUtility;
    }

    @Override
    public MostExpensiveDTO getMostExpSubs(LocalDate month) {
        LocalDateTime from = month.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime to = month.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay();

        List<MostExpensiveDTO> results = paymentHistoryRepository.getMostExp(securityUtility.getCurrentUserId(), from, to);

        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No payment found");
        }

        MostExpensiveDTO mostExp = results.getFirst();
        Double priceInUzs = currencyConverter.convertToUZS(mostExp.price(), mostExp.currency());

        return new MostExpensiveDTO(
                mostExp.name(),
                mostExp.category(),
                mostExp.price(),
                mostExp.currency(),
                priceInUzs
        );
    }

    @Override
    public MonthlyTotalDTO getMonthlyExpense(LocalDate month) {
        LocalDateTime from = month.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime to = month.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay();

        List<MonthlyExpenseDTO> expenseDTOS = paymentHistoryRepository.getMonthlyExpense(securityUtility.getCurrentUserId(), from, to).stream().map(monthlyExpenseDTO ->
                new MonthlyExpenseDTO(
                        monthlyExpenseDTO.date(),
                        monthlyExpenseDTO.subName(),
                        monthlyExpenseDTO.currency(),
                        monthlyExpenseDTO.subPrice(),
                        monthlyExpenseDTO.total(),
                        currencyConverter.convertToUZS(monthlyExpenseDTO.total(), monthlyExpenseDTO.currency())
                )).toList();

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

        Map<LocalDateTime, List<MonthlyExpenseDTO>> monthlyExpense = paymentHistoryRepository.getMonthlyExpense(securityUtility.getCurrentUserId(), from, to)
                .stream().map(monthlyExpenseDTO ->
                        new MonthlyExpenseDTO(
                                monthlyExpenseDTO.date(),
                                monthlyExpenseDTO.subName(),
                                monthlyExpenseDTO.currency(),
                                monthlyExpenseDTO.subPrice(),
                                monthlyExpenseDTO.total(),
                                currencyConverter.convertToUZS(monthlyExpenseDTO.total(), monthlyExpenseDTO.currency())
                        ))
                .collect(Collectors.groupingBy(MonthlyExpenseDTO::date));

        List<MonthlyTotalDTO> monthlyTotal = new ArrayList<>();

        monthlyExpense.forEach((date, dtos) -> {
            monthlyTotal.add(new MonthlyTotalDTO(
                    date.getMonth(),
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
