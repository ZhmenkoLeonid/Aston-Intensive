package com.zhmenko.user.data.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.zhmenko.book.data.dao.BookDao;
import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.exception.BadRequestException;
import com.zhmenko.exception.BookNotFoundException;
import com.zhmenko.exception.SQLRuntimeException;
import com.zhmenko.exception.UserNotFoundException;
import com.zhmenko.user.data.model.BillingDetailsEntity;
import com.zhmenko.user.data.model.UserEntity;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jpa.SpecHints;
import org.hibernate.query.Query;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Dao for user CRUD operations
 */
public class UserDaoImpl implements UserDao {
    Logger log = LogManager.getLogger(UserDaoImpl.class);

    private final Provider<EntityManager> entityManagerProvider;
    private final BookDao bookDao;

    @Inject
    public UserDaoImpl(final Provider<EntityManager> entityManagerProvider,
                       BookDao bookDao) {
        this.bookDao = bookDao;
        this.entityManagerProvider = entityManagerProvider;
    }

    /**
     * Insert a new user entity into the users table.
     *
     * @param userEntity The user entity object to be added to the database.
     * @throws SQLRuntimeException if an error occurs while executing the SQL query.
     */
    @Transactional
    public UserEntity insertUser(final UserEntity userEntity) {
        final EntityManager entityManager = entityManagerProvider.get();
        entityManager.persist(userEntity);
        log.info("saving book using id - {}", userEntity.getId());
        return userEntity;
    }

    /**
     * Retrieve a user entity by its id.
     *
     * @param id The id of the user to be retrieved.
     * @return An optional containing the user entity if found, otherwise an empty optional.
     * @throws SQLRuntimeException if an error occurs while executing the SQL query.
     */
    @Transactional
    public Optional<UserEntity> selectUserById(final Long id) {
        final EntityManager entityManager = entityManagerProvider.get();
        EntityGraph entityGraph = entityManager.getEntityGraph("graph.Users");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        UserEntity userEntity = entityManager.find(UserEntity.class, id, properties);
        log.info("finding user with id - {}", id);
        return Optional.ofNullable(userEntity);
    }

    @Override
    @Transactional
    public List<UserEntity> selectAll() {
        final EntityManager entityManager = entityManagerProvider.get();
        EntityGraph entityGraph = entityManager.getEntityGraph("graph.Users");
        final Query<UserEntity> usersQuery = entityManager.unwrap(Session.class)
                .createQuery("from UserEntity", UserEntity.class)
                .setHint(SpecHints.HINT_SPEC_FETCH_GRAPH, entityGraph);
        log.info("find all users");
        return usersQuery.getResultList();
    }

    /**
     * Delete a user from the database if it exists.
     *
     * @param id The id of the user to be deleted.
     * @return True if the user was successfully deleted, false otherwise.
     * @throws SQLRuntimeException   if an error occurs while executing the SQL query.
     * @throws UserNotFoundException if the user does not exist
     */
    @Transactional
    public UserEntity deleteUserById(final Long id) {
        final EntityManager entityManager = entityManagerProvider.get();
        final Optional<UserEntity> userEntityOptional = selectUserById(id);
        final UserEntity userEntity = userEntityOptional.orElseThrow(() -> new UserNotFoundException(id));
        entityManager.remove(userEntity);
        log.info("removing user with id - {}", id);
        return userEntity;
    }

    /**
     * Update a user's state specified by its id.
     *
     * @param userEntity The new user state.
     * @return True if the database was successfully updated, false otherwise.
     * @throws SQLRuntimeException if an error occurs while executing the SQL query.
     */
    @Transactional
    public UserEntity updateUser(final UserEntity userEntity) {
        final UserEntity updatedUser = selectUserById(userEntity.getId())
                .orElseThrow(() -> new UserNotFoundException(userEntity.getId()));
        updatedUser.setName(userEntity.getName());
        updatedUser.setEmail(userEntity.getEmail());
        updatedUser.setCountry(userEntity.getCountry());
        log.info("updating user with id - {}", userEntity.getId());
        return updatedUser;
    }

    /**
     * Check if a user with the specified id exists in the database.
     *
     * @param id The id of the user to check.
     * @return True if a user with the specified id exists in the database, false otherwise.
     * @throws SQLRuntimeException if an error occurs while executing the SQL query.
     */
    @Override
    public boolean isExistById(final Long id) {
        final EntityManager entityManager = entityManagerProvider.get();
        final long cnt = (long) entityManager
                .createQuery("SELECT COUNT(u) " +
                             "FROM UserEntity u " +
                             "WHERE u.id=:id")
                .setParameter("id", id)
                .getSingleResult();
        return cnt == 1;
    }

    @Override
    @Transactional
    public UserEntity addBook(final UserEntity user, final BookEntity book) {
        final EntityManager entityManager = entityManagerProvider.get();
        long bookId = book.getId();
        // check if book exist
        if (!bookDao.isExistById(bookId))
            throw new BookNotFoundException(bookId);
        // check if user exist
        final UserEntity managedUser = selectUserById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));

        final Set<BookEntity> bookEntitySet = managedUser.getBookEntitySet();
        if (bookEntitySet == null || bookEntitySet.contains(book))
            throw new BadRequestException(
                    String.format("User with id %s already contain book with id %s", user.getId(), bookId));
        bookEntitySet.add(book);
        return managedUser;
    }

    @Override
    @Transactional
    public UserEntity removeBook(final UserEntity user, final BookEntity book) {
        final EntityManager entityManager = entityManagerProvider.get();
        long bookId = book.getId();
        // check if book exist
        if (!bookDao.isExistById(bookId)) throw new BookNotFoundException(bookId);
        // check if user exist
        final UserEntity managedUser = selectUserById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));

        final Set<BookEntity> bookEntitySet = managedUser.getBookEntitySet();
        if (bookEntitySet == null || !bookEntitySet.contains(book))
            throw new BadRequestException(
                    String.format("User with id %s dont contain book with id %s", user.getId(), bookId));

        bookEntitySet.remove(book);
        return managedUser;
    }

    @Override
    @Transactional
    public BillingDetailsEntity addBillingDetails(final BillingDetailsEntity billingDetailsEntity) {
        // check if user exist
        final Long userId = billingDetailsEntity.getUser().getId();
        final UserEntity managedUser = selectUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        final Set<BillingDetailsEntity> billingDetailsSet = managedUser.getBillingDetailsSet();
        billingDetailsSet.add(billingDetailsEntity);
        return billingDetailsEntity;
    }

    @Override
    @Transactional
    public BillingDetailsEntity removeBillingDetails(final Long userId, final Long billingDetailsId) {
        // check if user exist
        final UserEntity managedUser = selectUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        final Set<BillingDetailsEntity> billingDetailsSet = managedUser.getBillingDetailsSet();
        Optional<BillingDetailsEntity> toRemoveOptional;
        if (billingDetailsSet == null ||
            (toRemoveOptional = billingDetailsSet
                    .stream()
                    .filter(billingDetailsEntity ->
                            billingDetailsEntity.getId().equals(billingDetailsId))
                    .findFirst()).isEmpty())
            throw new BadRequestException(
                    String.format("User with id %s dont contain billing with id %s", userId, billingDetailsId));
        final BillingDetailsEntity toRemove = toRemoveOptional.get();
        billingDetailsSet.remove(toRemove);
        return toRemove;
    }
}