package com.wallet.wallet.service;

import com.wallet.wallet.entity.Wallet;
import com.wallet.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {

    @Autowired
    private WalletRepository repository;

    public Wallet createWallet(Long userId) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency("USD");
        return repository.save(wallet);
    }

    public Wallet getWalletByUserId(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user id: " + userId));
    }

    @Transactional
    public Wallet addMoney(Long userId, BigDecimal amount) {
        Wallet wallet = getWalletByUserId(userId);
        wallet.setBalance(wallet.getBalance().add(amount));
        return repository.save(wallet);
    }

    @Transactional
    public void updateBalance(Long userId, BigDecimal amount) {
        // Amount can be negative for deduction
        Wallet wallet = getWalletByUserId(userId);
        BigDecimal newBalance = wallet.getBalance().add(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        wallet.setBalance(newBalance);
        repository.save(wallet);
    }
}
