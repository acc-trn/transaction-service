package com.acc.transactionservice.external.client;

import com.acc.transactionservice.external.model.APIResponse;
import com.acc.transactionservice.external.model.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-management-service", url = "/api/v1/accounts")
public interface AccountClient {

    @GetMapping("/{id}")
    APIResponse<AccountDto> getAccountById(@PathVariable("id") Long id);

    @PutMapping("/{id}/balance")
    APIResponse<AccountDto> updateAccountBalance(@PathVariable("id") Long id, @RequestParam Double newBalance);

}
