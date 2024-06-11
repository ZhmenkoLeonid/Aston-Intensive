package com.zhmenko.book.data.model;

import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.user.data.model.UserEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BookEntity {

    private int id;
    private String name;

    private Set<UserEntity> userEntities;

    private AuthorEntity author;

    public BookEntity() {
    }

    public BookEntity(final Integer id, final String name) {
        this.id = id;
        this.name = name;
        this.userEntities = new HashSet<>();
    }

    public BookEntity(final int id, final String name, final AuthorEntity author) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.userEntities = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserEntities(Set<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BookEntity that = (BookEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(userEntities, that.userEntities) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toShortString() {
        return "BookEntity{" +
               "id=" + id +
               ", name='" + name +
               '}';
    }

    @Override
    public String toString() {
        return "BookEntity{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", userBookEntities=" +
               userEntities.stream()
                       .map(UserEntity::toShortString)
                       .collect(Collectors.joining(", ")) +
               ", author=" + author.toShortString() +
               '}';
    }
}
