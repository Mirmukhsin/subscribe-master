package org.subscribe.master.services.impl;

import org.springframework.stereotype.Service;
import org.subscribe.master.entities.AuthUser;
import org.subscribe.master.entities.PaymentHistory;
import org.subscribe.master.entities.Subscription;
import org.subscribe.master.entities.UserSubscription;
import org.subscribe.master.enums.SubscriptionStatus;
import org.subscribe.master.enums.SubscriptionType;
import org.subscribe.master.exceptionHandling.customExceptions.ConflictException;
import org.subscribe.master.exceptionHandling.customExceptions.ResourceNotFoundException;
import org.subscribe.master.repositories.PaymentHistoryRepository;
import org.subscribe.master.repositories.SubscriptionRepository;
import org.subscribe.master.repositories.UserRepository;
import org.subscribe.master.repositories.UserSubscriptionRepository;
import org.subscribe.master.services.UserSubscriptionService;

import java.time.LocalDateTime;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final UserRepository userRepository;

    public UserSubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UserSubscriptionRepository userSubscriptionRepository, PaymentHistoryRepository paymentHistoryRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void subscribe(Long subscriptionId, SubscriptionType subscriptionType, Long subscriberId) {
        boolean existedUserSub = userSubscriptionRepository.findByIdAndSubscriberIdAndIsDeleted(subscriptionId, subscriberId, false).isPresent();
        if (existedUserSub){
            throw new ConflictException("You already subscribed");
        }

        UserSubscription userSubscription = new UserSubscription();

        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        userSubscription.setSubscription(subscription);
        //TODO: checccccccccccccccccck
        AuthUser subscriber = userRepository.findById(subscriberId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userSubscription.setSubscriber(subscriber);

        userSubscription.setType(subscriptionType);
        userSubscription.setStatus(SubscriptionStatus.ACTIVE);

        LocalDateTime now = LocalDateTime.now();
        switch (subscriptionType) {
            case WEEKLY -> userSubscription.setNextPaymentDate(now.plusWeeks(1));
            case MONTHLY -> userSubscription.setNextPaymentDate(now.plusMonths(1));
            case ANNUALLY -> userSubscription.setNextPaymentDate(now.plusYears(1));
        }

        userSubscriptionRepository.save(userSubscription);
        //TODO: checccccccccccccccccccccccccck
        paymentHistoryRepository.save(new PaymentHistory(subscription, subscriber, "Paid", subscription.getPrice()));
    }

    @Override
    public void changeStatus(Long userSubscriptionId, Long subscriberId, SubscriptionStatus status) {
        UserSubscription userSubscription = userSubscriptionRepository
                .findByIdAndSubscriberIdAndIsDeleted(userSubscriptionId, subscriberId, false)
                .orElseThrow(() -> new ResourceNotFoundException("Data not found"));

        if (status.equals(SubscriptionStatus.CANCELLED)) {
            userSubscription.setDeleted(true);
        }
        userSubscription.setStatus(status);

        userSubscriptionRepository.save(userSubscription);
        paymentHistoryRepository.save(new PaymentHistory(userSubscription.getSubscription(), userSubscription.getSubscriber(), status.name(), 0.0));
    }
}
