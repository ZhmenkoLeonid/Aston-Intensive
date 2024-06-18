package com.zhmenko.book.service;

import com.zhmenko.book.model.BookInsertRequest;
import com.zhmenko.book.model.BookModifyRequest;
import com.zhmenko.book.model.BookResponse;

public interface BookService {
    void addBook(final BookInsertRequest bookInsertRequest);

    BookResponse getBookById(final int id);

    void updateBook(final BookModifyRequest bookModifyRequest, final int id);

    void deleteBookById(final int id);
}
