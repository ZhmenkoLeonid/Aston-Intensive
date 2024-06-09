package com.zhmenko.book.data.dao;

import com.zhmenko.AbstractDaoTest;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user.data.model.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
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
        author.setId(2);
        bookEntity.setAuthor(author);
        bookEntity.setName("book name");
        final boolean b = bookDao.insertBook(bookEntity);

        assertTrue(b);
        final Optional<BookEntity> newBookEntityOpt = bookDao.selectBookById(4);
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
        author.setId(3);
        bookEntity.setAuthor(author);
        bookEntity.setName("book name");
        assertThrows(AuthorNotFoundException.class, () -> bookDao.insertBook(bookEntity));
    }

    @Test
    void testSelectBookById() {
        final Optional<BookEntity> bookEntityOptional = bookDao.selectBookById(1);
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
        assertTrue(bookDao.selectBookById(4).isEmpty());
    }

    @Test
    void testSelectBooksByAuthorId() {
        final Set<BookEntity> bookEntities = bookDao.selectBooksByAuthorId(1);
        for (final BookEntity bookEntity : bookEntities) {
            assertTrue(bookEntity.getId() > 0);
        }
        assertEquals(2, bookEntities.size());
    }

    @Test
    void testSelectBooksByNotExistingAuthorId() {
        assertThrows(AuthorNotFoundException.class, () -> bookDao.selectBooksByAuthorId(3));
    }

    @Test
    void testSelectBooksByUserId() {
        final Set<BookEntity> bookEntities = bookDao.selectBooksByUserId(1);
        for (final BookEntity bookEntity : bookEntities) {
            assertTrue(bookEntity.getId() > 0);
        }
        assertEquals(2, bookEntities.size());
    }

    @Test
    void testSelectBooksByNotExistingUserId() {
        assertThrows(UserNotFoundException.class, () -> bookDao.selectBooksByUserId(4));
    }

    @Test
    void testIsExistById() {
        assertTrue(bookDao.isExistById(1));
    }

    @Test
    void testIsExistByNotExistingId() {
        assertFalse(bookDao.isExistById(4));
    }

    @Test
    void isBooksExistById() {
        assertTrue(bookDao.isBooksExistById(Set.of(1, 2, 3)));
    }

    @Test
    void isBooksExistByNotExistingId() {
        assertFalse(bookDao.isBooksExistById(Set.of(1, 2, 4)));
    }

    @Test
    void testUpdateBook() {
        BookEntity bookEntity = new BookEntity();
        AuthorEntity author = new AuthorEntity();
        author.setId(2);
        bookEntity.setAuthor(author);
        bookEntity.setId(1);
        bookEntity.setName("book name");
        final boolean b = bookDao.updateBook(bookEntity, bookEntity.getId());
        assertTrue(b);

        final Optional<BookEntity> updatedBookOpt = bookDao.selectBookById(1);
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
        author.setId(3);
        bookEntity.setAuthor(author);
        bookEntity.setId(1);
        bookEntity.setName("new name");
        assertThrows(AuthorNotFoundException.class, () -> bookDao.updateBook(bookEntity, bookEntity.getId()));
    }

    @Test
    void testDeleteBookById() {
        assertTrue(bookDao.selectBookById(1).isPresent());
        bookDao.deleteBookById(1);
        assertFalse(bookDao.selectBookById(1).isPresent());
    }

    @Test
    void testDeleteBookByNotExistingId() {
        assertThrows(BookNotFoundException.class, () -> bookDao.deleteBookById(4));
    }

    @Test
    void testDeleteBooksByAuthorId() {
        final Set<BookEntity> bookEntities = bookDao.selectBooksByAuthorId(1);
        bookDao.deleteBooksByAuthorId(1);
        final List<Integer> books = bookEntities.stream().map(BookEntity::getId).toList();
        for (final Integer book : books) {
            assertFalse(bookDao.isExistById(book));
        }
    }

    @Test
    void testDeleteBooksByNotExistingAuthorId() {
        assertThrows(AuthorNotFoundException.class, () -> bookDao.deleteBooksByAuthorId(3));
    }
}