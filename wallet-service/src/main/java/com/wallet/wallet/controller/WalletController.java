package com.wallet.wallet.controller;

import com.wallet.wallet.entity.Wallet;
import com.wallet.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletService service;

    @PostMapping("/{userId}")
    public Wallet createWallet(@PathVariable Long userId, @RequestHeader(value = "loggedInUser", required = false) Long loggedInUser) {
        validateUser(userId, loggedInUser, true);
        return service.createWallet(userId);
    }

    @GetMapping("/{userId}")
    public Wallet getWallet(@PathVariable Long userId, @RequestHeader(value = "loggedInUser", required = false) Long loggedInUser) {
        validateUser(userId, loggedInUser, true);
        return service.getWalletByUserId(userId);
    }

    @PostMapping("/{userId}/add-money")
    public Wallet addMoney(@PathVariable Long userId, @RequestParam BigDecimal amount, @RequestHeader(value = "loggedInUser", required = false) Long loggedInUser) {
        validateUser(userId, loggedInUser, true);
        return service.addMoney(userId, amount);
    }

    @PutMapping("/{userId}/update-balance")
    public void updateBalance(@PathVariable Long userId, @RequestParam BigDecimal amount, @RequestHeader(value = "loggedInUser", required = false) Long loggedInUser) {
        validateUser(userId, loggedInUser, false);
        service.updateBalance(userId, amount);
    }

    private void validateUser(Long userId, Long loggedInUser, boolean strict) {
        if (strict && loggedInUser == null) {
            throw new RuntimeException("Unauthorized: Login required");
        }
        if (loggedInUser != null && !userId.equals(loggedInUser)) {
            throw new RuntimeException("Unauthorized access to wallet");
        }
    }
}
