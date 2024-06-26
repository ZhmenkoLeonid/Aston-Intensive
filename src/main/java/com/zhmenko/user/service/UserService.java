package com.zhmenko.user.service;

import com.zhmenko.user.model.request.*;
import com.zhmenko.user.model.response.BillingDetailsResponse;
import com.zhmenko.user.model.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse addUser(final UserInsertRequest user);

    UserResponse getUserById(final Long id);

    List<UserResponse> getAll();

    UserResponse updateUser(final UserModifyRequest user, final Long id);

    UserResponse deleteUserById(final Long id);

    UserResponse addBook(UserBookModifyRequest userBookModifyRequest);

    UserResponse removeBook(UserBookModifyRequest userBookModifyRequest);

    BillingDetailsResponse addBillingDetails(BillingDetailsInsertRequest billingDetailsInsertRequest);

    BillingDetailsResponse removeBillingDetails(BillingDetailsModifyRequest billingDetailsRequest);
}
