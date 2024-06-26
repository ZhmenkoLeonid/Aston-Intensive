package com.zhmenko.author.service;

import com.zhmenko.AbstractTest;
import com.zhmenko.author.data.dao.AuthorDao;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.author.mapper.AuthorMapper;
import com.zhmenko.author.model.AuthorInsertRequest;
import com.zhmenko.author.model.AuthorModifyRequest;
import com.zhmenko.author.model.AuthorResponse;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void testAddAuthor() {
        when(authorDao.insertAuthor(any())).thenReturn(
                new AuthorEntity(3L, "firstname", "secondname", "thirdname"));
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
        when(authorDao.selectAuthorById(1L)).thenReturn(Optional.ofNullable(authorEntityFirst));
        AuthorResponse expected = new AuthorResponse(1,
                authorEntityFirst.getFirstName(),
                authorEntityFirst.getSecondName(),
                authorEntityFirst.getThirdName(),
                authorEntityFirst.getBookEntities().stream().map(BookEntity::getName).toList());
        when(mapper.authorEntityToAuthorResponse(authorEntityFirst)).thenReturn(expected);
        final AuthorResponse actual = service.getAuthorById(1L);

        assertEquals(expected, actual);
    }

    @Test
    void testGetAuthorByNotExistingAuthorId() {
        when(authorDao.selectAuthorById(4L)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> service.getAuthorById(4L));
    }

    @Test
    void testUpdateAuthor() {
        AuthorModifyRequest authorModifyRequest
                = new AuthorModifyRequest(1,
                "firstnameChanged",
                "secondnameChanged",
                "thirdnameChanged");
        assertDoesNotThrow(() -> service.updateAuthor(authorModifyRequest, 1L));
    }

    @Test
    void testUpdateAuthorWithWrongPathVariableId() {
        AuthorModifyRequest authorModifyRequest
                = new AuthorModifyRequest(1,
                "firstnameChanged",
                "secondnameChanged",
                "thirdnameChanged");
        assertThrows(BadRequestException.class, () -> service.updateAuthor(authorModifyRequest, 2L));
    }

    @Test
    void testUpdateAuthorWithNotExistingAuthorId() {
        AuthorModifyRequest authorModifyRequest
                = new AuthorModifyRequest(5,
                "firstnameChanged",
                "secondnameChanged",
                "thirdnameChanged");
        doThrow(new AuthorNotFoundException(5L)).when(authorDao).updateAuthor(any());
        assertThrows(AuthorNotFoundException.class, () -> service.updateAuthor(authorModifyRequest, 5L));
    }

    @Test
    void testDeleteAuthorById() {
        when(authorDao.deleteAuthorById(1L)).thenReturn(authorEntityFirst);
        assertDoesNotThrow(() -> service.deleteAuthorById(1L));
    }

    @Test
    void testDeleteAuthorByWrongId() {
        when(authorDao.deleteAuthorById(4L)).thenThrow(new AuthorNotFoundException(4L));
        assertThrows(AuthorNotFoundException.class, () -> service.deleteAuthorById(4L));
    }
}