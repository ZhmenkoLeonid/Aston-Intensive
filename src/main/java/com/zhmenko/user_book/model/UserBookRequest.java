package com.zhmenko.user_book.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserBookRequest {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("books_id")
    private List<Integer> booksId;

    public UserBookRequest(final int userId, final List<Integer> booksId) {
        this.userId = userId;
        this.booksId = booksId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public List<Integer> getBooksId() {
        return booksId;
    }

    public void setBooksId(final List<Integer> booksId) {
        this.booksId = booksId;
    }
}
