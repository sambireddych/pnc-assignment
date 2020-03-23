package com.accounts.accountapplication.controller;


import com.accounts.accountapplication.domain.Accounts;
import com.accounts.accountapplication.jsonResponse.JsonResponse;
import com.accounts.accountapplication.service.AccountsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private AccountsService accountsService;
    private JsonResponse jsonResponse;
    public AccountsController(AccountsService accountsService, JsonResponse jsonResponse) {
        this.accountsService = accountsService;
        this.jsonResponse = jsonResponse;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAll() throws IOException {
        return new ResponseEntity<>(jsonResponse.printArrayAccounts(accountsService.getAll()), HttpStatus.OK);
    }




    @GetMapping(produces = "application/json",path = {"/user/{id}"})
    public ResponseEntity<?> getAccountsByUser(@PathVariable long id){
        String uniqueIdentifier = accountsService.getById(id).get().getUniqueIdentification();
        if (uniqueIdentifier.isEmpty() || uniqueIdentifier == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Accounts> accountsList = accountsService.getAllAccountsForUser(uniqueIdentifier);
        return new ResponseEntity<>(accountsList, HttpStatus.OK);
    }



    @GetMapping(value = "/{id}",produces = "application/json")
    public ResponseEntity<?> getOne(@PathVariable long id) throws IOException {
        return new ResponseEntity<>(jsonResponse.printAccount(accountsService.getById(id).get()),HttpStatus.OK);
    }

    @PostMapping(produces = "application/json",consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody Accounts accounts) throws IOException {
        return new ResponseEntity<>(jsonResponse.printAccount(accountsService.save(accounts)),HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public void delete(long id){
        Optional<Accounts> accountById = accountsService.getById(id);
        if(!accountById.isPresent()){
            return;
        }else {
            accountsService.delete(accountById.get());
        }
    }

}
