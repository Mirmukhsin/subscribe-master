package org.subscribe.master.services.statisticsService;

import org.subscribe.master.dtos.reportDTOs.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<Report> getAnnuallyReport(LocalDate startDate, LocalDate endDate);
}
