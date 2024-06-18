package com.zhmenko.author.service;

import com.google.inject.Inject;
import com.zhmenko.author.data.dao.AuthorDao;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.author.mapper.AuthorMapper;
import com.zhmenko.author.model.AuthorInsertRequest;
import com.zhmenko.author.model.AuthorModifyRequest;
import com.zhmenko.author.model.AuthorResponse;
import com.zhmenko.author.validator.AuthorValidator;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.exception.BadRequestException;

/**
 * Provides methods to interact with the User data.
 */
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    private final AuthorMapper authorMapper;

    private final AuthorValidator authorValidator;

    /**
     * Constructor for the {@link AuthorServiceImpl} class.
     *
     * @param authorDao       The {@link AuthorDao} instance used for database operations.
     * @param authorMapper    The {@link AuthorMapper} instance used for mapping between domain and data models.
     * @param authorValidator The {@link AuthorValidator} instance used for validating input data.
     */
    @Inject
    public AuthorServiceImpl(final AuthorDao authorDao, final AuthorMapper authorMapper,
                             final AuthorValidator authorValidator) {
        this.authorDao = authorDao;
        this.authorMapper = authorMapper;
        this.authorValidator = authorValidator;
    }

    /**
     * Adds a new author to the database.
     *
     * @param authorInsertRequest The {@link AuthorInsertRequest} containing the details of the new author.
     * @throws BadRequestException If the author insert request is invalid.
     */
    @Override
    public void addAuthor(final AuthorInsertRequest authorInsertRequest) {
        if (!authorValidator.validate(authorInsertRequest))
            throw new BadRequestException("Invalid author insert request");
        authorDao.insertAuthor(authorMapper.authorInsertRequestToAuthorEntity(authorInsertRequest));
    }

    /**
     * Retrieves an author by its ID from the database.
     *
     * @param id The ID of the author to retrieve.
     * @return The {@link AuthorResponse} containing the details of the author, or null if the author was not found.
     * @throws AuthorNotFoundException If the author with the given ID was not found in the database.
     */
    @Override
    public AuthorResponse getAuthorById(final int id) {
        final AuthorEntity authorEntity = authorDao.selectAuthorById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        return authorMapper.authorEntityToAuthorResponse(authorEntity);
    }

    /**
     * Updates an existing author in the database.
     *
     * @param authorModifyRequest The {@link AuthorModifyRequest} containing the details of the author to update.
     * @param id                  The ID of the author to update.
     * @throws BadRequestException If the author modify request is invalid.
     */
    @Override
    public void updateAuthor(final AuthorModifyRequest authorModifyRequest, final int id) {
        if (!authorValidator.validate(authorModifyRequest, id))
            throw new BadRequestException("Invalid author modify request");
        authorDao.updateAuthor(authorMapper.authorModifyRequestToAuthorEntity(authorModifyRequest), id);
    }

    /**
     * Deletes an author from the database by its ID.
     *
     * @param id The ID of the author to delete.
     */
    @Override
    public void deleteAuthorById(final int id) {
        authorDao.deleteAuthorById(id);
    }
}
