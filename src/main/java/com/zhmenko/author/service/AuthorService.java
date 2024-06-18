package com.zhmenko.author.service;

import com.zhmenko.author.model.AuthorInsertRequest;
import com.zhmenko.author.model.AuthorModifyRequest;
import com.zhmenko.author.model.AuthorResponse;

public interface AuthorService {
    void addAuthor(final AuthorInsertRequest authorInsertRequest);

    AuthorResponse getAuthorById(final int id);

    void updateAuthor(final AuthorModifyRequest authorModifyRequest, final int id);

    void deleteAuthorById(final int id);
}
