package com.wallet.transaction.service;

import com.wallet.transaction.client.NotificationClient;
import com.wallet.transaction.client.WalletClient;
import com.wallet.transaction.entity.Transaction;
import com.wallet.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private WalletClient walletClient;

    @Autowired
    private NotificationClient notificationClient;

    @Transactional
    public Transaction transfer(Long senderId, Long receiverId, BigDecimal amount) {
        // 1. Deduct from Sender
        walletClient.updateBalance(senderId, amount.negate());

        // 2. Add to Receiver
        walletClient.updateBalance(receiverId, amount);

        // 3. Save Transaction
        Transaction transaction = new Transaction();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setAmount(amount);
        transaction.setStatus("SUCCESS");
        transaction.setTimestamp(LocalDateTime.now());
        Transaction savedTransaction = repository.save(transaction);

        // 4. Send Notification (Async ideally, but sync for now)
        try {
            notificationClient.sendNotification(senderId, "Sent " + amount + " to " + receiverId);
            notificationClient.sendNotification(receiverId, "Received " + amount + " from " + senderId);
        } catch (Exception e) {
            // Log error but don't fail transaction
            System.err.println("Notification failed: " + e.getMessage());
        }

        return savedTransaction;
    }

    public List<Transaction> getHistory(Long userId) {
        List<Transaction> sent = repository.findBySenderId(userId);
        List<Transaction> received = repository.findByReceiverId(userId);
        sent.addAll(received);
        return sent;
    }
}
