package com.useraggregate.useraggregateapplication.adapters;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Component
public class AccountServiceClientImpl implements AccountServiceClient {

    private RestTemplate restTemplate;
    private AccountServiceClientConfig config;


    public AccountServiceClientImpl(AccountServiceClientConfig config, RestTemplateBuilder restTemplateBuilder) {
        this.config = config;
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }


    @Override
    public ResponseEntity<List<Accounts>> getAccountFromAccountService(String uid) {
        String accountUrl = config.getUrl() + "/accounts/user?uid=" + uid;
        ResponseEntity<Accounts[]> forEntity = restTemplate.getForEntity(accountUrl, Accounts[].class);
        return new ResponseEntity<>(Arrays.asList(forEntity.getBody()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Accounts> saveAccount(Accounts accounts) {
        String accountUrl = config.getUrl() + "/accounts/account/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(accounts, headers);
        ResponseEntity<Accounts> saveAccount = restTemplate.exchange(accountUrl, HttpMethod.POST, entity, Accounts.class);
        return new ResponseEntity<>(saveAccount.getBody(), HttpStatus.OK);
    }

}
