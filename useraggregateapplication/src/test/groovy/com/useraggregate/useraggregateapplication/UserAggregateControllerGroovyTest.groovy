package com.useraggregate.useraggregateapplication

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.useraggregate.useraggregateapplication.adapters.Accounts
import com.useraggregate.useraggregateapplication.adapters.CustomerData
import com.useraggregate.useraggregateapplication.adapters.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest(classes = UseraggregateapplicationApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserAggregateControllerGroovyTest extends Specification{

    @LocalServerPort
    int port

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MockMvc mockMvc

    def "should send request of user and account and should return 200"(){
        setup:
        User user = new User()
        user.setId(4L)
        user.setFirstName("Sambi")
        user.setLastName("Ch")
        user.setPhoneNo("6692828775")
        user.setUniqueIdentification("chsa")
        Accounts account = new Accounts()
        account.setUniqueIdentification("chsa")
        account.setAccountName("Sambi CH")
        account.setAccountNumber(787848383)
        account.setAccountType("Savings")
        account.setBankName("United Bank")
        account.setId(5L)
        CustomerData customerData = new CustomerData()
        List<Accounts> accounts = new ArrayList<>()
        accounts.add(account)
        customerData.setAccounts(accounts)
        customerData.setUser(user)
        ObjectMapper objectMapper = new ObjectMapper()

        expect: "should return 200 and return status 200"
        try{
            this.mockMvc.perform(request(HttpMethod.POST, "/userAccounts/create")
                    .header("Authorization", "Bearer " + obtainAccessToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customerData)))
                    .andDo(print())
                    .andExpect(content().json("{\"status\":\"success\"}"))
                    .andExpect(status().isOk()).andReturn()
        }catch(Exception e){
            System.out.println(e.getCause())
        }

    }

    def obtainAccessToken(){
        HttpHeaders headers = new HttpHeaders()
        String url = "http://localhost:" + port + "/oauth/token"
        url += "?grant_type=client_credentials"
        headers.setBasicAuth("user_aggregate", "uascecrc")
        HttpEntity<?> entity = new HttpEntity<>(headers)
        ResponseEntity<String> exchange = testRestTemplate.exchange(url, HttpMethod.POST, entity, String.class)
        JsonNode node = null
        ObjectMapper mapper = new ObjectMapper()
        try {
            node = mapper.readTree(exchange.getBody())
        } catch (JsonProcessingException e) {
            e.printStackTrace()
        }
        String token = node.path("access_token").asText()
        return token
    }


    def "should return 401 with invalid token"(){
        expect: "should return unauthorized and return 401"
        try {
            this.mockMvc.perform(request(HttpMethod.GET, "/userAccounts/getAll")
                    .header("Authorization", "Bearer "))
                    .andDo(print())
                    .andExpect(status().isUnauthorized())
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    def "should return 401 without no headers"(){
        expect:
        try {
            this.mockMvc.perform(get("/userAccounts/getAll"))
                    .andDo(print())
                    .andExpect(status().isUnauthorized())
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}