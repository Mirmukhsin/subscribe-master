package org.subscribe.master.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.subscribe.master.dtos.UserSubscriptionsResponseDTO;
import org.subscribe.master.enums.SubscriptionStatus;
import org.subscribe.master.enums.SubscriptionType;
import org.subscribe.master.services.userService.UserSubscriptionService;

@RestController
@RequestMapping("/users")
@Tag(name = "User Subscriptions API")
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;

    public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    @Operation(summary = "Subscribing a subscription by ID")
    @PostMapping("/subscribe/{subId}")
    public ResponseEntity<UserSubscriptionsResponseDTO> subscribe(@RequestParam SubscriptionType subscriptionType,
                                                                  @PathVariable(name = "subId") Long subscriptionId) {
        return new ResponseEntity<>(userSubscriptionService.subscribe(subscriptionId, subscriptionType), HttpStatus.OK);
    }

    @Operation(summary = "Unsubscribe a subscription")
    @PutMapping("/unsubscribe/{subId}")
    public ResponseEntity<Void> changeStatus(@PathVariable(name = "subId") Long userSubscriptionId,
                                             @RequestParam SubscriptionStatus status) {
        userSubscriptionService.changeStatus(userSubscriptionId, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
