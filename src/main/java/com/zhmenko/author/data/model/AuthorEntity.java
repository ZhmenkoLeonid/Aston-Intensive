package com.zhmenko.author.data.model;

import com.zhmenko.book.data.model.BookEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class AuthorEntity {
    private int id;

    private String firstName;
    private String secondName;
    private String thirdName;
    private Set<BookEntity> bookEntities;

    public AuthorEntity() {
        this.bookEntities = new HashSet<>();
    }

    public AuthorEntity(Integer id, String firstName, String secondName, String thirdName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.bookEntities = new HashSet<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<BookEntity> getBookEntities() {
        return bookEntities;
    }

    public void setBookEntities(Set<BookEntity> bookEntities) {
        this.bookEntities = bookEntities;
    }

    public String toShortString() {
        return "AuthorEntity{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", secondName='" + secondName + '\'' +
               ", thirdName='" + thirdName + '\'' +
               '}';
    }

    @Override
    public String toString() {
        return "AuthorEntity{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", secondName='" + secondName + '\'' +
               ", thirdName='" + thirdName + '\'' +
               ", bookEntities=" + bookEntities.stream().map(BookEntity::toShortString).collect(Collectors.joining(", ")) +
               '}';
    }
}
