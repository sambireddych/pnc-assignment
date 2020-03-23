package com.user.userapplication.adapters;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AccountServiceClientImpl implements AccountServiceClient{

    private RestTemplate restTemplate;
    private AccountServiceClientConfig config;


    public AccountServiceClientImpl(AccountServiceClientConfig config, RestTemplateBuilder restTemplateBuilder) {
        this.config = config;
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }




    public ResponseEntity<List<Accounts>> getAccountFromAccountService(long id){
        String accountUrl = config.getUrl()+"/accounts/user/"+id;
        ResponseEntity<Accounts[]> forEntity = restTemplate.getForEntity(accountUrl, Accounts[].class);
        return new ResponseEntity<>(Arrays.asList(forEntity.getBody()), HttpStatus.OK);
    }

}
