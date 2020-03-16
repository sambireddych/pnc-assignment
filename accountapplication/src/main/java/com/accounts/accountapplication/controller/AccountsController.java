package com.accounts.accountapplication.controller;


import com.accounts.accountapplication.domain.Accounts;
import com.accounts.accountapplication.service.AccountsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class AccountsController {

    private AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Accounts>> getAll(){
        return new ResponseEntity<>(accountsService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = "application/json")
    public ResponseEntity<Accounts> getOne(@PathVariable long id){
        return new ResponseEntity<>(accountsService.getById(id).get(),HttpStatus.OK);
    }

    @PostMapping(produces = "application/json",consumes = "application/json")
    public ResponseEntity<Accounts> save(@RequestBody Accounts accounts){
        return new ResponseEntity<>(accountsService.save(accounts),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public void delete(long id){
        Optional<Accounts> userById = accountsService.getById(id);
        if(userById.isPresent()){
            return;
        }
        accountsService.delete(userById.get());
    }

}
