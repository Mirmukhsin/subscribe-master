package org.subscribe.master.services;

import org.subscribe.master.entities.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<Report> getAnnuallyReport(Long subscriberId, LocalDate startDate, LocalDate endDate);

    void getStatistics(Long subscriberId);
}
