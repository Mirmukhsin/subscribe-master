package org.subscribe.master.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.subscribe.master.services.statisticsService.ReportService;

import java.time.LocalDate;

@RestController
@RequestMapping("/users")
public class HistoryReportController {
    private final ReportService reportService;

    public HistoryReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report/download")
    public ResponseEntity<byte[]> getReport(@RequestParam LocalDate startDate,
                                            @RequestParam LocalDate endDate) {

        byte[] excelBytes = reportService.getAnnuallyReport(startDate, endDate);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        ));
        httpHeaders.setContentDispositionFormData("attachment", "report-" + startDate + "-" + endDate + ".xlsx");

        return new ResponseEntity<>(excelBytes, httpHeaders, HttpStatus.OK);
    }
}
