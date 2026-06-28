package org.subscribe.master.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.subscribe.master.entities.Subscription;
import org.subscribe.master.enums.Currency;
import org.subscribe.master.services.subscriptionService.SubscriptionService;

@RestController
@RequestMapping("/subs")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Subscription>> getAllSubs(
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double lowPrice
    ) {
        return new ResponseEntity<>(subscriptionService.getAllSubscriptions(size, page, currency, maxPrice, lowPrice), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Subscription> get(@PathVariable Long id) {
        return new ResponseEntity<>(subscriptionService.get(id), HttpStatus.OK);
    }
}
