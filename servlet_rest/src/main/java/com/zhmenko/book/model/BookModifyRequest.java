package com.zhmenko.book.model;

import com.google.gson.annotations.SerializedName;

public class BookModifyRequest {
    private int id;
    private String name;

    @SerializedName("author_id")
    private int authorId;

    public BookModifyRequest() {
    }

    public BookModifyRequest(int id, String name, int authorId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
