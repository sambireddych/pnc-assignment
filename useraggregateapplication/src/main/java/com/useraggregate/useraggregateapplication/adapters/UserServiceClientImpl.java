package com.useraggregate.useraggregateapplication.adapters;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;


@Component
public class UserServiceClientImpl implements UserServiceClient{

    private RestTemplate restTemplate;
    private UserServiceClientConfig config;

    public UserServiceClientImpl(UserServiceClientConfig config, RestTemplateBuilder restTemplateBuilder) {
        this.config = config;
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }

    @Override
    public ResponseEntity<List<User>> getUserDetails() {
        String url = config.getUrl()+"/users/all";
        ResponseEntity<User[]> users = restTemplate.getForEntity(url, User[].class);
        return new ResponseEntity<>(Arrays.asList(users.getBody()), HttpStatus.OK);
    }
}
