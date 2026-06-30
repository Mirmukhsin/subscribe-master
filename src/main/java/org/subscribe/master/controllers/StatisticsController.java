package org.subscribe.master.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.subscribe.master.dtos.statisticsDTOs.MonthlyTotalDTO;
import org.subscribe.master.dtos.statisticsDTOs.MonthlyTrendDTO;
import org.subscribe.master.dtos.statisticsDTOs.MostExpensiveDTO;
import org.subscribe.master.services.statisticsService.StatisticsService;

import java.time.LocalDate;

@RestController
@RequestMapping("/users/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/mostExpSub")
    public ResponseEntity<MostExpensiveDTO> getMostExp(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {
        return new ResponseEntity<>(statisticsService.getMostExpSubs(month), HttpStatus.OK);
    }

    @GetMapping("/monthlyExpense")
    public ResponseEntity<MonthlyTotalDTO> getMonthlyExpense(@RequestParam LocalDate month) {
        return new ResponseEntity<>(statisticsService.getMonthlyExpense(month), HttpStatus.OK);
    }

    @GetMapping("/expenseInPeriod")
    public ResponseEntity<MonthlyTrendDTO> getMonthlyTrend(@RequestParam(defaultValue = "6") int period) {
        return new ResponseEntity<>(statisticsService.getMonthlyTrend(period), HttpStatus.OK);
    }
}
