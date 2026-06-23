package org.subscribe.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.subscribe.master.dtos.ReportDTO;
import org.subscribe.master.entities.PaymentHistory;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    @Query(
            "select new org.subscribe.master.dtos.ReportDTO(s.name,s.price, s.currency, ps.amount) from PaymentHistory ps inner join Subscription s " +
                    "on s.id = ps.subscription.id " +
                    "where ps.subscriber.id = :subscriberId and ps.reason = 'Paid' " +
                    "and cast( ps.paymentDate as date ) between cast( :startDate as date ) and cast( :endDate as date )"
    )
    List<ReportDTO> getForReport(Long subscriberId, LocalDate startDate, LocalDate endDate);
}
