package com.acc.transactionservice.entity;

import com.acc.transactionservice.model.TransactionType;
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

    private Double amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // INCOME, EXPENSE, TRANSFER

    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @ManyToOne
//    @JoinColumn(name = "account_id")
//    private Account account;

}
