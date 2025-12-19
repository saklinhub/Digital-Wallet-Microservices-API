package com.wallet.transaction.controller;

import com.wallet.transaction.entity.Transaction;
import com.wallet.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/transfer")
    public Transaction transfer(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam BigDecimal amount, @RequestHeader(value = "loggedInUser") Long loggedInUser) {
        if (!senderId.equals(loggedInUser)) {
             throw new RuntimeException("Unauthorized: Can only transfer from your own wallet");
        }
        return service.transfer(senderId, receiverId, amount);
    }

    @GetMapping("/history/{userId}")
    public List<Transaction> getHistory(@PathVariable Long userId, @RequestHeader(value = "loggedInUser") Long loggedInUser) {
        if (!userId.equals(loggedInUser)) {
            throw new RuntimeException("Unauthorized: Can only view your own history");
        }
        return service.getHistory(userId);
    }
}
