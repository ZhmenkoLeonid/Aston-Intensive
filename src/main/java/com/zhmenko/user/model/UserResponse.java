package com.zhmenko.user.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class UserResponse {
    private int id;
    private String name;
    private String email;
    private String country;

    @SerializedName("books_name")
    private List<String> booksName;

    public UserResponse() {
    }

    public UserResponse(int id, String name, String email, String country, List<String> booksName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.booksName = booksName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        final UserResponse that = (UserResponse) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(country, that.country) && Objects.equals(booksName, that.booksName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", country='" + country + '\'' +
               ", booksName=" + booksName +
               '}';
    }
}
