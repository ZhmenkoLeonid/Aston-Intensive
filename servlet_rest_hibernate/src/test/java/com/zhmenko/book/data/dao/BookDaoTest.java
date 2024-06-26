package com.zhmenko.book.data.dao;

import com.zhmenko.AbstractDaoTest;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.user.data.model.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookDaoTest extends AbstractDaoTest {
    private static BookDao bookDao;

    @BeforeAll
    public static void injectObjects() {
        bookDao = injector.getInstance(BookDao.class);
    }

    @Test
    void testInsertBook() {
        BookEntity bookEntity = new BookEntity();
        AuthorEntity author = new AuthorEntity();
        author.setId(2L);
        bookEntity.setAuthor(author);
        bookEntity.setName("book name");
        final BookEntity b = bookDao.insertBook(bookEntity);

        assertNotNull(b);
        final Optional<BookEntity> newBookEntityOpt = bookDao.selectBookById(4L);
        assertTrue(newBookEntityOpt.isPresent());
        final BookEntity newBook = newBookEntityOpt.get();
        assertEquals(4, newBook.getId());
        assertEquals(bookEntity.getName(), newBook.getName());
        assertEquals(bookEntity.getAuthor().getId(), author.getId());
    }

    @Test
    void testInsertBookWithWrongAuthorId() {
        BookEntity bookEntity = new BookEntity();
        AuthorEntity author = new AuthorEntity();
        author.setId(3L);
        bookEntity.setAuthor(author);
        bookEntity.setName("book name");
        assertThrows(AuthorNotFoundException.class, () -> bookDao.insertBook(bookEntity));
    }

    @Test
    void testSelectBookById() {
        final Optional<BookEntity> bookEntityOptional = bookDao.selectBookById(1L);
        assertTrue(bookEntityOptional.isPresent());
        final BookEntity bookEntity = bookEntityOptional.get();
        assertEquals(1, bookEntity.getId());
        assertEquals("The Pilgrim’s Progress", bookEntity.getName());
        final AuthorEntity author = bookEntity.getAuthor();
        assertEquals(1, author.getId());
        assertEquals("Abel", author.getFirstName());
        assertEquals("Latoya", author.getSecondName());
        assertEquals("Abraham", author.getThirdName());
        final Set<UserEntity> userEntities = bookEntity.getUserEntities();
        assertEquals(2, userEntities.size());
        for (final UserEntity userEntity : userEntities) {
            assertTrue(userEntity.getId() > 0);
            assertFalse(userEntity.getName().isEmpty());
            assertFalse(userEntity.getEmail().isEmpty());
        }
    }

    @Test
    void testSelectBookByNotExistingId() {
        assertTrue(bookDao.selectBookById(4L).isEmpty());
    }

    @Test
    void testIsExistById() {
        assertTrue(bookDao.isExistById(1L));
    }

    @Test
    void testIsExistByNotExistingId() {
        assertFalse(bookDao.isExistById(4L));
    }

    @Test
    void testUpdateBook() {
        BookEntity bookEntity = new BookEntity();
        AuthorEntity author = new AuthorEntity();
        author.setId(2L);
        bookEntity.setAuthor(author);
        bookEntity.setId(1L);
        bookEntity.setName("book name");
        final BookEntity b = bookDao.updateBook(bookEntity);

        assertNotNull(b);
        final Optional<BookEntity> updatedBookOpt = bookDao.selectBookById(1L);
        assertTrue(updatedBookOpt.isPresent());
        final BookEntity updatedBook = updatedBookOpt.get();
        assertEquals(updatedBook.getId(), bookEntity.getId());
        assertEquals(updatedBook.getName(), bookEntity.getName());
        assertEquals(updatedBook.getAuthor().getId(), author.getId());
        assertEquals(2, updatedBook.getUserEntities().size());
    }

    @Test
    void testUpdateBookWithNotExistingAuthorId() {
        BookEntity bookEntity = new BookEntity();
        AuthorEntity author = new AuthorEntity();
        author.setId(3L);
        bookEntity.setAuthor(author);
        bookEntity.setId(1L);
        bookEntity.setName("new name");
        assertThrows(AuthorNotFoundException.class, () -> bookDao.updateBook(bookEntity));
    }

    @Test
    void testDeleteBookById() {
        assertTrue(bookDao.selectBookById(1L).isPresent());
        bookDao.deleteBookById(1L);
        assertFalse(bookDao.selectBookById(1L).isPresent());
    }

    @Test
    void testDeleteBookByNotExistingId() {
        assertThrows(BookNotFoundException.class, () -> bookDao.deleteBookById(4L));
    }
}