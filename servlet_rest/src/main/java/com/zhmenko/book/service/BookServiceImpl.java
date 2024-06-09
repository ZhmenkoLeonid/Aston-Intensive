package com.zhmenko.book.service;

import com.google.inject.Inject;
import com.zhmenko.book.data.dao.BookDao;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.book.mapper.BookMapper;
import com.zhmenko.book.model.BookInsertRequest;
import com.zhmenko.book.model.BookModifyRequest;
import com.zhmenko.book.model.BookResponse;
import com.zhmenko.book.validator.BookValidator;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.BookNotFoundException;

/**
 * Business logic linked to books
 */
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final BookMapper bookMapper;
    private final BookValidator bookValidator;

    /**
     * Constructor for initializing the dependencies.
     *
     * @param bookDao       the database access layer for books
     * @param bookValidator the validator for book operations
     * @param bookMapper    the mapper for converting between domain and DTO objects
     */
    @Inject
    public BookServiceImpl(final BookDao bookDao, final BookValidator bookValidator,
                           final BookMapper bookMapper) {
        this.bookDao = bookDao;
        this.bookMapper = bookMapper;
        this.bookValidator = bookValidator;
    }

    /**
     * Adds a new book to the database.
     *
     * @param bookInsertRequest the request containing the details of the new book
     * @throws BadRequestException if the request is invalid
     */
    @Override
    public void addBook(final BookInsertRequest bookInsertRequest) {
        if (!bookValidator.validate(bookInsertRequest)) {
            throw new BadRequestException("Invalid book insert request");
        }
        bookDao.insertBook(bookMapper.bookInsertRequestBookEntity(bookInsertRequest));
    }

    /**
     * Retrieves a book from the database by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return the book with the specified ID, or throws an exception if not found
     * @throws BookNotFoundException if the book with the specified ID is not found
     */
    @Override
    public BookResponse getBookById(final int id) {
        BookEntity bookEntity = bookDao.selectBookById(id).orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.bookEntityToBookResponse(bookEntity);
    }

    /**
     * Updates an existing book in the database.
     *
     * @param bookModifyRequest the request containing the details of the updated book
     * @param id                the ID of the book to update
     * @throws BadRequestException if the request is invalid
     */
    @Override
    public void updateBook(final BookModifyRequest bookModifyRequest, final int id) {
        if (!bookValidator.validate(bookModifyRequest, id)) {
            throw new BadRequestException("Invalid book modify request");
        }
        bookDao.updateBook(bookMapper.bookModifyRequestToBookEntity(bookModifyRequest), id);
    }

    /**
     * Deletes a book from the database by its ID.
     *
     * @param id the ID of the book to delete
     */
    @Override
    public void deleteBookById(final int id) {
        bookDao.deleteBookById(id);
    }
}
