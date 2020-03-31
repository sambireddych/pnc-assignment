package com.useraggregate.useraggregateapplication.adapters;

import java.util.List;

public class CustomerData {

    private User user;
    private List<Accounts> accounts;

    public CustomerData() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Accounts> accounts) {
        this.accounts = accounts;
    }
}
