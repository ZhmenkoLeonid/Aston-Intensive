package com.zhmenko.book.model;

import com.google.gson.annotations.SerializedName;

public class BookInsertRequest {
    private String name;

    @SerializedName("author_id")
    private int authorId;

    public BookInsertRequest() {
    }

    public BookInsertRequest(String name, int authorId) {
        this.name = name;
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
