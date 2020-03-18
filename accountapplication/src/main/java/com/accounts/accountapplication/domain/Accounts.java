package com.accounts.accountapplication.domain;


import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountId")
    private long id;

    @Column(name = "AccountName",length = 50)
    private String accountName;

    @Column(name = "BankName")
    private String bankName;

    @Column(name = "AccountType", columnDefinition = "enum('Savings','Checking')")
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    @Column(name = "AccountNumber",length = 11)
    private long accountNumber;

    @Column(name = "UniqueIdentification")
    private String uniqueIdentification;


    public Accounts() {
    }


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getUniqueIdentification() {
        return uniqueIdentification;
    }

    public void setUniqueIdentification(String uniqueIdentification) {
        this.uniqueIdentification = uniqueIdentification;
    }
}
