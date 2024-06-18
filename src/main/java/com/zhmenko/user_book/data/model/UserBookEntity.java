package com.zhmenko.user_book.data.model;

import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.user.data.model.UserEntity;

public class UserBookEntity {
    private BookEntity book;
    private UserEntity user;

    public UserBookEntity() {
    }

    public UserBookEntity(BookEntity book, UserEntity user) {
        this.book = book;
        this.user = user;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String toShortString() {
        return "UserBookEntity{" +
               "book=" + book.getName() +
               ", user=" + user.getName() +
               '}';
    }

    @Override
    public String toString() {
        return "UserBookEntity{" +
               "book=" + book.toShortString() +
               ", user=" + user.toShortString() +
               '}';
    }
}
