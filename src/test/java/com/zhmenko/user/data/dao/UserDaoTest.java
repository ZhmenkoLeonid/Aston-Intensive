package com.zhmenko.user.data.dao;

import com.zhmenko.AbstractDaoTest;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user.data.model.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest extends AbstractDaoTest {
    private static UserDao userDao;

    @BeforeAll
    public static void injectObjects() {
        userDao = injector.getInstance(UserDao.class);
    }

    @Test
    void insertUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("original book name (NO)");
        userEntity.setCountry("RUS");
        userEntity.setEmail("some@mail.ru");
        final UserEntity b = userDao.insertUser(userEntity);
        assertNotNull(b);
        final Optional<UserEntity> createdUserOpt = userDao.selectUserById(4L);
        assertTrue(createdUserOpt.isPresent());
        final UserEntity userEntity1 = createdUserOpt.get();
        assertEquals(4L, userEntity1.getId());
        assertEquals(userEntity.getName(), userEntity1.getName());
        assertEquals(userEntity.getEmail(), userEntity1.getEmail());
        assertEquals(userEntity.getCountry(), userEntity1.getCountry());
    }

    @Test
    void testUpdateUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("updated nae");
        userEntity.setCountry("JAP");
        userEntity.setEmail("new@mail.ru");

        final UserEntity b = userDao.updateUser(userEntity);
        assertNotNull(b);
        final UserEntity updatedUser = userDao.selectUserById(1L).get();
        assertEquals(updatedUser.getId(), userEntity.getId());
        assertEquals(updatedUser.getName(), userEntity.getName());
        assertEquals(updatedUser.getEmail(), userEntity.getEmail());
        assertEquals(updatedUser.getCountry(), userEntity.getCountry());
        assertTrue(updatedUser.getBookEntitySet().size() > 0);
    }

    @Test
    void testUpdateUserWithNotExistingId() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(4L);
        userEntity.setName("original book name (NO)");
        userEntity.setCountry("RUS");
        userEntity.setEmail("some@mail.ru");

        assertThrows(UserNotFoundException.class, () -> userDao.updateUser(userEntity));
    }

    @Test
    void deleteUserById() {
        final UserEntity b = userDao.deleteUserById(1L);
        assertNotNull(b);
    }

    @Test
    void deleteUserByNotExistingId() {
        assertThrows(UserNotFoundException.class, () -> userDao.deleteUserById(4L));
    }

    @Test
    void testSelectUserById() {
        final Optional<UserEntity> userEntityOpt = userDao.selectUserById(1L);
        assertTrue(userEntityOpt.isPresent());
        final UserEntity userEntity = userEntityOpt.get();
        assertEquals("Robbie Baird", userEntity.getName());
        assertEquals("SYR", userEntity.getCountry());
        assertEquals("yyax5@d-tk--.com", userEntity.getEmail());
        final List<String> usersBooksName = userEntity.getBookEntitySet().stream().map(BookEntity::getName).toList();
        assertEquals(List.of("The Pilgrim’s Progress", "Gulliver’s Travels"), usersBooksName);
    }

    @Test
    void testSelectUsersByNotExistingId() {
        final Optional<UserEntity> userEntityOpt = userDao.selectUserById(4L);

        assertTrue(userEntityOpt.isEmpty());
    }

    @Test
    void testIsExistById() {
        final boolean existById = userDao.isExistById(1L);

        assertTrue(existById);
    }

    @Test
    void testIfExistByNotExistingId() {
        final boolean existById = userDao.isExistById(4L);

        assertFalse(existById);
    }

    @Test
    void testInsertBookByUserId() {
        final UserEntity b = userDao.addBook(new UserEntity(1L), new BookEntity(2L));
        assertNotNull(b);
        final Optional<UserEntity> userEntity = userDao.selectUserById(1L);
        assertTrue(userEntity.isPresent());
        final UserEntity userEntity1 = userEntity.get();
        assertEquals(3, userEntity1.getBookEntitySet().size());
    }

    @Test
    void testInsertBookByNotExistingUserId() {
        assertThrows(UserNotFoundException.class, () -> userDao.addBook(new UserEntity(4L), new BookEntity(2L)));
    }

    @Test
    void testInsertBookByUserIdWithAlreadyExistingBookId() {
        //                                                                                                Already exist
        //                                                                                                      |
        //                                                                                                      v
        assertThrows(BadRequestException.class, () -> userDao.addBook(new UserEntity(1L), new BookEntity(1L)));
    }

    @Test
    void testDeleteBookByUserId() {
        final UserEntity b = userDao.removeBook(new UserEntity(1L), new BookEntity(1L));
        assertNotNull(b);
        final Optional<UserEntity> userEntity = userDao.selectUserById(1L);
        assertTrue(userEntity.isPresent());
        final UserEntity userEntity1 = userEntity.get();
        assertEquals(1, userEntity1.getBookEntitySet().size());
    }

    @Test
    void testDeleteSpecifiedBookByNotExistingUserId() {
        assertThrows(UserNotFoundException.class, () -> userDao.removeBook(new UserEntity(4L), new BookEntity(2L)));
    }
    @Test
    void testDeleteNotOwnedByUserBook() {
        //                                                                                           Not owned by user with id 1
        //                                                                                                         |
        //                                                                                                         v
        assertThrows(BadRequestException.class, () -> userDao.removeBook(new UserEntity(1L), new BookEntity(2L)));
    }
}