package com.acc.transactionservice.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private String error;
    private int status;

    public CustomException(String message, String error, int status) {
        super(message);
        this.error = error;
        this.status = status;
    }
}

