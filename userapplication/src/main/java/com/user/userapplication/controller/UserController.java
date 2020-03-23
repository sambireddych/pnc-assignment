package com.user.userapplication.controller;


import com.user.userapplication.adapters.AccountServiceClient;
import com.user.userapplication.adapters.Accounts;
import com.user.userapplication.domain.User;
import com.user.userapplication.jsonResponse.JsonResponse;
import com.user.userapplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private JsonResponse jsonResponse;
    private AccountServiceClient accountServiceClient;


    public UserController(UserService userService, JsonResponse jsonResponse, AccountServiceClient accountServiceClient) {
        this.userService = userService;
        this.jsonResponse = jsonResponse;
        this.accountServiceClient = accountServiceClient;
    }

    @GetMapping(value = "/all",produces = "application/json")
    private ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllUsers() throws IOException {

        return new ResponseEntity<>(jsonResponse.printUsersList(userService.getAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = "application/json")
    public ResponseEntity<?> getOneUser(@PathVariable long id) throws IOException {
        Optional<User> byId = userService.getById(id);
        return new ResponseEntity<>(jsonResponse.printUserInfo(userService.getById(id).get()),HttpStatus.OK);
    }

    @PostMapping(produces = "application/json",consumes = "application/json")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws IOException {
        return new ResponseEntity<>(jsonResponse.printUserInfo(userService.save(user)),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id){
        Optional<User> userById = userService.getById(id);
        if(!userById.isPresent()){
            return;
        }else
            userService.delete(userById.get());
    }


    @GetMapping(produces = "application/json",path = "/{id}/accounts")
    public ResponseEntity<?> getAccountsForUser(@PathVariable long id) throws IOException {
        Optional<User> user = userService.getById(id);
       /* if (!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/
        List<Accounts> accounts = accountServiceClient.getAccountFromAccountService(id).getBody();
        return new ResponseEntity<>(jsonResponse.printJsendWithAccountData(accounts,user.get()),HttpStatus.OK);

    }

}
