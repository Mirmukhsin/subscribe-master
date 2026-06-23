package org.subscribe.master.services.impl;

import org.springframework.stereotype.Service;
import org.subscribe.master.dtos.ReportDTO;
import org.subscribe.master.entities.Report;
import org.subscribe.master.repositories.PaymentHistoryRepository;
import org.subscribe.master.services.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final PaymentHistoryRepository paymentHistoryRepository;

    public ReportServiceImpl(PaymentHistoryRepository paymentHistoryRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
    }


    @Override
    public List<Report> getAnnuallyReport(Long subscriberId, LocalDate startDate, LocalDate endDate) {
        List<Report> reports = new ArrayList<>();

        List<ReportDTO> forReport = paymentHistoryRepository.getForReport(subscriberId, startDate, endDate);

        for (ReportDTO dto : forReport) {
            reports.add(new Report(dto.getSubscriptionName(), dto.getPrice(), (dto.getPrice() * 12000.0), dto.getAmount()));
        }

        return reports;
    }

    @Override
    public void getStatistics(Long subscriberId) {
        
    }
}
