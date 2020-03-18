package com.accounts.accountapplication.service;


import com.accounts.accountapplication.domain.Accounts;

import java.util.List;
import java.util.Optional;

public interface AccountsService {

    List<Accounts> getAll();
    Optional<Accounts> getById(long id);
    void delete(Accounts user);
    Accounts save(Accounts accounts);

    List<Accounts> getAllAccountsForUser(String uniqueIdentification);

}
