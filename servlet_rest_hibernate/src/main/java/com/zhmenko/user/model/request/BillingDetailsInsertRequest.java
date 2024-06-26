package com.zhmenko.user.model.request;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BillingDetailsInsertRequest {
    @SerializedName("user_id")
    @NotNull(message = "user id cannot be null")
    private Long userId;
    @NotNull(message = "owner cannot be null")
    @NotBlank(message = "owner cannot be blank")
    private String owner;

    public BillingDetailsInsertRequest() {
    }

    public BillingDetailsInsertRequest(final Long userId, final String owner) {
        this.userId = userId;
        this.owner = owner;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }
}
