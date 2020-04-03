package com.useraggregate.useraggregateapplication;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.useraggregate.useraggregateapplication.adapters.Accounts;
import com.useraggregate.useraggregateapplication.adapters.CustomerData;
import com.useraggregate.useraggregateapplication.adapters.User;
import com.useraggregate.useraggregateapplication.adapters.UserServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import javax.servlet.Filter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles({"default", "local", "test"})
public class UserAggregateControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    int port;
    @Test
    public void should_return_401_error() {
        try {
            this.mockMvc.perform(get("/userAccounts/getAll"))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void should_return_200_after_giving_token() {
        try {
            this.mockMvc.perform(request(HttpMethod.GET, "/userAccounts/getAll")
                    .header("Authorization", "Bearer "+obtainAccessToken()))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_return_401_if_access_token_is_null() {
        try {
            this.mockMvc.perform(request(HttpMethod.GET, "/userAccounts/getAll")
                    .header("Authorization", "Bearer "))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String obtainAccessToken() throws Exception {


        HttpHeaders headers = new HttpHeaders();
        String url = "http://localhost:" + port + "/oauth/token";
        url += "?grant_type=client_credentials";
        headers.setBasicAuth("user_aggregate", "uascecrc");
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);
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


    @Test
    public void save_user_account_and_should_return_200(){
        User savedUser = buildUser();
        Accounts saveAccountForUser = buildAccount();
        CustomerData customerData = new CustomerData();
        List<Accounts> accountsList = new ArrayList<>();
        accountsList.add(saveAccountForUser);
        customerData.setUser(savedUser);
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            MvcResult authorizationRequest = this.mockMvc.perform(request(HttpMethod.POST, "/userAccounts/create")
                    .header("Authorization", "Bearer " + obtainAccessToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customerData)))
                    .andDo(print())
                    .andExpect(content().json("{\"status\":\"success\"}"))
                    .andExpect(status().isOk()).andReturn();
        }catch(Exception e){
            System.out.println(e.getCause());
        }
    }



    private Accounts buildAccount() {
        Accounts account = new Accounts();
        account.setUniqueIdentification("chsa");
        account.setAccountName("Sambi CH");
        account.setAccountNumber(787848383);
        account.setAccountType("Savings");
        account.setBankName("United Bank");
        account.setId(5L);
        return account;
    }


    private User buildUser(){
        User user = new User();
        user.setId(4L);
        user.setFirstName("Sambi");
        user.setLastName("Ch");
        user.setPhoneNo("6692828775");
        user.setUniqueIdentification("chsa");
        return user;
    }


}
