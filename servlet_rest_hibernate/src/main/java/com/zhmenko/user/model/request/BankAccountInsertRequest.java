package com.zhmenko.user.model.request;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BankAccountInsertRequest extends BillingDetailsInsertRequest {
    @NotNull(message = "account cannot be null")
    private Long account;
    @SerializedName("bank_name")
    @NotNull(message = "bank name cannot be null")
    @NotBlank(message = "bank name cannot be blank")
    private String bankName;
    public BankAccountInsertRequest() {
    }

    public BankAccountInsertRequest(final @NotNull Long userId,
                                    final String owner,
                                    final Long account,
                                    final String bankName) {
        super(userId, owner);
        this.account = account;
        this.bankName = bankName;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(final Long account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }
}
