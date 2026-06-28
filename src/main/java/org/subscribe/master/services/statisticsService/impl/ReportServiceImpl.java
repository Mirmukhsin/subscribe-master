package org.subscribe.master.services.statisticsService.impl;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.subscribe.master.dtos.reportDTOs.Report;
import org.subscribe.master.exceptionHandling.customExceptions.ConflictException;
import org.subscribe.master.repositories.PaymentHistoryRepository;
import org.subscribe.master.services.statisticsService.ReportService;
import org.subscribe.master.utility.CurrencyConverter;
import org.subscribe.master.utility.SecurityUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final CurrencyConverter currencyConverter;
    private final SecurityUtility securityUtility;

    public ReportServiceImpl(PaymentHistoryRepository paymentHistoryRepository, CurrencyConverter currencyConverter, SecurityUtility securityUtility) {
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.currencyConverter = currencyConverter;
        this.securityUtility = securityUtility;
    }


    @Override
    public List<Report> getAnnuallyReport(LocalDate startDate, LocalDate endDate) {

        LocalDateTime from = startDate.atStartOfDay();
        LocalDateTime to = endDate.atStartOfDay();


        List<Report> reports = paymentHistoryRepository.getForReport(securityUtility.getCurrentUserId(), from, to)
                .stream()
                .map(dto -> new Report(
                        dto.getSubscriptionName(),
                        dto.getPrice(),
                        dto.getCurrency(),
                        currencyConverter.convertToUZS(
                                dto.getPrice(),
                                dto.getCurrency()
                        ),
                        dto.getAmount(),
                        currencyConverter.convertToUZS(
                                dto.getAmount(),
                                dto.getCurrency()
                        )
                )).toList();
        try {
            generatingReportInExcel(reports);
            return reports;
        } catch (Exception e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void generatingReportInExcel(List<Report> reports) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Subscription Report");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Price");
        row.createCell(2).setCellValue("In UZS");
        row.createCell(3).setCellValue("Total Expense");

        int dataRowIndex = 1;

        for (Report report : reports) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);

            dataRow.createCell(0).setCellValue(report.getSubscriptionName());
            dataRow.createCell(1).setCellValue(report.getPrice());
            dataRow.createCell(2).setCellValue(report.getPriceInMainCurrency());
            dataRow.createCell(3).setCellValue(report.getTotalExpense());

            dataRowIndex++;
        }

        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "report.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }
}
