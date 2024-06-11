package com.zhmenko.user_book.data.dao;

import com.zhmenko.AbstractDaoTest;
import com.zhmenko.book.data.dao.BookDao;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user.data.dao.UserDao;
import com.zhmenko.user.data.model.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserBookDaoTest extends AbstractDaoTest {
    static Stream<Arguments> inputData() {
        return Stream.of(
                Arguments.of(List.of(1, 2), false),
                Arguments.of(List.of(1, 3), false),
                Arguments.of(List.of(1, 2, 3), false),
                Arguments.of(List.of(2, 3), false),
                Arguments.of(List.of(2), true)
        );
    }

    private static UserBookDao userBookDao;
    private static BookDao bookDao;

    private static UserDao userDao;


    @BeforeAll
    static void injectObjects() {
        userBookDao = injector.getInstance(UserBookDao.class);
        bookDao = injector.getInstance(BookDao.class);
        userDao = injector.getInstance(UserDao.class);
    }

    @Test
    void testInsertUserBooksByUserId() {
        final boolean b = userBookDao.insertUserBooksByUserId(List.of(2), 1);
        assertTrue(b);
        final Optional<UserEntity> userEntity = userDao.selectUserById(1);
        assertTrue(userEntity.isPresent());
        final UserEntity userEntity1 = userEntity.get();
        assertEquals(3, userEntity1.getBookEntitySet().size());
    }

    @Test
    void testInsertUserBooksByNotExistingUserId() {
        assertThrows(UserNotFoundException.class, () -> userBookDao.insertUserBooksByUserId(List.of(2), 4));
    }

    @Test
    void testInsertUserBooksByUserIdWithAlreadyExistingBookId() {
        //                                                                                 Already exist
        //                                                                                        |
        //                                                                                        v
        assertThrows(BadRequestException.class, () -> userBookDao.insertUserBooksByUserId(List.of(1, 2), 1));
    }

    @Test
    void testDeleteUserBooksByUserId() {
        final boolean b = userBookDao.deleteUserBooksByUserId(1);
        assertTrue(b);
        final Optional<UserEntity> userEntityOptional = userDao.selectUserById(1);
        assertTrue(userEntityOptional.isPresent());
        final UserEntity userEntity = userEntityOptional.get();
        assertTrue(userEntity.getBookEntitySet().isEmpty());
    }

    @Test
    void testDeleteUserBooksByNotExistingUserId() {
        assertThrows(UserNotFoundException.class, () -> userBookDao.deleteUserBooksByUserId(4));
    }

    @Test
    void testDeleteSpecifiedUserBooksByUserId() {
        final boolean b = userBookDao.deleteUserBooksByUserId(List.of(1), 1);
        assertTrue(b);
        final Optional<UserEntity> userEntityOptional = userDao.selectUserById(1);
        assertTrue(userEntityOptional.isPresent());
        final UserEntity userEntity = userEntityOptional.get();
        assertEquals(1, userEntity.getBookEntitySet().size());
    }

    @Test
    void testDeleteSpecifiedUserBooksByNotExistingUserId() {
        assertThrows(UserNotFoundException.class, () -> userBookDao.deleteUserBooksByUserId(List.of(1), 4));
    }

    @Test
    void testDeleteSpecifiedUserBooksByUserIdWithNotOwnedByUserBookId() {
        //                                                                               Not owned by user with id 1
        //                                                                                           |
        //                                                                                           v
        assertThrows(BadRequestException.class, () -> userBookDao.deleteUserBooksByUserId(List.of(1, 2), 1));
    }

    @Test
    void testDeleteUserBooksByBookId() {
        final boolean b = userBookDao.deleteUserBooksByBookId(1);
        assertTrue(b);
        final Optional<BookEntity> bookEntityOptional = bookDao.selectBookById(1);
        assertTrue(bookEntityOptional.isPresent());
        final BookEntity bookEntity = bookEntityOptional.get();
        assertTrue(bookEntity.getUserEntities().isEmpty());
    }

    @Test
    void testDeleteUserBooksByNotExistingBookId() {
        assertThrows(BookNotFoundException.class, () -> userBookDao.deleteUserBooksByBookId(4));
    }
}