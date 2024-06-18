package com.zhmenko.user.data.dao;

import com.zhmenko.AbstractDaoTest;
import com.zhmenko.book.data.model.BookEntity;
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
        final boolean b = userDao.insertUser(userEntity);
        assertTrue(b);
        final Optional<UserEntity> createdUserOpt = userDao.selectUserById(4);
        assertTrue(createdUserOpt.isPresent());
        final UserEntity userEntity1 = createdUserOpt.get();
        assertEquals(4, userEntity1.getId());
        assertEquals(userEntity.getName(), userEntity1.getName());
        assertEquals(userEntity.getEmail(), userEntity1.getEmail());
        assertEquals(userEntity.getCountry(), userEntity1.getCountry());
    }

    @Test
    void selectUsersByBookId() {
        final Set<UserEntity> userEntitySet = userDao.selectUsersByBookId(1);
        for (final UserEntity userEntity : userEntitySet) {
            assertTrue(userEntity.getId() > 0);
        }
        assertEquals(2, userEntitySet.size());
    }

    @Test
    void selectUsersByNotExistingBookId() {
        assertThrows(BookNotFoundException.class, () -> userDao.selectUsersByBookId(4));
    }

    @Test
    void testUpdateUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("original book name (NO)");
        userEntity.setCountry("RUS");
        userEntity.setEmail("some@mail.ru");

        final boolean b = userDao.updateUser(userEntity, userEntity.getId());
        assertTrue(b);
        final UserEntity updatedUser = userDao.selectUserById(1).get();
        assertEquals(updatedUser.getId(), userEntity.getId());
        assertEquals(updatedUser.getName(), userEntity.getName());
        assertEquals(updatedUser.getEmail(), userEntity.getEmail());
        assertEquals(updatedUser.getCountry(), userEntity.getCountry());
        assertTrue(updatedUser.getBookEntitySet().size() > 0);
    }

    @Test
    void testUpdateUserWithNotExistingId() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(4);
        userEntity.setName("original book name (NO)");
        userEntity.setCountry("RUS");
        userEntity.setEmail("some@mail.ru");

        assertThrows(UserNotFoundException.class, () -> userDao.updateUser(userEntity, userEntity.getId()));
    }

    @Test
    void deleteUserById() {
        final boolean b = userDao.deleteUserById(1);
        assertTrue(b);
    }

    @Test
    void deleteUserByNotExistingId() {
        assertThrows(UserNotFoundException.class, () -> userDao.deleteUserById(4));
    }

    @Test
    void testSelectUserById() {
        final Optional<UserEntity> userEntityOpt = userDao.selectUserById(1);
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
        final Optional<UserEntity> userEntityOpt = userDao.selectUserById(4);

        assertTrue(userEntityOpt.isEmpty());
    }

    @Test
    void testIsExistById() {
        final boolean existById = userDao.isExistById(1);

        assertTrue(existById);
    }

    @Test
    void testIfExistByNotExistingId() {
        final boolean existById = userDao.isExistById(4);

        assertFalse(existById);
    }
}