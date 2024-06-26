package com.zhmenko.user.model.response;

import com.google.gson.annotations.SerializedName;

public class BillingDetailsResponse {
    private Long id;
    
    private String owner;

    @SerializedName("user_id")
    private Long userId;

    public BillingDetailsResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }
}
