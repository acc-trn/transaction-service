package com.acc.transactionservice.controller;

import com.acc.transactionservice.model.APIResponse;
import com.acc.transactionservice.model.TransactionDto;
import com.acc.transactionservice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransaction() {
        TransactionDto transactionDto = new TransactionDto();
        APIResponse<TransactionDto> apiResponse = new APIResponse<>();
        when(transactionService.createTransaction(any(TransactionDto.class))).thenReturn(apiResponse);

        ResponseEntity<APIResponse<TransactionDto>> response = transactionController.createTransaction(transactionDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(transactionService, times(1)).createTransaction(any(TransactionDto.class));
    }

    @Test
    void getTransactionById() {
        Long id = 1L;
        APIResponse<TransactionDto> apiResponse = new APIResponse<>();
        when(transactionService.getTransactionById(id)).thenReturn(apiResponse);

        ResponseEntity<APIResponse<TransactionDto>> response = transactionController.getTransactionById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(transactionService, times(1)).getTransactionById(id);
    }

    @Test
    void getAllTransactions() {
        List<TransactionDto> transactionDtos = Arrays.asList(new TransactionDto(), new TransactionDto());
        APIResponse<List<TransactionDto>> apiResponse = new APIResponse<>(transactionDtos);
        when(transactionService.getAllTransactions()).thenReturn(apiResponse);

        ResponseEntity<APIResponse<List<TransactionDto>>> response = transactionController.getAllTransactions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    void updateTransaction() {
        Long id = 1L;
        TransactionDto transactionDto = new TransactionDto();
        APIResponse<TransactionDto> apiResponse = new APIResponse<>();
        when(transactionService.createTransaction(any(TransactionDto.class))).thenReturn(apiResponse);

        ResponseEntity<APIResponse<TransactionDto>> response = transactionController.updateTransaction(id, transactionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(transactionService, times(1)).createTransaction(any(TransactionDto.class));
    }

    @Test
    void deleteTransaction() {
        Long id = 1L;
        APIResponse<String> apiResponse = new APIResponse<>("Transaction deleted successfully");
        when(transactionService.deleteTransaction(id)).thenReturn(apiResponse);

        ResponseEntity<APIResponse<String>> response = transactionController.deleteTransaction(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(transactionService, times(1)).deleteTransaction(id);
    }
}