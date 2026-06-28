package org.subscribe.master.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.subscribe.master.enums.SubscriptionStatus;
import org.subscribe.master.enums.SubscriptionType;
import org.subscribe.master.services.userService.UserSubscriptionService;

@RestController
@RequestMapping("/users")
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;

    public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    @PostMapping("/subscribe/{subId}")
    public ResponseEntity<Void> subscribe(@RequestParam SubscriptionType subscriptionType,
                                          @PathVariable(name = "subId") Long subscriptionId) {
        userSubscriptionService.subscribe(subscriptionId, subscriptionType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/unsubscribe/{subId}")
    public ResponseEntity<Void> changeStatus(@PathVariable(name = "subId") Long userSubscriptionId,
                                             @RequestParam SubscriptionStatus status) {
        userSubscriptionService.changeStatus(userSubscriptionId, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
