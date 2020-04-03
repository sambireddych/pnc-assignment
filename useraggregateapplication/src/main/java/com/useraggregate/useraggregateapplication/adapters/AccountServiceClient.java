package com.useraggregate.useraggregateapplication.adapters;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountServiceClient {

    ResponseEntity<List<Accounts>> getAccountFromAccountService(String uid);
    ResponseEntity<Accounts> saveAccount(Accounts accounts);

}
