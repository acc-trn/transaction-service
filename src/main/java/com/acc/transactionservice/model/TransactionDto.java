package com.acc.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Long id;

    private String description;

    private Double amount;

    private String category;

    private String type;

    private Long accountId;

    private LocalDateTime transactionDate;

}
