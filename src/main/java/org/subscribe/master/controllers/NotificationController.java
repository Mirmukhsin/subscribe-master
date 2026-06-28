package org.subscribe.master.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.subscribe.master.services.notificationService.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(@Qualifier("emailNotifier") NotificationService notificationService) {
        this.notificationService = notificationService;
    }

//    public NotificationController(@Qualifier("emailNotifier") NotificationService notificationService) {
//        this.notificationService = notificationService;
//    }
//
//    @GetMapping("/get/{id}")
//    public ResponseEntity<List<UserSubscription>> get(@PathVariable(name = "id") Long subscriberId) {
//        return new ResponseEntity<>(notificationService.checkExpiration(subscriberId), HttpStatus.OK);
//    }
}
