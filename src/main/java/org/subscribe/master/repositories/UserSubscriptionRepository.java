package org.subscribe.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.subscribe.master.entities.UserSubscription;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    Optional<UserSubscription> findByIdAndSubscriberIdAndIsDeleted(Long subscriptionId, Long subscriberId, Boolean isDeleted);

    @Query("select us from UserSubscription us where us.subscriber.id = :subId and cast( us.nextPaymentDate as date ) = cast( :date as date ) and us.isDeleted = :deleted")
    List<UserSubscription> findExpiredInDays(Long subId, LocalDate date, Boolean deleted);
}
