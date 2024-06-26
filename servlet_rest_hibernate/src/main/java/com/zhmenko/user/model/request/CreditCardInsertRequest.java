package com.zhmenko.user.model.request;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreditCardInsertRequest extends BillingDetailsInsertRequest {
    @SerializedName("card_number")
    @NotNull(message = "card number cannot be null")
    @NotBlank(message = "card number cannot be blank")
    private String cardNumber;
    @SerializedName("exp_month")
    @NotNull(message = "expiry month cannot be null")
    @NotBlank(message = "expiry month cannot be blank")
    private String expMonth;
    @SerializedName("exp_year")
    @NotNull(message = "expiry year cannot be null")
    @NotBlank(message = "expiry year cannot be blank")
    private String expYear;

    public CreditCardInsertRequest() {
    }

    public CreditCardInsertRequest(final Long userId,
                                   final String owner,
                                   final String cardNumber,
                                   final String expMonth,
                                   final String expYear) {
        super(userId, owner);
        this.cardNumber = cardNumber;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(final String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(final String expYear) {
        this.expYear = expYear;
    }
}
