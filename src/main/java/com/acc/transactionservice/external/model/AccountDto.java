package com.acc.transactionservice.external.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private String accountName;

    private Double balance;

    private List<BankAccountDto> bankAccounts;

    private List<CreditCardDto> creditCards;

}