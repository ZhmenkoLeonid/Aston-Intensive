package com.zhmenko.author.service;

import com.zhmenko.author.model.AuthorInsertRequest;
import com.zhmenko.author.model.AuthorModifyRequest;
import com.zhmenko.author.model.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse addAuthor(final AuthorInsertRequest authorInsertRequest);

    AuthorResponse getAuthorById(final Long id);

    AuthorResponse updateAuthor(final AuthorModifyRequest authorModifyRequest, final Long id);

    AuthorResponse deleteAuthorById(final Long id);

    List<AuthorResponse> getAll();
}
