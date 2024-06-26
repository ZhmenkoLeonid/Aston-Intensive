package com.zhmenko.user.model.response;

import com.google.gson.annotations.SerializedName;
import com.zhmenko.user.data.model.BankAccountEntity;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link BankAccountEntity}
 */
public class BankAccountResponse extends BillingDetailsResponse implements Serializable {
    private final Long account;
    @SerializedName("bank_name")
    private final String bankName;

    public BankAccountResponse(Long account, String bankName) {
        this.account = account;
        this.bankName = bankName;
    }

    public Long getAccount() {
        return account;
    }

    public String getBankName() {
        return bankName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccountResponse entity = (BankAccountResponse) o;
        return Objects.equals(this.account, entity.account) &&
               Objects.equals(this.bankName, entity.bankName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, bankName);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
               "account = " + account + ", " +
               "bankName = " + bankName + ")";
    }
}