package com.zhmenko.author.data.dao;

import com.zhmenko.author.data.model.AuthorEntity;

import java.util.Optional;

public interface AuthorDao {
    boolean insertAuthor(final AuthorEntity author);

    Optional<AuthorEntity> selectAuthorById(final int id);

    boolean updateAuthor(final AuthorEntity author, final int id);

    boolean deleteAuthorById(final int id);

    boolean isExistById(final int id);
}
