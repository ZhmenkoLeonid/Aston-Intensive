package com.zhmenko.user.model.request;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.Min;

public class UserBookModifyRequest {
    @SerializedName("user_id")
    @Min(value = 1, message = "user id must be positive")
    private long userId;
    @SerializedName("book_id")
    @Min(value = 1, message = "book id must be positive")
    private long bookId;

    public UserBookModifyRequest() {
    }

    public UserBookModifyRequest(final int userId, final int bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(final long bookId) {
        this.bookId = bookId;
    }
}
