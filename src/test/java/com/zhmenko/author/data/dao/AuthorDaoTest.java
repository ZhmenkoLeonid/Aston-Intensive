package com.zhmenko.author.data.dao;

import com.zhmenko.AbstractDaoTest;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.exception.AuthorNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

        final Optional<AuthorEntity> newAuthorOpt = authorDao.selectAuthorById(3);
        assertTrue(newAuthorOpt.isPresent());
        final AuthorEntity newAuthor = newAuthorOpt.get();
        assertEquals(3, newAuthor.getId());
        assertEquals(authorEntity.getFirstName(), newAuthor.getFirstName());
        assertEquals(authorEntity.getSecondName(), newAuthor.getSecondName());
        assertEquals(authorEntity.getThirdName(), newAuthor.getThirdName());
    }

    @Test
    void testSelectAuthorById() {
        final Optional<AuthorEntity> authorOpt = authorDao.selectAuthorById(1);
        assertTrue(authorOpt.isPresent());
        final AuthorEntity author = authorOpt.get();
        assertEquals(1, author.getId());
        assertEquals("Abel", author.getFirstName());
        assertEquals("Latoya", author.getSecondName());
        assertEquals("Abraham", author.getThirdName());
    }

    @Test
    void testSelectAuthorByNotExistingId() {
        assertThrows(AuthorNotFoundException.class, () -> authorDao.selectAuthorById(3));
    }

    @Test
    void testUpdateAuthor() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(1);
        authorEntity.setFirstName("fn");
        authorEntity.setSecondName("sn");
        authorEntity.setThirdName("da");

        final boolean b = authorDao.updateAuthor(authorEntity, authorEntity.getId());

        assertTrue(b);
        final Optional<AuthorEntity> updatedAuthorOpt = authorDao.selectAuthorById(1);
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
        authorEntity.setId(3);
        authorEntity.setFirstName("fn");
        authorEntity.setSecondName("sn");
        authorEntity.setThirdName("da");

        assertThrows(AuthorNotFoundException.class, () -> authorDao.updateAuthor(authorEntity, authorEntity.getId()));
    }

    @Test
    void testDeleteAuthorById() {
        final boolean b = authorDao.deleteAuthorById(1);
        assertTrue(b);
        assertFalse(authorDao.isExistById(1));
    }

    @Test
    void testDeleteAuthorByNotExistingId() {
        assertThrows(AuthorNotFoundException.class, () -> authorDao.deleteAuthorById(3));
    }

    @Test
    void testIsExistById() {
        assertTrue(authorDao.isExistById(1));
    }

    @Test
    void testIsExistByNotExistingId() {
        assertFalse(authorDao.isExistById(3));
    }
}