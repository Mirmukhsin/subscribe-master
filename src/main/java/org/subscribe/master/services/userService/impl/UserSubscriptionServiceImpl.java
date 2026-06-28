package org.subscribe.master.services.userService.impl;

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
import org.subscribe.master.services.userService.UserSubscriptionService;
import org.subscribe.master.utility.SecurityUtility;

import java.time.LocalDateTime;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final UserRepository userRepository;
    private final SecurityUtility securityUtility;

    public UserSubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UserSubscriptionRepository userSubscriptionRepository, PaymentHistoryRepository paymentHistoryRepository, UserRepository userRepository, SecurityUtility securityUtility) {
        this.subscriptionRepository = subscriptionRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.userRepository = userRepository;
        this.securityUtility = securityUtility;
    }

    @Override
    public void subscribe(Long subscriptionId, SubscriptionType subscriptionType) {
        boolean existedUserSub = userSubscriptionRepository.existsSubscription(subscriptionId, securityUtility.getCurrentUserId(), false);
        if (existedUserSub) {
            throw new ConflictException("You already subscribed");
        }

        UserSubscription userSubscription = new UserSubscription();

        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        userSubscription.setSubscription(subscription);

        AuthUser subscriber = userRepository.findById(securityUtility.getCurrentUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
        paymentHistoryRepository.save(new PaymentHistory(subscription, subscriber, "PAID", subscription.getPrice()));
    }

    @Override
    public void changeStatus(Long userSubscriptionId, SubscriptionStatus status) {
        UserSubscription userSubscription = userSubscriptionRepository
                .findUserSubscription(userSubscriptionId, securityUtility.getCurrentUserId(), false)
                .orElseThrow(() -> new ResourceNotFoundException("Data not found"));

        if (status.equals(SubscriptionStatus.CANCELLED)) {
            userSubscription.setDeleted(true);
        }
        userSubscription.setStatus(status);

        userSubscriptionRepository.save(userSubscription);
        paymentHistoryRepository.save(new PaymentHistory(userSubscription.getSubscription(), userSubscription.getSubscriber(), status.name(), 0.0));
    }
}
