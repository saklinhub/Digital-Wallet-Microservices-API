package com.wallet.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @PostMapping("/send")
    public void sendNotification(@RequestParam Long userId, @RequestParam String message) {
        // Simulate email sending
        logger.info("Sending notification to User ID {}: {}", userId, message);
    }
}
