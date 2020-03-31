package com.useraggregate.useraggregateapplication.adapters;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountServiceClient {

    ResponseEntity<List<Accounts>> getAccountFromAccountService(long id);
    ResponseEntity<Accounts> saveAccount(Accounts accounts);

}
