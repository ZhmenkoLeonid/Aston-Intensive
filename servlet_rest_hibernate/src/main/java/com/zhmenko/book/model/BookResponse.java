package com.zhmenko.book.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookResponse {
    private int id;

    private String name;
    @SerializedName("author_name")
    private String authorName;

    @SerializedName("users_name")
    private List<String> usersName;

    public BookResponse() {
    }

    public BookResponse(final int id, final String name, final String authorName, final List<String> usersName) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.usersName = usersName;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<String> getUsersName() {
        return usersName;
    }

    public void setUsersName(List<String> usersName) {
        this.usersName = usersName;
    }
}
