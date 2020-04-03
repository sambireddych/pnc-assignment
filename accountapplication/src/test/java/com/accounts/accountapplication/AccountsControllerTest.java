package com.accounts.accountapplication;


import com.accounts.accountapplication.domain.AccountType;
import com.accounts.accountapplication.domain.Accounts;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = AccountapplicationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void save_User_Method_Should_Create_And_Return_Ok(){
        Accounts accounts = buildAccount();
//        given(userService.save(user)).willReturn(userService.getById(user.getId()).get());
        try {
            this.mockMvc.perform(post("/accounts")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(accounts)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"status\":\"success\"}"));
            try {
                this.mockMvc.perform(get("/accounts/" + accounts.getId()))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\"status\":\"success\"}"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*@Test
    public void should_Delete_User_And_Return_200(){

        try {
            this.mockMvc.perform(delete("/accounts/2"))
                    .andDo(print())
                    .andExpect(status().isOk()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    @Test
    @Sql("/test.sql")

    public void get_Account_By_Id_return_200(){
        try {
            this.mockMvc.perform(get("/accounts/1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"status\":\"success\"}"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  @Test
    public void invalid_get_Account_id_should_return_error(){
        try {
            this.mockMvc.perform(get("/accounts/6"))
                    .andDo(print())
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().json("{\"status\":\"error\"}"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Accounts buildAccount(){
        Accounts accounts = new Accounts();
        accounts.setId(5L);
        accounts.setAccountName("Mount Mason");
        accounts.setUniqueIdentification("moma");
        accounts.setAccountNumber(787878833);
        accounts.setBankName("Chase Bank");
        accounts.setAccountType(AccountType.Savings);
        return accounts;
    }

}
