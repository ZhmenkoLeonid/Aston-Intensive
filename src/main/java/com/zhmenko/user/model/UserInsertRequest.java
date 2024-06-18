package com.zhmenko.user.model;

public class UserInsertRequest {
    private String name;
    private String email;
    private String country;

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
