package com.zhmenko.user.data.dao;

import com.zhmenko.book.data.model.BookEntity;
import com.zhmenko.user.data.model.BillingDetailsEntity;
import com.zhmenko.user.data.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    UserEntity insertUser(final UserEntity user);

    Optional<UserEntity> selectUserById(final Long id);

    List<UserEntity> selectAll();

    UserEntity updateUser(final UserEntity user);

    UserEntity deleteUserById(final Long id);

    boolean isExistById(final Long id);

    UserEntity addBook(final UserEntity user, final BookEntity book);

    UserEntity removeBook(final UserEntity user, final BookEntity book);

    BillingDetailsEntity addBillingDetails(final BillingDetailsEntity billingDetailsEntity);

    BillingDetailsEntity removeBillingDetails(final Long userId, final Long billingDetailsId);
}
