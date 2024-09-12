package com.acc.transactionservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mst_transaction")
public class Transaction extends AuditingEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double amount;

    private String category; // e.g., Food, Entertainment, Bills

    private String type; // INCOME or EXPENSE

    private Long accountId; // Reference to account in another service

    private LocalDateTime transactionDate;

}
