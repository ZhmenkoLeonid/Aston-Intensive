package com.zhmenko.book.data.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.exception.SQLRuntimeException;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jpa.SpecHints;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Dao for user CRUD operations
 */
@Singleton
public class BookDaoImpl implements BookDao {
    private static final Logger log = LogManager.getLogger(BookDaoImpl.class);

    private final Provider<EntityManager> entityManagerProvider;

    /**
     * Initializes the {@code BookDaoImpl} with the provided dependencies.
     */
    @Inject
    public BookDaoImpl(final Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    /**
     * Inserts a new book into the database.
     *
     * @param bookEntity the {@link BookEntity} to be inserted
     * @return true if the insertion was successful, false otherwise
     * @throws AuthorNotFoundException if the author of the book does not exist in the database
     * @throws SQLRuntimeException     if an error occurs during the database operation
     */
    @Transactional
    public BookEntity insertBook(final BookEntity bookEntity) {
        final EntityManager entityManager = entityManagerProvider.get();
        // check author exist
        final Long authorId = bookEntity.getAuthor().getId();
        final AuthorEntity author = entityManager.find(AuthorEntity.class, authorId);
        if (author == null) throw new AuthorNotFoundException(authorId);

        entityManager.persist(bookEntity);
        log.info("saving book using id - {}", bookEntity.getId());
        return bookEntity;
    }

    /**
     * Retrieves a book by its id from the database.
     *
     * @param id the id of the book to be retrieved
     * @return an {@link Optional} containing the {@link BookEntity} if found, otherwise an empty {@link Optional}
     * @throws SQLRuntimeException if an error occurs during the database operation
     */
    @Transactional
    public Optional<BookEntity> selectBookById(final Long id) {
        final EntityManager entityManager = entityManagerProvider.get();
        EntityGraph entityGraph = entityManager.getEntityGraph("graph.Books");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        BookEntity bookEntity = entityManager.find(BookEntity.class, id, properties);
        log.info("finding book with id - {}", id);
        return Optional.ofNullable(bookEntity);
    }

    @Override
    @SuppressWarnings("unckecked")
    @Transactional
    public List<BookEntity> selectAll() {
        final EntityManager entityManager = entityManagerProvider.get();
        EntityGraph entityGraph = entityManager.getEntityGraph("graph.Books");
        final Query<BookEntity> books = entityManager.unwrap(Session.class)
                .createQuery("from BookEntity ", BookEntity.class)
                .setHint(SpecHints.HINT_SPEC_FETCH_GRAPH, entityGraph);
        log.info("find all books");
        return books.getResultList();
    }

    /**
     * Checks if a book with the given id exists in the database.
     *
     * @param bookId the id of the book to be checked
     * @return true if the book exists, false otherwise
     * @throws SQLRuntimeException if an error occurs during the database operation
     */
    @Override
    public boolean isExistById(final Long bookId) {
        final EntityManager entityManager = entityManagerProvider.get();
        final long cnt = (long) entityManager
                .createQuery("SELECT COUNT(b) " +
                             "FROM BookEntity b " +
                             "WHERE b.id=:id")
                .setParameter("id", bookId)
                .getSingleResult();
        return cnt == 1;
    }


    /**
     * Updates a book in the database.
     *
     * @param bookEntity the {@link BookEntity} to be updated
     * @return true if the update was successful, false otherwise
     * @throws AuthorNotFoundException if the author of the book does not exist in the database
     * @throws SQLRuntimeException     if an error occurs during the database operation
     */
    @Transactional
    public BookEntity updateBook(final BookEntity bookEntity) {
        final EntityManager entityManager = entityManagerProvider.get();
        // check author exist
        final Long newAuthorId = bookEntity.getAuthor().getId();
        final AuthorEntity author = entityManager.find(AuthorEntity.class, newAuthorId);
        if (author == null) throw new AuthorNotFoundException(newAuthorId);

        final BookEntity updatedBook = selectBookById(bookEntity.getId())
                .orElseThrow(() -> new BookNotFoundException(bookEntity.getId()));
        updatedBook.setName(bookEntity.getName());
        updatedBook.setAuthor(bookEntity.getAuthor());
        log.info("updating book with id - {}", bookEntity.getId());
        return updatedBook;
    }

    /**
     * Deletes a book by its id from the database.
     *
     * @param id the id of the book to be deleted
     * @return true if the deletion was successful, false otherwise
     * @throws SQLRuntimeException if an error occurs during the database operation
     */
    @Transactional
    public BookEntity deleteBookById(final Long id) {
        final EntityManager entityManager = entityManagerProvider.get();
        final Optional<BookEntity> bookEntityOptional = selectBookById(id);
        final BookEntity bookEntity = bookEntityOptional.orElseThrow(() -> new BookNotFoundException(id));
        entityManager.remove(bookEntity);
        log.info("removing book with id - {}", id);
        return bookEntity;
    }
}
