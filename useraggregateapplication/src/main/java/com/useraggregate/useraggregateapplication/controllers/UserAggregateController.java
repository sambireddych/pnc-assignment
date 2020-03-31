package com.useraggregate.useraggregateapplication.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.useraggregate.useraggregateapplication.adapters.*;
import com.useraggregate.useraggregateapplication.jsonResponse.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
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


    @GetMapping(produces = "application/json",path = "/getAll")
    public ResponseEntity<?> getAllAccountUser() throws IOException {

        /*String token = userServiceClient.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+token);*/

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
    }


    @PostMapping(produces = "application/json", consumes = "application/json", path = "/create")
    public ResponseEntity<?> saveUserAndAccount(@RequestBody CustomerData customerData) throws IOException {

        String uniqueIdentification = customerData.getUser().getUniqueIdentification();
        Map<User, List<Accounts>> map = new HashMap<>();

        customerData.getAccounts().stream().forEach(accounts -> accounts.setUniqueIdentification(uniqueIdentification));
        User saveUser = userServiceClient.saveUser(customerData.getUser()).getBody();
        Accounts accounts = new Accounts();
        List<Accounts> accountsList = new ArrayList<>();
        for (int i = 0; i < customerData.getAccounts().size(); i++) {
            accounts = accountServiceClient.saveAccount(customerData.getAccounts().get(i)).getBody();
            if (customerData.getAccounts().size() > 1) {
                accountsList.add(accounts);
                map.put(saveUser, new ArrayList<>(accountsList));
            } else {
                accountsList.add(accounts);
                map.put(saveUser, accountsList);
            }
        }


        return new ResponseEntity<>(jsonResponse.printJsendFormat(map), HttpStatus.OK);
    }




}
