package com.accounts.accountapplication.service.impl;

import com.accounts.accountapplication.domain.Accounts;
import com.accounts.accountapplication.repository.AccountsRepository;
import com.accounts.accountapplication.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountsServiceImpl implements AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    public List<Accounts> getAll() {
        return accountsRepository.findAll();
    }

    @Override
    public Optional<Accounts> getById(long id) {
        return accountsRepository.findById(id);
    }

    @Override
    public void delete(Accounts accounts) {
        accountsRepository.delete(accounts);
    }

    @Override
    public Accounts save(Accounts accounts) {
        return accountsRepository.saveAndFlush(accounts);
    }

    @Override
    public List<Accounts> getAllAccountsForUser(String uniqueIdentification) {
        return accountsRepository.findAllByUniqueIdentification(uniqueIdentification);
    }
}
