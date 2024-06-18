package com.zhmenko.author.model;

import com.google.gson.annotations.SerializedName;

public class AuthorModifyRequest {
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("second_name")
    private String secondName;
    @SerializedName("third_name")
    private String thirdName;

    public AuthorModifyRequest() {
    }

    public AuthorModifyRequest(final int id, final String firstName, final String secondName, final String thirdName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(final String secondName) {
        this.secondName = secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(final String thirdName) {
        this.thirdName = thirdName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
