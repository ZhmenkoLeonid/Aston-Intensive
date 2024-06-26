package com.zhmenko.user.model.request;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BillingDetailsModifyRequest {
    @SerializedName("user_id")
    @NotNull(message = "user id cannot be null")
    @Min(value = 1, message = "user id must be positive")
    private Long userId;
    @SerializedName("billing_details_id")
    @NotNull(message = "billing details id cannot be null")
    @Min(value = 1, message = "billing details id must be positive")
    private Long billingDetailsId;

    public BillingDetailsModifyRequest() {
    }

    public BillingDetailsModifyRequest(@NotNull(message = "user id cannot be null") final Long userId,
                                       @NotNull(message = "billing details id cannot be null") final Long billingDetailsId) {
        this.userId = userId;
        this.billingDetailsId = billingDetailsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getBillingDetailsId() {
        return billingDetailsId;
    }

    public void setBillingDetailsId(final Long billingDetailsId) {
        this.billingDetailsId = billingDetailsId;
    }
}
