package com.zhmenko.author.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class AuthorResponse {
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("second_name")
    private String secondName;
    @SerializedName("third_name")
    private String thirdName;
    @SerializedName("books_name")
    private List<String> booksName;

    public AuthorResponse() {
    }

    public AuthorResponse(final int id,
                          final String firstName,
                          final String secondName,
                          final String thirdName,
                          final List<String> booksName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.booksName = booksName;
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


    public List<String> getBooksName() {
        return booksName;
    }

    public void setBooksName(List<String> booksName) {
        this.booksName = booksName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AuthorResponse that = (AuthorResponse) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(secondName, that.secondName) && Objects.equals(thirdName, that.thirdName) && Objects.equals(booksName, that.booksName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
