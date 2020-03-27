package com.accounts.accountapplication

import com.accounts.accountapplication.domain.AccountType
import com.accounts.accountapplication.domain.Accounts
import com.accounts.accountapplication.service.AccountsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountsControllerGroovyTest extends Specification{
    @Autowired
    MockMvc mockMvc

    @Autowired
    AccountsService accountsService

    def "should return 200 and should expect status is success"() {
        expect: "should return 200 and status is ok"
        this.mockMvc.perform(get("/accounts/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\"}"))
                .andReturn()
    }

    def "should return 500 before new account inserted"() {
        expect: "should return 500 and status is internal server error"
        this.mockMvc.perform(get("/accounts/5"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"status\":\"error\"}"))
                .andReturn()
    }


    def "should send request and create account and return 200"() {
        setup:
        Accounts accounts = new Accounts()
        accounts.setId(5L)
        accounts.setAccountName("Mount Mason")
        accounts.setUniqueIdentification("moma")
        accounts.setAccountNumber(787878833)
        accounts.setBankName("Chase Bank")
        accounts.setAccountType(AccountType.Savings)


        expect:
        accountsService.save(accounts)

    }

    def "find all accounts and should return 200"() {
        when:
        def accounts = accountsService.getAll()
        then:
        accounts.size() == 5
    }


    def "should return 200 after newly accounts inserted"() {
        expect: "should return 200 and status is ok"
        this.mockMvc.perform(get("/accounts/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\"}"))
                .andReturn()
    }


    def "delete account by id and should return status ok"(){
        expect: "should return 200 and status is ok"
        this.mockMvc.perform(delete("/accounts/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()

        when:
        def accounts = accountsService.getAll()
        then:
        accounts.size() == 4
    }
}
