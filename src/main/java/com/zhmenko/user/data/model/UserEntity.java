package com.zhmenko.user.data.model;

import com.zhmenko.book.data.model.BookEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User entity
 */
public class UserEntity {
    private int id;
    private String name;
    private String email;
    private String country;

    private Set<BookEntity> bookEntitySet;

    public UserEntity() {
        this.bookEntitySet = new HashSet<>();
    }

    public UserEntity(String name, String email, String country) {
        this.name = name;
        this.email = email;
        this.country = country;
        this.bookEntitySet = new HashSet<>();
    }

    public UserEntity(int id, String name, String email, String country) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.bookEntitySet = new HashSet<>();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<BookEntity> getBookEntitySet() {
        return bookEntitySet;
    }

    public void setBookEntitySet(Set<BookEntity> bookEntitySet) {
        this.bookEntitySet = bookEntitySet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
               Objects.equals(name, that.name) &&
               Objects.equals(email, that.email) &&
               Objects.equals(country, that.country) &&
               Objects.equals(bookEntitySet, that.bookEntitySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, country, bookEntitySet);
    }

    public String toShortString() {
        return "UserEntity{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", country='" + country + '\'' +
               '}';
    }

    @Override
    public String toString() {
        return "UserEntity{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", country='" + country + '\'' +
               ", userEntities=" + bookEntitySet +
               '}';
    }
}
