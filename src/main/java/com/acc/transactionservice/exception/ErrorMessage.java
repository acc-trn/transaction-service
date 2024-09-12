package com.acc.transactionservice.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {
    private String error; // ERROR (PRODUCT_NOT_FOUND)
    private String message; // Error Message
}
