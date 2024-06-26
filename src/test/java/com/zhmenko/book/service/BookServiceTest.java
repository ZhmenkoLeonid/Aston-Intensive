package com.zhmenko.book.service;

import com.zhmenko.AbstractTest;
import com.zhmenko.book.data.dao.BookDao;
import com.zhmenko.book.mapper.BookMapper;
import com.zhmenko.book.model.BookInsertRequest;
import com.zhmenko.book.model.BookModifyRequest;
import com.zhmenko.book.model.BookResponse;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest extends AbstractTest {
    @Mock
    private BookDao bookDao;

    @InjectMocks
    private BookServiceImpl service;

    @Mock
    private BookMapper mapper;

    @Test
    void testAddBook() {
        when(bookDao.insertBook(any())).thenReturn(any());
        BookInsertRequest bookInsertRequest = new BookInsertRequest("name", 1);
        assertDoesNotThrow(() -> service.addBook(bookInsertRequest));
    }

    @Test
    void testAddBookWithWrongProperties() {
        BookInsertRequest bookInsertRequest = new BookInsertRequest("name", -1);
        assertThrows(BadRequestException.class, () -> service.addBook(bookInsertRequest));
    }

    @Test
    void testGetBookById() {
        when(bookDao.selectBookById(1L)).thenReturn(Optional.ofNullable(bookEntityFirst));
        BookResponse expected = new BookResponse(1,
                bookEntityFirst.getName(),
                authorEntityFirst.getSecondName() + " " +
                authorEntityFirst.getFirstName() + " " +
                authorEntityFirst.getThirdName(),
                List.of(userEntityFirst.getName(), userEntitySecond.getName()));
        when(mapper.bookEntityToBookResponse(bookEntityFirst)).thenReturn(expected);
        final BookResponse actual = service.getBookById(1L);

        assertEquals(expected, actual);
    }

    @Test
    void testGetBookByNotExistingBookId() {
        when(bookDao.selectBookById(4L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> service.getBookById(4L));
    }

    @Test
    void testUpdateBook() {
        BookModifyRequest bookModifyRequest = new BookModifyRequest(1, "another book name", 2);
        assertDoesNotThrow(() -> service.updateBook(bookModifyRequest, 1L));
    }

    @Test
    void testUpdateBookWithWrongPathVariableId() {
        BookModifyRequest bookModifyRequest = new BookModifyRequest(1, "another book name", 2);
        assertThrows(BadRequestException.class, () -> service.updateBook(bookModifyRequest, 2L));
    }

    @Test
    void testUpdateBookWithNotExistingBookId() {
        BookModifyRequest bookModifyRequest = new BookModifyRequest(4, "another book name", 2);
        doThrow(new BookNotFoundException(4L)).when(bookDao).updateBook(any());
        assertThrows(BookNotFoundException.class, () -> service.updateBook(bookModifyRequest, 4L));
    }

    @Test
    void testUpdateBookWithNotExistingAuthorId() {
        BookModifyRequest bookModifyRequest = new BookModifyRequest(1, "another book name", 4);
        doThrow(new AuthorNotFoundException(4L)).when(bookDao).updateBook(any());
        assertThrows(AuthorNotFoundException.class, () -> service.updateBook(bookModifyRequest, 1L));
    }

    @Test
    void testDeleteBookById() {
        when(bookDao.deleteBookById(1L)).thenReturn(any());
        assertDoesNotThrow(() -> service.deleteBookById(1L));
    }

    @Test
    void testDeleteBookByWrongId() {
        when(bookDao.deleteBookById(4L)).thenThrow(new BookNotFoundException(4L));
        assertThrows(BookNotFoundException.class, () -> service.deleteBookById(4L));
    }


}