package com.zhmenko.author.model;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuthorModifyRequest {
    @Min(value = 1, message = "id must be positive")
    private int id;
    @SerializedName("first_name")
    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first name cannot be blank")
    private String firstName;
    @SerializedName("second_name")
    @NotNull(message = "second name cannot be null")
    @NotBlank(message = "second name cannot be blank")
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
