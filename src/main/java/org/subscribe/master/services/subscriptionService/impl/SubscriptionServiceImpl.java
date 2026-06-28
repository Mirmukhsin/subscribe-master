package org.subscribe.master.services.subscriptionService.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.subscribe.master.entities.Subscription;
import org.subscribe.master.enums.Currency;
import org.subscribe.master.exceptionHandling.customExceptions.ResourceNotFoundException;
import org.subscribe.master.repositories.SubscriptionRepository;
import org.subscribe.master.services.subscriptionService.SubscriptionService;
import org.subscribe.master.utility.SubscriptionSpecification;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Page<Subscription> getAllSubscriptions(int size, int page, Currency currency, Double maxPrice, Double lowPrice) {

        Specification<Subscription> spec = Specification.not(null);

        if (currency != null) {
            spec = spec.and(SubscriptionSpecification.hasCurrency(currency));
        }

        if (maxPrice != null) {
            spec = spec.and(SubscriptionSpecification.hasPriceLessThan(maxPrice));
        }

        if (lowPrice != null) {
            spec = spec.and(SubscriptionSpecification.hasPriceGreaterThan(lowPrice));
        }

        Pageable pageable = PageRequest.of(page, size);

        return subscriptionRepository.findAll(spec, pageable);
    }

    @Override
    public Subscription get(Long id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subscription found"));
    }
}
