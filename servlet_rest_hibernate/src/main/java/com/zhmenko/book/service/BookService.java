package com.zhmenko.book.service;

import com.zhmenko.book.model.BookInsertRequest;
import com.zhmenko.book.model.BookModifyRequest;
import com.zhmenko.book.model.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse addBook(final BookInsertRequest bookInsertRequest);

    BookResponse getBookById(final Long id);

    BookResponse updateBook(final BookModifyRequest bookModifyRequest, final Long id);

    BookResponse deleteBookById(final Long id);

    List<BookResponse> getAll();
}
