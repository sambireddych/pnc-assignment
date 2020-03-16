package com.accounts.accountapplication.domain;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountId")
    private long id;

    @Column(name = "AccountName",length = 50)
    private String accountName;

    @Column(name = "AccountType",length = 20)
    private AccountType accountType;

    @Column(name = "AccountNumber",length = 11)
    private long accountNumber;



    public Accounts() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }
}
