package org.subscribe.master.services.statisticsService;

import java.time.LocalDate;

public interface ReportService {

    byte[] getAnnuallyReport(LocalDate startDate, LocalDate endDate);
}
