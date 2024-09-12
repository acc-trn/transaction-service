package com.acc.transactionservice.controller;

import com.acc.transactionservice.entity.Transaction;
import com.acc.transactionservice.model.TransactionDto;
import com.acc.transactionservice.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
    }

    @Test
    void createTransaction() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDescription("Test Transaction");
        transactionDto.setAmount(100.0);
        transactionDto.setCategory("Food");
        transactionDto.setType("INCOME");
        transactionDto.setAccountId(1L);
        transactionDto.setTransactionDate(LocalDateTime.now());

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.description").value("Test Transaction"))
                .andExpect(jsonPath("$.data.amount").value(100.0));
    }

    @Test
    void getTransactionById() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setDescription("Test Transaction");
        transaction.setAmount(100.0);
        transaction.setCategory("Food");
        transaction.setType("INCOME");
        transaction.setAccountId(1L);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction = transactionRepository.save(transaction);

        mockMvc.perform(get("/transactions/{id}", transaction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.description").value("Test Transaction"))
                .andExpect(jsonPath("$.data.amount").value(100.0));
    }

    @Test
    void getAllTransactions() throws Exception {
        Transaction transaction1 = new Transaction();
        transaction1.setDescription("Test Transaction 1");
        transaction1.setAmount(100.0);
        transaction1.setCategory("Food");
        transaction1.setType("INCOME");
        transaction1.setAccountId(1L);
        transaction1.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setDescription("Test Transaction 2");
        transaction2.setAmount(200.0);
        transaction2.setCategory("Entertainment");
        transaction2.setType("EXPENSE");
        transaction2.setAccountId(2L);
        transaction2.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction2);

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    void deleteTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setDescription("Test Transaction");
        transaction.setAmount(100.0);
        transaction.setCategory("Food");
        transaction.setType("INCOME");
        transaction.setAccountId(1L);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction = transactionRepository.save(transaction);

        mockMvc.perform(delete("/transactions/{id}", transaction.getId()))
                .andExpect(status().isNoContent());
    }
}