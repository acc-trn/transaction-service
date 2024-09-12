package com.acc.transactionservice.external.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {

    private String cardNumber;

    private String cardHolderName;

    private Double creditLimit;

}
