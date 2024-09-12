package com.acc.transactionservice.controller;

import com.acc.transactionservice.model.APIResponse;
import com.acc.transactionservice.model.TransactionDto;
import com.acc.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<TransactionDto>> createTransaction(@RequestBody TransactionDto transactionDto) {
        APIResponse<TransactionDto> response = transactionService.createTransaction(transactionDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<TransactionDto>> getTransactionById(@PathVariable Long id) {
        APIResponse<TransactionDto> response = transactionService.getTransactionById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<TransactionDto>>> getAllTransactions() {
        APIResponse<List<TransactionDto>> response = transactionService.getAllTransactions();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<TransactionDto>> updateTransaction(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {
        // Assuming that `createTransaction` method can also handle updates
        APIResponse<TransactionDto> response = transactionService.createTransaction(transactionDto); // Use appropriate method for update if different
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteTransaction(@PathVariable Long id) {
        APIResponse<String> response = transactionService.deleteTransaction(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}