package com.acc.transactionservice.service;

import com.acc.transactionservice.entity.Transaction;
import com.acc.transactionservice.exception.CustomException;
import com.acc.transactionservice.external.client.AccountClient;
import com.acc.transactionservice.external.model.AccountDto;
import com.acc.transactionservice.model.APIResponse;
import com.acc.transactionservice.model.TransactionDto;
import com.acc.transactionservice.model.mapper.TransactionMapper;
import com.acc.transactionservice.repository.TransactionRepository;
import com.acc.transactionservice.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountClient accountClient;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransaction() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountId(1L);
        transactionDto.setType("INCOME");
        transactionDto.setAmount(100.0);

        AccountDto accountDto = new AccountDto();
        accountDto.setBalance(1000.0);

        APIResponse<AccountDto> accountResponse = new APIResponse<>();
        accountResponse.setData(accountDto);

        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);
        transaction.setType("INCOME");

        when(accountClient.getAccountById(transactionDto.getAccountId())).thenReturn(accountResponse);
        when(transactionMapper.dtoToEntity(transactionDto)).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.mapToApiResponse(any(Transaction.class))).thenReturn(new APIResponse<>(transactionDto));

        APIResponse<TransactionDto> response = transactionService.createTransaction(transactionDto);

        assertNotNull(response);
        assertEquals(transactionDto, response.getData());
        verify(accountClient, times(1)).getAccountById(transactionDto.getAccountId());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountClient, times(1)).updateAccountBalance(transactionDto.getAccountId(), 1100.0);
    }

    @Test
    void getAllTransactions() {
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepository.findAll()).thenReturn(transactions);
        when(transactionMapper.mapListToApiResponse(transactions)).thenReturn(new APIResponse<>(Arrays.asList(new TransactionDto(), new TransactionDto())));

        APIResponse<List<TransactionDto>> response = transactionService.getAllTransactions();

        assertNotNull(response);
        assertEquals(2, response.getData().size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void getTransactionById() {
        Long id = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(id);

        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));
        when(transactionMapper.mapToApiResponse(transaction)).thenReturn(new APIResponse<>(new TransactionDto()));

        APIResponse<TransactionDto> response = transactionService.getTransactionById(id);

        assertNotNull(response);
        verify(transactionRepository, times(1)).findById(id);
    }

    @Test
    void getTransactionById_NotFound() {
        Long id = 1L;

        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> transactionService.getTransactionById(id));

        assertEquals("Transaction not found", exception.getMessage());
        verify(transactionRepository, times(1)).findById(id);
    }

    @Test
    void deleteTransaction() {
        Long id = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(id);

        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));

        APIResponse<String> response = transactionService.deleteTransaction(id);

        assertNotNull(response);
        assertEquals("Transaction deleted successfully", response.getData());
        verify(transactionRepository, times(1)).findById(id);
        verify(transactionRepository, times(1)).delete(transaction);
    }

    @Test
    void deleteTransaction_NotFound() {
        Long id = 1L;

        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> transactionService.deleteTransaction(id));

        assertEquals("Transaction not found", exception.getMessage());
        verify(transactionRepository, times(1)).findById(id);
    }
}