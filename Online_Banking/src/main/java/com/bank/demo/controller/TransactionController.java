package com.bank.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.demo.entity.Transaction;
import com.bank.demo.service.TransactionService;
import com.bank.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId));
    }

    @PostMapping("/process")
    public ResponseEntity<String> processTransaction(
            @RequestParam Long userId,
            @RequestParam Double amount,
            @RequestParam String type,
            @RequestParam String mpin) {
        
        if (!userService.validateMpin(userId, mpin)) {
            return ResponseEntity.status(403).body("Invalid MPIN");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(type);

        transaction.setUser(userService.getUserById(userId).orElseThrow());
        transaction = transactionService.createTransaction(transaction);

        try {
            userService.updateBalance(userId, amount, type);
            transactionService.completeTransaction(transaction, true);
            return ResponseEntity.ok("Transaction successful");
        } catch (Exception e) {
            transactionService.completeTransaction(transaction, false);
            return ResponseEntity.status(400).body("Transaction failed: " + e.getMessage());
        }
    }
}
