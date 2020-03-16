package com.user.userapplication.controller;


import com.user.userapplication.domain.User;
import com.user.userapplication.jsonResponse.JsonResponse;
import com.user.userapplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private JsonResponse jsonResponse;
    public UserController(UserService userService, JsonResponse jsonResponse) {
        this.userService = userService;
        this.jsonResponse = jsonResponse;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllUsers() throws IOException {

        return new ResponseEntity<>(jsonResponse.testSuccessItemArray(userService.getAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = "application/json")
    public ResponseEntity<?> getOneUser(@PathVariable long id) throws IOException {
        Optional<User> byId = userService.getById(id);
        return new ResponseEntity<>(jsonResponse.testSuccessOneItem(userService.getById(id).get()),HttpStatus.OK);
    }

    @PostMapping(produces = "application/json",consumes = "application/json")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws IOException {
        return new ResponseEntity<>(jsonResponse.testSuccessOneItem(userService.save(user)),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id){
        Optional<User> userById = userService.getById(id);
        if(!userById.isPresent()){
            return;
        }else
            userService.delete(userById.get());
    }

}
