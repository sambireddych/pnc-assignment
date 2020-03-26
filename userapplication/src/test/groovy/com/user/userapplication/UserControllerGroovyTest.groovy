package com.user.userapplication

import com.user.userapplication.domain.User
import com.user.userapplication.repository.UserRepository
import com.user.userapplication.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerGroovyTest extends Specification {


    @Autowired
    MockMvc mockMvc

    @Autowired
    UserService userService

    UserRepository userRepository = Mock()


    def "should return 200 and should expect status is success"() {
        expect: "should return 200 and status is ok"
        this.mockMvc.perform(get("/users/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\"}"))
                .andReturn()
    }

    def "should return 500 before new user inserted"() {
        expect: "should return 500 and status is internal server error"
        this.mockMvc.perform(get("/users/4"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"status\":\"error\"}"))
                .andReturn()
    }


    def "should send request and create user return 200"() {
        setup:
        User user = new User()
        user.setId(4)
        user.setFirstName("Frank")
        user.setLastName("Lamps")
        user.setPhoneNo("7783778772")
        user.setUniqueIdentification("frla")


        expect:
        userService.save(user)

        /* then:
         1 * userRepository.save(user)*/
    }

    def "find all users and should return 200"() {
        when:
        def users = userService.getAll()
        then:
        users.size() == 4
    }


    def "should return 200 after newly user inserted"() {
        expect: "should return 200 and status is ok"
        this.mockMvc.perform(get("/users/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\"}"))
                .andReturn()
    }
}
