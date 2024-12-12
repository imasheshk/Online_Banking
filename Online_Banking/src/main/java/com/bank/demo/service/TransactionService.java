package com.bank.demo.service;


import org.springframework.stereotype.Service;

import com.bank.demo.entity.Transaction;
import com.bank.demo.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Transaction createTransaction(Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("PENDING");
        return transactionRepository.save(transaction);
    }

    public void completeTransaction(Transaction transaction, boolean success) {
        transaction.setStatus(success ? "SUCCESS" : "FAILED");
        transactionRepository.save(transaction);
    }
}
