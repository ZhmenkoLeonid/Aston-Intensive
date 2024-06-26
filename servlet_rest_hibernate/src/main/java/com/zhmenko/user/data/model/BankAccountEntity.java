package com.zhmenko.user.data.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccountEntity extends BillingDetailsEntity {

    @Column(name = "account", nullable = false, unique = true)
    private Long account;

    @Column(name = "bank_name", nullable = false)
    private String bankName;


    public BankAccountEntity() {
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
               "account=" + account +
               ", bankName='" + bankName + '\'' +
               '}';
    }
}

