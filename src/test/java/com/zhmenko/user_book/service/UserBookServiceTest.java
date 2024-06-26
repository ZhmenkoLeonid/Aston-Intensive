package com.zhmenko.user_book.service;

import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user_book.data.dao.UserBookDao;
import com.zhmenko.user_book.model.UserBookRequest;
import com.zhmenko.user_book.validator.UserBookValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserBookServiceTest {
    @Mock
    private UserBookDao userBookDao;

    @InjectMocks
    private UserBookServiceImpl service;

    @Spy
    private UserBookValidator validator;

    @Test
    void testAddBooksToUser() {
        when(userBookDao.insertUserBooksByUserId(any(), anyLong())).thenReturn(true);
        UserBookRequest userBookRequest = new UserBookRequest(1L, List.of(2L, 3L));
        assertDoesNotThrow(() -> service.addBooksToUser(userBookRequest));
    }

    @Test
    void testAddBooksToUserWithWrongUserId() {
        when(userBookDao.insertUserBooksByUserId(any(), eq(5L))).thenThrow(new UserNotFoundException(5L));
        UserBookRequest userBookRequest = new UserBookRequest(5L, List.of(2L, 3L));
        assertThrows(UserNotFoundException.class, () -> service.addBooksToUser(userBookRequest));
    }

    @Test
    void testAddBooksToUserWithWrongBookId() {
        when(userBookDao.insertUserBooksByUserId(any(), anyLong())).thenThrow(new BookNotFoundException(5L));
        UserBookRequest userBookRequest = new UserBookRequest(1L, List.of(2L, 5L));
        assertThrows(BookNotFoundException.class, () -> service.addBooksToUser(userBookRequest));
    }

    @Test
    void testAddBooksToUserWithNegativeId() {
        UserBookRequest userBookRequest = new UserBookRequest(-1L, List.of(2L, 3L));
        assertThrows(BadRequestException.class, () -> service.addBooksToUser(userBookRequest));
    }

    @Test
    void testAddBooksToUserWithDuplicatingBookId() {
        UserBookRequest userBookRequest = new UserBookRequest(1L, List.of(2L, 3L, 2L));
        assertThrows(BadRequestException.class, () -> service.addBooksToUser(userBookRequest));
    }

    @Test
    void testDeleteBooksFromUser() {
        when(userBookDao.deleteUserBooksByUserId(any(), anyLong())).thenReturn(true);
        UserBookRequest userBookRequest = new UserBookRequest(1L, List.of(2L, 3L));
        assertDoesNotThrow(() -> service.deleteBookFromUser(userBookRequest));
    }

    @Test
    void testDeleteBooksFromUserWithWrongUserId() {
        when(userBookDao.deleteUserBooksByUserId(any(), eq(5L))).thenThrow(new UserNotFoundException(5L));
        UserBookRequest userBookRequest = new UserBookRequest(5L, List.of(2L, 3L));
        assertThrows(UserNotFoundException.class, () -> service.deleteBookFromUser(userBookRequest));
    }

    @Test
    void testDeleteBooksFromUserWithWrongBookId() {
        when(userBookDao.deleteUserBooksByUserId(any(), anyLong())).thenThrow(new BookNotFoundException(5L));
        UserBookRequest userBookRequest = new UserBookRequest(1L, List.of(2L, 5L));
        assertThrows(BookNotFoundException.class, () -> service.deleteBookFromUser(userBookRequest));
    }

    @Test
    void testDeleteBooksFromUserWithNegativeId() {
        UserBookRequest userBookRequest = new UserBookRequest(-1L, List.of(2L, 3L));
        assertThrows(BadRequestException.class, () -> service.deleteBookFromUser(userBookRequest));
    }

    @Test
    void testDeleteBooksFromUserWithDuplicateBookId() {
        UserBookRequest userBookRequest = new UserBookRequest(1L, List.of(2L, 3L, 2L));
        assertThrows(BadRequestException.class, () -> service.deleteBookFromUser(userBookRequest));
    }
}