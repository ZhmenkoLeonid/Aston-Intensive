package com.zhmenko.author.data.dao;

import com.google.inject.Provider;
import com.zhmenko.AbstractDaoTest;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.exception.AuthorNotFoundException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDaoTest extends AbstractDaoTest {
    private static AuthorDao authorDao;

    @BeforeAll
    static void inject() {
        authorDao = injector.getInstance(AuthorDao.class);
    }

    @Test
    void testInsertAuthor() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setFirstName("fn");
        authorEntity.setSecondName("sn");
        authorEntity.setThirdName("da");

        authorDao.insertAuthor(authorEntity);

        final Optional<AuthorEntity> newAuthorOpt = authorDao.selectAuthorById(3L);
        assertTrue(newAuthorOpt.isPresent());
        final AuthorEntity newAuthor = newAuthorOpt.get();
        assertEquals(3, newAuthor.getId());
        assertEquals(authorEntity.getFirstName(), newAuthor.getFirstName());
        assertEquals(authorEntity.getSecondName(), newAuthor.getSecondName());
        assertEquals(authorEntity.getThirdName(), newAuthor.getThirdName());
    }

    @Test
    void testSelectAuthorById() {
        final Optional<AuthorEntity> authorOpt = authorDao.selectAuthorById(1L);
        assertTrue(authorOpt.isPresent());
        final AuthorEntity author = authorOpt.get();
        assertEquals(1, author.getId());
        assertEquals("Abel", author.getFirstName());
        assertEquals("Latoya", author.getSecondName());
        assertEquals("Abraham", author.getThirdName());
    }

    @Test
    void testSelectAuthorByNotExistingId() {
        assertThrows(NoSuchElementException.class, () -> authorDao.selectAuthorById(3L).get());
    }

    @Test
    void testUpdateAuthor() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(1L);
        authorEntity.setFirstName("fn");
        authorEntity.setSecondName("sn");
        authorEntity.setThirdName("da");

        final AuthorEntity author = authorDao.updateAuthor(authorEntity);

        assertNotNull(author);
        final Optional<AuthorEntity> updatedAuthorOpt = authorDao.selectAuthorById(1L);
        assertTrue(updatedAuthorOpt.isPresent());
        final AuthorEntity updatedAuthor = updatedAuthorOpt.get();
        assertEquals(updatedAuthor.getId(), authorEntity.getId());
        assertEquals(updatedAuthor.getFirstName(), authorEntity.getFirstName());
        assertEquals(updatedAuthor.getSecondName(), authorEntity.getSecondName());
        assertEquals(updatedAuthor.getThirdName(), authorEntity.getThirdName());
        assertEquals(2, updatedAuthor.getBookEntities().size());
    }

    @Test
    void testUpdateAuthorWithNotExistingId() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(3L);
        authorEntity.setFirstName("fn");
        authorEntity.setSecondName("sn");
        authorEntity.setThirdName("da");

        assertThrows(AuthorNotFoundException.class, () -> authorDao.updateAuthor(authorEntity));
    }

    @Test
    void testDeleteAuthorById() {
        final AuthorEntity author = authorDao.deleteAuthorById(1L);
        assertNotNull(author);
        assertFalse(authorDao.isExistById(1L));
    }

    @Test
    void testDeleteAuthorByNotExistingId() {
        assertThrows(AuthorNotFoundException.class, () -> authorDao.deleteAuthorById(3L));
    }

    @Test
    void testIsExistById() {
        assertTrue(authorDao.isExistById(1L));
    }

    @Test
    void testIsExistByNotExistingId() {
        assertFalse(authorDao.isExistById(3L));
    }
}