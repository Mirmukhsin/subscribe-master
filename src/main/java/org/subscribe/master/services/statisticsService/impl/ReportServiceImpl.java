package org.subscribe.master.services.statisticsService.impl;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.subscribe.master.dtos.reportDTOs.Report;
import org.subscribe.master.dtos.reportDTOs.ReportDTO;
import org.subscribe.master.exceptionHandling.customExceptions.ConflictException;
import org.subscribe.master.exceptionHandling.customExceptions.ResourceNotFoundException;
import org.subscribe.master.repositories.PaymentHistoryRepository;
import org.subscribe.master.services.statisticsService.ReportService;
import org.subscribe.master.utility.mappers.ReportMapper;
import org.subscribe.master.utility.SecurityUtility;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final SecurityUtility securityUtility;
    private final ReportMapper reportMapper;

    public ReportServiceImpl(PaymentHistoryRepository paymentHistoryRepository, SecurityUtility securityUtility, ReportMapper reportMapper) {
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.securityUtility = securityUtility;
        this.reportMapper = reportMapper;
    }


    @Override
    public byte[] getAnnuallyReport(LocalDate startDate, LocalDate endDate) {

        LocalDateTime from = startDate.atStartOfDay();
        LocalDateTime to = endDate.atStartOfDay();

        List<ReportDTO> forReport = paymentHistoryRepository.getForReport(securityUtility.getCurrentUserId(), from, to);
        System.err.println(forReport);
        if (forReport.isEmpty()) {
            throw new ResourceNotFoundException("Subscription payment history not found for given period");
        }

        List<Report> reports = forReport
                .stream().map(reportDTO ->
                {
                    Report report = reportMapper.reportDtoToReport(reportDTO);
                    report.setTotalExpenseInMainCurrency(reportDTO.getAmountInUZS());
                    return report;
                }).toList();
        try {
            return generatingExcelBytes(reports);
        } catch (Exception e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public byte[] generatingExcelBytes(List<Report> reports) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Subscription Report");
        XSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("Subscription Name");
        row.createCell(1).setCellValue("Price");
        row.createCell(4).setCellValue("Total Expense in UZS");

        int dataRowIndex = 1;

        for (Report report : reports) {
            XSSFRow dataRow = sheet.createRow(dataRowIndex);

            dataRow.createCell(0).setCellValue(report.getSubscriptionName());
            dataRow.createCell(1).setCellValue(report.getPrice() + "    " + report.getCurrency());
            dataRow.createCell(2).setCellValue(report.getTotalExpenseInMainCurrency() + "   so`m");

            dataRowIndex++;
        }

        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
