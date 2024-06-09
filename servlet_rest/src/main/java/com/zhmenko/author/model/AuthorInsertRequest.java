package com.zhmenko.author.model;

import com.google.gson.annotations.SerializedName;

public class AuthorInsertRequest {
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("second_name")
    private String secondName;
    @SerializedName("third_name")
    private String thirdName;

    public AuthorInsertRequest() {
    }

    public AuthorInsertRequest(final String firstName, final String secondName, final String thirdName) {
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
}
