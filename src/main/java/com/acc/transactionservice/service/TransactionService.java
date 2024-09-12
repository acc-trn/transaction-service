package com.acc.transactionservice.service;

import com.acc.transactionservice.model.APIResponse;
import com.acc.transactionservice.model.TransactionDto;

import java.util.List;

public interface TransactionService {

    APIResponse<TransactionDto> createTransaction(TransactionDto transactionDto);

    APIResponse<List<TransactionDto>> getAllTransactions();

    APIResponse<TransactionDto> getTransactionById(Long id);

    APIResponse<String> deleteTransaction(Long id);
}

