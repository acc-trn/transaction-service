package com.acc.transactionservice.service.impl;

import com.acc.transactionservice.entity.Transaction;
import com.acc.transactionservice.exception.CustomException;
import com.acc.transactionservice.external.client.AccountClient;
import com.acc.transactionservice.external.model.AccountDto;
import com.acc.transactionservice.model.APIResponse;
import com.acc.transactionservice.model.TransactionDto;
import com.acc.transactionservice.model.mapper.TransactionMapper;
import com.acc.transactionservice.repository.TransactionRepository;
import com.acc.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountClient accountClient;

    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountClient accountClient, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountClient = accountClient;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public APIResponse<TransactionDto> createTransaction(TransactionDto transactionDto) {
        // Get account to verify it exists
        APIResponse<AccountDto> accountResponse = accountClient.getAccountById(transactionDto.getAccountId());

        if (accountResponse.getData() == null) {
            throw new CustomException("Account not found", "ACCOUNT_NOT_FOUND", HttpStatus.NOT_FOUND.value());
        }

        // Create and save transaction
        Transaction transaction = transactionMapper.dtoToEntity(transactionDto);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction = transactionRepository.save(transaction);

        // Update balance in account
        Double newBalance = calculateNewBalance(accountResponse.getData().getBalance(), transaction);
        accountClient.updateAccountBalance(transactionDto.getAccountId(), newBalance);

        return transactionMapper.mapToApiResponse(transaction);
    }

    @Override
    public APIResponse<List<TransactionDto>> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionMapper.mapListToApiResponse(transactions);
    }

    @Override
    public APIResponse<TransactionDto> getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new CustomException("Transaction not found", "TRANSACTION_NOT_FOUND", HttpStatus.NOT_FOUND.value()));
        return transactionMapper.mapToApiResponse(transaction);
    }

    @Override
    public APIResponse<String> deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new CustomException("Transaction not found", "TRANSACTION_NOT_FOUND", HttpStatus.NOT_FOUND.value()));
        transactionRepository.delete(transaction);
        return new APIResponse<>("Transaction deleted successfully");
    }

    private Double calculateNewBalance(Double currentBalance, Transaction transaction) {
        if (transaction.getType().equals("INCOME")) {
            return currentBalance + transaction.getAmount();
        } else if (transaction.getType().equals("EXPENSE")) {
            return currentBalance - transaction.getAmount();
        }
        return currentBalance;
    }
}

