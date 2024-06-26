package com.zhmenko.user.data.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "credit_card")
public class CreditCardEntity extends BillingDetailsEntity {

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "exp_month", nullable = false)
    private String expMonth;

    @Column (name = "exp_year", nullable = false)
    private String expYear;

    public CreditCardEntity() {
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

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
               "cardNumber=" + cardNumber +
               ", expMonth='" + expMonth + '\'' +
               ", expYear='" + expYear + '\'' +
               '}';
    }
}