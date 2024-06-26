package com.zhmenko.author.service;

import com.google.inject.Inject;
import com.zhmenko.author.data.dao.AuthorDao;
import com.zhmenko.author.data.model.AuthorEntity;
import com.zhmenko.author.mapper.AuthorCollectionMapper;
import com.zhmenko.author.mapper.AuthorMapper;
import com.zhmenko.author.model.AuthorInsertRequest;
import com.zhmenko.author.model.AuthorModifyRequest;
import com.zhmenko.author.model.AuthorResponse;
import com.zhmenko.author.validator.AuthorValidator;
import com.zhmenko.exception.AuthorNotFoundException;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.util.ValidationUtils;

import java.util.List;

/**
 * Provides methods to interact with the User data.
 */
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    private final AuthorMapper authorMapper;

    private final AuthorCollectionMapper authorCollectionMapper;

    private final AuthorValidator authorValidator;
    /**
     * Constructor for the {@link AuthorServiceImpl} class.
     *
     * @param authorDao       The {@link AuthorDao} instance used for database operations.
     * @param authorMapper    The {@link AuthorMapper} instance used for mapping between domain and data models.
     * @param authorValidator The {@link AuthorValidator} instance used for validating input data.
     */
    @Inject
    public AuthorServiceImpl(final AuthorDao authorDao,
                             final AuthorMapper authorMapper,
                             final AuthorCollectionMapper authorCollectionMapper,
                             final AuthorValidator authorValidator) {
        this.authorDao = authorDao;
        this.authorMapper = authorMapper;
        this.authorCollectionMapper = authorCollectionMapper;
        this.authorValidator = authorValidator;
    }

    /**
     * Adds a new author to the database.
     *
     * @param authorInsertRequest The {@link AuthorInsertRequest} containing the details of the new author.
     * @throws BadRequestException If the author insert request is invalid.
     */
    @Override
    public AuthorResponse addAuthor(final AuthorInsertRequest authorInsertRequest) {
        ValidationUtils.validate(authorInsertRequest);
        final AuthorEntity author = authorDao.insertAuthor(authorMapper.authorInsertRequestToAuthorEntity(authorInsertRequest));
        return authorMapper.authorEntityToAuthorResponse(author);
    }

    /**
     * Retrieves an author by its ID from the database.
     *
     * @param id The ID of the author to retrieve.
     * @return The {@link AuthorResponse} containing the details of the author, or null if the author was not found.
     * @throws AuthorNotFoundException If the author with the given ID was not found in the database.
     */
    @Override
    public AuthorResponse getAuthorById(final Long id) {
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
    public AuthorResponse updateAuthor(final AuthorModifyRequest authorModifyRequest, final Long id) {
        ValidationUtils.validate(authorModifyRequest);
        if (authorModifyRequest.getId() != id) throw new BadRequestException("Path variable id must be equal to body id");
        final AuthorEntity author = authorDao.updateAuthor(authorMapper.authorModifyRequestToAuthorEntity(authorModifyRequest));
        return authorMapper.authorEntityToAuthorResponse(author);
    }

    /**
     * Deletes an author from the database by its ID.
     *
     * @param id The ID of the author to delete.
     */
    @Override
    public AuthorResponse deleteAuthorById(final Long id) {
        final AuthorEntity author = authorDao.deleteAuthorById(id);
        return authorMapper.authorEntityToAuthorResponse(author);
    }

    @Override
    public List<AuthorResponse> getAll() {
        return authorCollectionMapper.authorEntityCollectionToAuthorResponseList(authorDao.selectAll());
    }

}
