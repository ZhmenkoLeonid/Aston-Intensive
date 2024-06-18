package com.zhmenko.user.service;

import com.zhmenko.user.model.UserInsertRequest;
import com.zhmenko.user.model.UserModifyRequest;
import com.zhmenko.user.model.UserResponse;

public interface UserService {
    void addUser(final UserInsertRequest user);

    UserResponse getUserById(final int id);

    void updateUser(final UserModifyRequest user, final int id);

    void deleteUserById(final int id);

}
