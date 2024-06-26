package com.zhmenko.user.model.response;

import com.google.gson.annotations.SerializedName;
import com.zhmenko.user.data.model.CreditCardEntity;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link CreditCardEntity}
 */
public class CreditCardResponse extends BillingDetailsResponse implements Serializable {
    @SerializedName("card_number")
    private final String cardNumber;
    @SerializedName("exp_month")
    private final String expMonth;
    @SerializedName("exp_year")
    private final String expYear;

    public CreditCardResponse(String cardNumber, String expMonth, String expYear) {
        this.cardNumber = cardNumber;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCardResponse entity = (CreditCardResponse) o;
        return Objects.equals(this.cardNumber, entity.cardNumber) &&
               Objects.equals(this.expMonth, entity.expMonth) &&
               Objects.equals(this.expYear, entity.expYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, expMonth, expYear);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
               "cardNumber = " + cardNumber + ", " +
               "expMonth = " + expMonth + ", " +
               "expYear = " + expYear + ")";
    }
}