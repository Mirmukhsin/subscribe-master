package org.subscribe.master.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.subscribe.master.dtos.reportDTOs.Report;
import org.subscribe.master.services.statisticsService.ReportService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class HistoryReportController {
    private final ReportService reportService;

    public HistoryReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public ResponseEntity<List<Report>> getReport(@RequestParam LocalDate startDate,
                                                  @RequestParam LocalDate endDate) {
        return new ResponseEntity<>(reportService.getAnnuallyReport(startDate, endDate), HttpStatus.OK);
    }
}
