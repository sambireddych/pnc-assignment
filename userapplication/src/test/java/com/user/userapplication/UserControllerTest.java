package com.user.userapplication;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.userapplication.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void save_User_Method_Should_Create_And_Return_Ok(){
        User user = buildUser();
//        given(userService.save(user)).willReturn(userService.getById(user.getId()).get());
        try {
            this.mockMvc.perform(post("/users")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(user)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"status\":\"success\"}"));
            try {
                this.mockMvc.perform(get("/users/" + user.getId()))
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


    @Test
    public void should_Delete_User_And_Return_200(){

        try {
            this.mockMvc.perform(delete("/users/2"))
                    .andDo(print())
                    .andExpect(status().isOk()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @Sql("/test.sql")

    public void get_User_Id_By_Test(){
        try {
            this.mockMvc.perform(get("/users/1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"status\":\"success\"}"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  @Test
    public void invalid_get_user_id_should_return_error(){
        try {
            this.mockMvc.perform(get("/users/5"))
                    .andDo(print())
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().json("{\"status\":\"error\"}"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private User buildUser(){
        User user = new User();
        user.setId(4);
        user.setFirstName("Frank");
        user.setLastName("Lamps");
        user.setPhoneNo("7783778772");
        user.setUniqueIdentification("frla");
        return user;
    }

}
