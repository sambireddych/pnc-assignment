package com.useraggregate.useraggregateapplication.adapters;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServiceClient {

    ResponseEntity<List<User>> getUserDetails();

    ResponseEntity<User> saveUser();

}
