package org.subscribe.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.subscribe.master.dtos.UserSubsNotificationDTO;
import org.subscribe.master.entities.UserSubscription;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    @Query("select count(us) > 0 from UserSubscription us " +
            "where us.subscription.id = :subscriptionId " +
            "and us.subscriber.id = :subscriberId " +
            "and us.isDeleted = :isDeleted")
    boolean existsSubscription(Long subscriptionId, Long subscriberId, Boolean isDeleted);

    @Query("select us from UserSubscription us " +
            "where us.id = :userSubscriptionId " +
            "and us.subscriber.id = :subscriberId " +
            "and us.isDeleted = :isDeleted")
    Optional<UserSubscription> findUserSubscription(Long userSubscriptionId, Long subscriberId, Boolean isDeleted);

    @Query("select new org.subscribe.master.dtos.UserSubsNotificationDTO(" +
            "us.subscriber.username, us.subscriber.email," +
            "us.subscription.name, us.subscription.currency, us.subscription.price, 0.0," +
            "us.startedDate, us.nextPaymentDate, us.type) from UserSubscription us " +
            "where us.nextPaymentDate >= :from and us.nextPaymentDate <= :to " +
            "and us.isDeleted = false ")
    List<UserSubsNotificationDTO> findExpiredInDays(LocalDateTime from, LocalDateTime to);

}
