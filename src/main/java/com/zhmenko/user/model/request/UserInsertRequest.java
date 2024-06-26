package com.zhmenko.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserInsertRequest {
    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be blank")
    private String name;
    @Email
    private String email;
    @NotNull(message = "country cannot be null")
    @NotBlank(message = "country cannot be blank")
    private String country;

    public UserInsertRequest() {
    }

    public UserInsertRequest(String name, String email, String country) {
        this.name = name;
        this.email = email;
        this.country = country;
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

    @Override
    public String toString() {
        return "UserInsertRequest{" +
               "name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", country='" + country + '\'' +
               '}';
    }
}
