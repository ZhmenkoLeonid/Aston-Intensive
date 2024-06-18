package com.zhmenko.author.service;

import com.zhmenko.AbstractTest;
import com.zhmenko.author.data.dao.AuthorDao;
import com.zhmenko.author.mapper.AuthorMapper;
import com.zhmenko.author.model.AuthorInsertRequest;
import com.zhmenko.author.model.AuthorModifyRequest;
import com.zhmenko.author.model.AuthorResponse;
import com.zhmenko.author.validator.AuthorValidator;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest extends AbstractTest {
    @Mock
    private AuthorDao authorDao;

    @InjectMocks
    private AuthorServiceImpl service;

    @Mock
    private AuthorMapper mapper;
    @Spy
    private AuthorValidator validator;

    @Test
    void testAddAuthor() {
        when(authorDao.insertAuthor(any())).thenReturn(true);
        AuthorInsertRequest authorInsertRequest =
                new AuthorInsertRequest("firstname", "secondname", "thirdname");
        assertDoesNotThrow(() -> service.addAuthor(authorInsertRequest));
    }

    @Test
    void testAddAuthorWithWrongProperties() {
        AuthorInsertRequest authorInsertRequest =
                new AuthorInsertRequest(null, "secondname", "thirdname");
        assertThrows(BadRequestException.class, () -> service.addAuthor(authorInsertRequest));
    }

    @Test
    void testGetAuthorById() {
        when(authorDao.selectAuthorById(1)).thenReturn(Optional.ofNullable(authorEntityFirst));
        AuthorResponse expected = new AuthorResponse(1,
                authorEntityFirst.getFirstName(),
                authorEntityFirst.getSecondName(),
                authorEntityFirst.getThirdName(),
                authorEntityFirst.getBookEntities().stream().map(BookEntity::getName).toList());
        when(mapper.authorEntityToAuthorResponse(authorEntityFirst)).thenReturn(expected);
        final AuthorResponse actual = service.getAuthorById(1);

        assertEquals(expected, actual);
    }

    @Test
    void testGetAuthorByNotExistingAuthorId() {
        when(authorDao.selectAuthorById(4)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> service.getAuthorById(4));
    }

    @Test
    void testUpdateAuthor() {
        AuthorModifyRequest authorModifyRequest
                = new AuthorModifyRequest(1,
                "firstnameChanged",
                "secondnameChanged",
                "thirdnameChanged");
        assertDoesNotThrow(() -> service.updateAuthor(authorModifyRequest, 1));
    }

    @Test
    void testUpdateAuthorWithWrongPathVariableId() {
        AuthorModifyRequest authorModifyRequest
                = new AuthorModifyRequest(1,
                "firstnameChanged",
                "secondnameChanged",
                "thirdnameChanged");
        assertThrows(BadRequestException.class, () -> service.updateAuthor(authorModifyRequest, 2));
    }

    @Test
    void testUpdateAuthorWithNotExistingAuthorId() {
        AuthorModifyRequest authorModifyRequest
                = new AuthorModifyRequest(5,
                "firstnameChanged",
                "secondnameChanged",
                "thirdnameChanged");
        doThrow(new AuthorNotFoundException(4)).when(authorDao).updateAuthor(any(), eq(5));
        assertThrows(AuthorNotFoundException.class, () -> service.updateAuthor(authorModifyRequest, 5));
    }

    @Test
    void testDeleteAuthorById() {
        when(authorDao.deleteAuthorById(1)).thenReturn(true);
        assertDoesNotThrow(() -> service.deleteAuthorById(1));
    }

    @Test
    void testDeleteAuthorByWrongId() {
        when(authorDao.deleteAuthorById(4)).thenThrow(new AuthorNotFoundException(4));
        assertThrows(AuthorNotFoundException.class, () -> service.deleteAuthorById(4));
    }
}