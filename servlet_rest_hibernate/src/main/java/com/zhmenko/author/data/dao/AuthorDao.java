package com.zhmenko.author.data.dao;

import com.zhmenko.author.data.model.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    AuthorEntity insertAuthor(final AuthorEntity author);

    Optional<AuthorEntity> selectAuthorById(final Long id);

    List<AuthorEntity> selectAll();

    AuthorEntity updateAuthor(final AuthorEntity author);

    AuthorEntity deleteAuthorById(final Long id);

    boolean isExistById(final Long id);
}
