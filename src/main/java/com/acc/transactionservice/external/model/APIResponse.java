package com.acc.transactionservice.external.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
    private String status = CommonStatus.SUCCESS.name(); // SUCCESS or FAILED
    private List<String> message = new ArrayList<>();// Error Message[Java Validation]
    private T data;

    public APIResponse(T data) {
        this.data = data;
    }

    public APIResponse(List<String> message) {
        this.status = CommonStatus.FAILED.name();
        this.message = message;
    }
}
