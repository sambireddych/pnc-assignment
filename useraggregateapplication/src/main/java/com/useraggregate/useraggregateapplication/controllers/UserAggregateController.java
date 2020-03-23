package com.useraggregate.useraggregateapplication.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.useraggregate.useraggregateapplication.adapters.AccountServiceClient;
import com.useraggregate.useraggregateapplication.adapters.Accounts;
import com.useraggregate.useraggregateapplication.adapters.User;
import com.useraggregate.useraggregateapplication.adapters.UserServiceClient;
import com.useraggregate.useraggregateapplication.jsonResponse.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userAccounts")
public class UserAggregateController {


    @Autowired
    private AccountServiceClient accountServiceClient;
    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JsonResponse jsonResponse;

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllAccountUser() throws IOException {

        ResponseEntity<List<User>> userList = userServiceClient.getUserDetails();
        ResponseEntity<List<Accounts>> accountsList = null;
        Map<User, List<Accounts>> map = new HashMap<>();



        for (User user : userList.getBody()) {

            /*if(map.containsKey(user)){
                accountsList = accountServiceClient.getAccountFromAccountService(user.getId());
                map.putIfAbsent(objectMapper.writeValueAsString(user),new ArrayList<>(accountsList.getBody()));
            }else{
            accountsList = accountServiceClient.getAccountFromAccountService(user.getId());
            map.put(objectMapper.writeValueAsString(user),new ArrayList<>(accountsList.getBody()));}
        }*/
            accountsList = accountServiceClient.getAccountFromAccountService(user.getId());
            map.put(user, new ArrayList<>(accountsList.getBody()));
        }

        return new ResponseEntity<>(jsonResponse.printJsendFormat(map), HttpStatus.OK);
//        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(map),HttpStatus.OK);
    }


}
