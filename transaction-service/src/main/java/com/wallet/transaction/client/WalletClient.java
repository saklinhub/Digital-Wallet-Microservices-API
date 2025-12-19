package com.wallet.transaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "WALLET-SERVICE")
public interface WalletClient {

    @PutMapping("/wallets/{userId}/update-balance")
    void updateBalance(@PathVariable Long userId, @RequestParam BigDecimal amount);
}
