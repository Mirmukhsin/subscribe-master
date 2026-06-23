package org.subscribe.master.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.subscribe.master.enums.SubscriptionStatus;
import org.subscribe.master.enums.SubscriptionType;
import org.subscribe.master.services.UserSubscriptionService;

@RestController
@RequestMapping("/users")
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;

    public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    @PostMapping("/{id}/subscribe/{subId}")
    public ResponseEntity<Void> subscribe(@PathVariable(name = "id") Long subscriberId,
                                          @RequestParam SubscriptionType subscriptionType,
                                          @PathVariable(name = "subId") Long subscriptionId) {
        userSubscriptionService.subscribe(subscriptionId, subscriptionType, subscriberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/unsubscribe/{subId}")
    public ResponseEntity<Void> changeStatus(@PathVariable(name = "id") Long subscriberId,
                                            @PathVariable(name = "subId") Long userSubscriptionId,
                                            @RequestParam SubscriptionStatus status) {
        userSubscriptionService.changeStatus(userSubscriptionId, subscriberId, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
