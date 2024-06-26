package com.zhmenko.user.service;

import com.google.inject.Inject;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user.data.dao.UserDao;
import com.zhmenko.user.data.model.BillingDetailsEntity;
import com.zhmenko.user.data.model.UserEntity;
import com.zhmenko.user.mapper.BillingDetailsMapper;
import com.zhmenko.user.mapper.UserCollectionMapper;
import com.zhmenko.user.mapper.UserMapper;
import com.zhmenko.user.model.request.*;
import com.zhmenko.user.model.response.BillingDetailsResponse;
import com.zhmenko.user.model.response.UserResponse;
import com.zhmenko.user.validator.UserValidator;
import com.zhmenko.util.ValidationUtils;

import java.util.List;

/**
 * Provides methods to interact with the User data.
 */
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final UserCollectionMapper userCollectionMapper;
    private final BillingDetailsMapper billingDetailsMapper;
    private final UserValidator userValidator;

    @Inject
    public UserServiceImpl(final UserDao userDao,
                           final UserMapper userMapper,
                           final UserValidator userValidator,
                           final UserCollectionMapper userCollectionMapper,
                           final BillingDetailsMapper billingDetailsMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.userCollectionMapper = userCollectionMapper;
        this.billingDetailsMapper = billingDetailsMapper;
        this.userValidator = userValidator;
    }

    /**
     * Adds a new user to the database.
     *
     * @param user the UserInsertRequest containing the user data
     * @throws BadRequestException if the user is invalid
     */
    @Override
    public UserResponse addUser(final UserInsertRequest user) {
        if (!userValidator.validate(user)) {
            throw new BadRequestException("Invalid user");
        }
        final UserEntity userEntity = userDao.insertUser(userMapper.userInsertRequestToUserEntity(user));
        return userMapper.userEntityToUserResponse(userEntity);
    }

    /**
     * Retrieves a user by their ID from the database.
     *
     * @param id the ID of the user to retrieve
     * @return the UserResponse containing the user data
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    @Override
    public UserResponse getUserById(final Long id) {
        UserEntity userEntity = userDao.selectUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.userEntityToUserResponse(userEntity);
    }

    @Override
    public List<UserResponse> getAll() {
        return userCollectionMapper.userEntityCollectionToUserResponseList(userDao.selectAll());
    }

    /**
     * Updates a user in the database.
     *
     * @param user the UserModifyRequest containing the updated user data
     * @param id   the ID of the user to update
     * @throws BadRequestException if the user is invalid
     */
    @Override
    public UserResponse updateUser(final UserModifyRequest user, final Long id) {
        ValidationUtils.validate(user);
        if (user.getId() != id) throw new BadRequestException("Path variable id must be equal to body id");
        final UserEntity userEntity = userDao.updateUser(userMapper.userModifyRequestToUserEntity(user));
        return userMapper.userEntityToUserResponse(userEntity);
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    public UserResponse deleteUserById(final Long id) {
        final UserEntity userEntity = userDao.deleteUserById(id);
        return userMapper.userEntityToUserResponse(userEntity);
    }

    @Override
    public UserResponse addBook(final UserBookModifyRequest userBookModifyRequest) {
        ValidationUtils.validate(userBookModifyRequest);
        final UserEntity userEntity = userDao.addBook(new UserEntity(userBookModifyRequest.getUserId()),
                new BookEntity(userBookModifyRequest.getBookId()));
        return userMapper.userEntityToUserResponse(userEntity);
    }

    @Override
    public UserResponse removeBook(final UserBookModifyRequest userBookModifyRequest) {
        ValidationUtils.validate(userBookModifyRequest);
        final UserEntity userEntity = userDao.removeBook(new UserEntity(userBookModifyRequest.getUserId()),
                new BookEntity(userBookModifyRequest.getBookId()));
        return userMapper.userEntityToUserResponse(userEntity);
    }

    @Override
    public BillingDetailsResponse addBillingDetails(final BillingDetailsInsertRequest billingDetailsInsertRequest) {
        ValidationUtils.validate(billingDetailsInsertRequest);
        final BillingDetailsEntity response = userDao.addBillingDetails(billingDetailsMapper.toEntity(billingDetailsInsertRequest));
        return billingDetailsMapper.toDto(response);
    }

    @Override
    public BillingDetailsResponse removeBillingDetails(final BillingDetailsModifyRequest billingDetailsRequest) {
        ValidationUtils.validate(billingDetailsRequest);
        final BillingDetailsEntity response = userDao
                .removeBillingDetails(billingDetailsRequest.getUserId(), billingDetailsRequest.getBillingDetailsId());
        return billingDetailsMapper.toDto(response);
    }
}
