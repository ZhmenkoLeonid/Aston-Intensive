package com.zhmenko.user.service;

import com.google.inject.Inject;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user.data.dao.UserDao;
import com.zhmenko.user.data.model.UserEntity;
import com.zhmenko.user.mapper.UserMapper;
import com.zhmenko.user.model.UserInsertRequest;
import com.zhmenko.user.model.UserModifyRequest;
import com.zhmenko.user.model.UserResponse;
import com.zhmenko.user.validator.UserValidator;

/**
 * Provides methods to interact with the User data.
 */
public class UserServiceImpl implements UserService {
    private final UserDao userDAO;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    /**
     * Constructor for UserServiceImpl.
     * Initializes the UserDao, UserMapper, and UserValidator.
     *
     * @param userDAO       the UserDao for interacting with the database
     * @param userMapper    the UserMapper for mapping between entities and requests
     * @param userValidator the UserValidator for validating user requests
     */
    @Inject
    public UserServiceImpl(final UserDao userDAO, final UserMapper userMapper, final UserValidator userValidator) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    /**
     * Adds a new user to the database.
     *
     * @param user the UserInsertRequest containing the user data
     * @throws BadRequestException if the user is invalid
     */
    @Override
    public void addUser(final UserInsertRequest user) {
        if (!userValidator.validate(user)) {
            throw new BadRequestException("Invalid user");
        }
        userDAO.insertUser(userMapper.userInsertRequestToUserEntity(user));
    }

    /**
     * Retrieves a user by their ID from the database.
     *
     * @param id the ID of the user to retrieve
     * @return the UserResponse containing the user data
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    @Override
    public UserResponse getUserById(final int id) {
        UserEntity userEntity = userDAO.selectUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.userEntityToUserResponse(userEntity);
    }

    /**
     * Updates a user in the database.
     *
     * @param user the UserModifyRequest containing the updated user data
     * @param id   the ID of the user to update
     * @throws BadRequestException if the user is invalid
     */
    @Override
    public void updateUser(final UserModifyRequest user, final int id) {
        if (!userValidator.validate(user, id)) {
            throw new BadRequestException("Invalid user");
        }
        userDAO.updateUser(userMapper.userModifyRequestToUserEntity(user), id);
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    public void deleteUserById(final int id) {
        userDAO.deleteUserById(id);
    }
}
