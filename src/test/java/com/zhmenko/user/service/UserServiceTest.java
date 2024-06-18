package com.zhmenko.user.service;

import com.zhmenko.AbstractTest;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user.data.dao.UserDao;
import com.zhmenko.user.mapper.UserMapper;
import com.zhmenko.user.model.UserInsertRequest;
import com.zhmenko.user.model.UserModifyRequest;
import com.zhmenko.user.model.UserResponse;
import com.zhmenko.user.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends AbstractTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper mapper;
    @Spy
    private UserValidator validator;


    @Test
    void testAddUser() {
        when(userDao.insertUser(any())).thenReturn(true);
        UserInsertRequest userInsertRequest = new UserInsertRequest("name", "email", "country");
        assertDoesNotThrow(() -> userService.addUser(userInsertRequest));
    }

    @Test
    void testAddUserWithWrongEmail() {
        UserInsertRequest userInsertRequest = new UserInsertRequest("name", null, "country");
        assertThrows(BadRequestException.class, () -> userService.addUser(userInsertRequest));
    }

    @Test
    void testAddUserWithWrongName() {
        UserInsertRequest userInsertRequest = new UserInsertRequest(null, "email", "country");
        assertThrows(BadRequestException.class, () -> userService.addUser(userInsertRequest));
    }

    @Test
    void testGetUserById() {
        when(userDao.selectUserById(3)).thenReturn(Optional.ofNullable(userEntityThird));
        UserResponse expected = new UserResponse(3,
                "name3",
                "3@mail.ru",
                "EN",
                List.of(bookEntityThird.getName()));
        when(mapper.userEntityToUserResponse(userEntityThird)).thenReturn(expected);
        final UserResponse actual = userService.getUserById(3);

        assertEquals(expected, actual);
    }

    @Test
    void testGetUserByNotExistingUserId() {
        when(userDao.selectUserById(4)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(4));
    }

    @Test
    void testUpdateUser() {
        UserModifyRequest userModifyRequest = new UserModifyRequest(1, "newName", "newEmail", "newCountry");
        assertDoesNotThrow(() -> userService.updateUser(userModifyRequest, 1));
    }

    @Test
    void testUpdateUserWithWrongPathVariableId() {
        UserModifyRequest userModifyRequest = new UserModifyRequest(1, "newName", "newEmail", "newCountry");
        assertThrows(BadRequestException.class, () -> userService.updateUser(userModifyRequest, 2));
    }

    @Test
    void testUpdateUserWithNullName() {
        UserModifyRequest userModifyRequest = new UserModifyRequest(1, null, "newEmail", "newCountry");
        assertThrows(BadRequestException.class, () -> userService.updateUser(userModifyRequest, 1));
    }

    @Test
    void testUpdateUserWithNullEmail() {
        UserModifyRequest userModifyRequest = new UserModifyRequest(1, "name", null, "newCountry");
        assertThrows(BadRequestException.class, () -> userService.updateUser(userModifyRequest, 1));
    }

    @Test
    void testUpdateUserWithNotExistingUserId() {
        UserModifyRequest userModifyRequest = new UserModifyRequest(4, "newName", "newEmail", "newCountry");
        doThrow(new UserNotFoundException(4)).when(userDao).updateUser(any(), eq(4));
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userModifyRequest, 4));
    }

    @Test
    void testDeleteUserById() {
        when(userDao.deleteUserById(3)).thenReturn(true);
        assertDoesNotThrow(() -> userService.deleteUserById(3));
    }

    @Test
    void testDeleteUserByWrongId() {
        when(userDao.deleteUserById(4)).thenThrow(new UserNotFoundException(4));
        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(4));
    }
}