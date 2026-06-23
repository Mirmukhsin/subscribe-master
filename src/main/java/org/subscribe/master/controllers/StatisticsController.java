package org.subscribe.master.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.subscribe.master.entities.Report;
import org.subscribe.master.services.ReportService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final ReportService reportService;

    public StatisticsController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Report>> getReport(@PathVariable(name = "id") Long subscriberId,
                                                  @RequestParam LocalDate startDate,
                                                  @RequestParam LocalDate endDate) {
        return new ResponseEntity<>(reportService.getAnnuallyReport(subscriberId, startDate, endDate), HttpStatus.OK);
    }
}
