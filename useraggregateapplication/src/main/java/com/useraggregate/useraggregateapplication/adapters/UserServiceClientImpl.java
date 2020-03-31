package com.useraggregate.useraggregateapplication.adapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
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
    public String getToken(){
        HttpHeaders headers = new HttpHeaders();
        String url = "http://localhost:8082/oauth/token";
        url += "?grant_type=client_credentials";
        headers.setBasicAuth("user_aggregate","uascecrc");
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        JsonNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            node = mapper.readTree(exchange.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String token = node.path("access_token").asText();
        return token;
    }
    @Override
    public ResponseEntity<List<User>> getUserDetails() {


/*
        String token = getToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","Bearer "+token);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<User[]> users = restTemplate.exchange(userUrl, HttpMethod.GET, httpEntity, User[].class);
*/

        String userUrl = config.getUrl()+"/users/all";
        ResponseEntity<User[]> users = restTemplate.getForEntity(userUrl, User[].class);
        return new ResponseEntity<>(Arrays.asList(users.getBody()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> saveUser(User user) {
        String url = config.getUrl()+"/users/newUser";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(user,headers);
        ResponseEntity<User> saveUser = restTemplate.exchange(url, HttpMethod.POST, entity, User.class);
        return new ResponseEntity<>(saveUser.getBody(),HttpStatus.OK);
    }

}
