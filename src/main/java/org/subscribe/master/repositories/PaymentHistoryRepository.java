package org.subscribe.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.subscribe.master.dtos.statisticsDTOs.MonthlyExpenseDTO;
import org.subscribe.master.dtos.statisticsDTOs.MostExpensiveDTO;
import org.subscribe.master.dtos.reportDTOs.ReportDTO;
import org.subscribe.master.entities.PaymentHistory;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    @Query(
            "select new org.subscribe.master.dtos.reportDTOs.ReportDTO(" +
                    "ph.subscription.name,ph.subscription.price, ph.subscription.currency, sum(ph.amountInUZS)) " +
                    "from PaymentHistory ph " +
                    "where ph.subscriber.id = :subscriberId " +
                    "and ph.reason = 'ACTIVATED' " +
                    "and ph.paymentDate >= :from " +
                    "and ph.paymentDate <= :to " +
                    "group by ph.subscription.id, ph.subscription.name, ph.subscription.price, ph.subscription.currency"
    )
    List<ReportDTO> getForReport(Long subscriberId, LocalDateTime from, LocalDateTime to);

    @Query("select new org.subscribe.master.dtos.statisticsDTOs.MostExpensiveDTO(ph.subscription.name, ph.subscription.category, " +
            "ph.subscription.price, ph.subscription.currency,ph.amountInUZS, ph.paymentDate) " +
            "from PaymentHistory ph " +
            "where ph.subscriber.id = :subscriberId " +
            "and ph.reason = 'ACTIVATED'" +
            "and ph.paymentDate >= :from " +
            "and ph.paymentDate <= :to " +
            "order by ph.subscription.price desc ")
    List<MostExpensiveDTO> getMostExp(Long subscriberId, LocalDateTime from, LocalDateTime to);

    @Query("select new org.subscribe.master.dtos.statisticsDTOs.MonthlyExpenseDTO(ph.paymentDate,ph.subscription.name, ph.subscription.currency, ph.subscription.price, sum(ph.amountInUZS)) " +
            "from PaymentHistory ph " +
            "where ph.subscriber.id = :subscriberId " +
            "and ph.reason = 'ACTIVATED'" +
            "and ph.paymentDate >= :from " +
            "and ph.paymentDate <= :to " +
            "group by ph.paymentDate, ph.subscription.name, ph.subscription.currency, ph.subscription.price ")
    List<MonthlyExpenseDTO> getMonthlyExpense(Long subscriberId, LocalDateTime from, LocalDateTime to);
}
