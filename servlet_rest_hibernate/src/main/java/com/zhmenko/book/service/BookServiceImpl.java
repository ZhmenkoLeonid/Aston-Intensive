package com.zhmenko.book.service;

import com.google.inject.Inject;
import com.zhmenko.book.data.dao.BookDao;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.book.mapper.BookCollectionMapper;
import com.zhmenko.book.mapper.BookMapper;
import com.zhmenko.book.model.BookInsertRequest;
import com.zhmenko.book.model.BookModifyRequest;
import com.zhmenko.book.model.BookResponse;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.util.ValidationUtils;

import java.util.List;

/**
 * Business logic linked to books
 */
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final BookMapper bookMapper;

    private final BookCollectionMapper bookCollectionMapper;

    @Inject
    public BookServiceImpl(final BookDao bookDao,
                           final BookMapper bookMapper,
                           final BookCollectionMapper bookCollectionMapper) {
        this.bookDao = bookDao;
        this.bookMapper = bookMapper;
        this.bookCollectionMapper = bookCollectionMapper;
    }

    /**
     * Adds a new book to the database.
     *
     * @param bookInsertRequest the request containing the details of the new book
     * @throws BadRequestException if the request is invalid
     */
    @Override
    public BookResponse addBook(final BookInsertRequest bookInsertRequest) {
        ValidationUtils.validate(bookInsertRequest);
        final BookEntity bookEntity = bookDao.insertBook(bookMapper.bookInsertRequestBookEntity(bookInsertRequest));
        return bookMapper.bookEntityToBookResponse(bookEntity);
    }

    /**
     * Retrieves a book from the database by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return the book with the specified ID, or throws an exception if not found
     * @throws BookNotFoundException if the book with the specified ID is not found
     */
    @Override
    public BookResponse getBookById(final Long id) {
        BookEntity bookEntity = bookDao.selectBookById(id).orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.bookEntityToBookResponse(bookEntity);
    }

    @Override
    public List<BookResponse> getAll() {
        return bookCollectionMapper.bookEntityCollectionToBookRepsonseList(bookDao.selectAll());
    }

    /**
     * Updates an existing book in the database.
     *
     * @param bookModifyRequest the request containing the details of the updated book
     * @param id                the ID of the book to update
     * @throws BadRequestException if the request is invalid
     */
    @Override
    public BookResponse updateBook(final BookModifyRequest bookModifyRequest, final Long id) {
        ValidationUtils.validate(bookModifyRequest);
        if (bookModifyRequest.getId() != id) throw new BadRequestException("Path variable id must be equal to body id");
        final BookEntity bookEntity = bookDao.updateBook(bookMapper.bookModifyRequestToBookEntity(bookModifyRequest));
        return bookMapper.bookEntityToBookResponse(bookEntity);
    }

    /**
     * Deletes a book from the database by its ID.
     *
     * @param id the ID of the book to delete
     */
    @Override
    public BookResponse deleteBookById(final Long id) {
        final BookEntity bookEntity = bookDao.deleteBookById(id);
        return bookMapper.bookEntityToBookResponse(bookEntity);
    }
}
