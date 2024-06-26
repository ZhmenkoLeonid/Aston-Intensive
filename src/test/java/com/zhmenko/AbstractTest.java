package com.zhmenko;

import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.user.data.model.UserEntity;
import org.junit.jupiter.api.BeforeAll;

import java.util.Set;

public class AbstractTest {
    protected static AuthorEntity authorEntityFirst;
    protected static AuthorEntity authorEntitySecond;
    protected static BookEntity bookEntityFirst;
    protected static BookEntity bookEntitySecond;
    protected static BookEntity bookEntityThird;
    protected static UserEntity userEntityFirst;
    protected static UserEntity userEntitySecond;
    protected static UserEntity userEntityThird;

    @BeforeAll
    static void setUp() {
        authorEntityFirst = new AuthorEntity(1L, "af1", "as1", "at1");
        authorEntitySecond = new AuthorEntity(2L, "af2", "as2", "at2");
        bookEntityFirst = new BookEntity(1L, "book 1", authorEntityFirst);
        bookEntitySecond = new BookEntity(2L, "book 2", authorEntitySecond);
        bookEntityThird = new BookEntity(3L, "book 3", authorEntityFirst);
        userEntityFirst = new UserEntity(1L, "name1", "1@mail.ru", "RUS");
        userEntitySecond = new UserEntity(2L, "name2", "2@mail.ru", "US");
        userEntityThird = new UserEntity(3L, "name3", "3@mail.ru", "EN");

        authorEntityFirst.setBookEntities(Set.of(bookEntityFirst, bookEntityThird));
        authorEntitySecond.setBookEntities(Set.of(bookEntitySecond));

        bookEntityFirst.setAuthor(authorEntityFirst);
        bookEntitySecond.setAuthor(authorEntityFirst);
        bookEntitySecond.setAuthor(authorEntitySecond);
        bookEntityFirst.setUserEntities(Set.of(userEntityFirst, userEntitySecond));
        bookEntitySecond.setUserEntities(Set.of(userEntitySecond, userEntityThird));
        bookEntityThird.setUserEntities(Set.of(userEntitySecond));

        userEntityFirst.setBookEntitySet(Set.of(bookEntityFirst));
        userEntitySecond.setBookEntitySet(Set.of(bookEntityFirst, bookEntitySecond));
        userEntityThird.setBookEntitySet(Set.of(bookEntityThird));
    }
}
