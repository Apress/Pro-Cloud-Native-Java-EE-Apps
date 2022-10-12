package com.example.jwallet.account.user.control;

import com.example.jwallet.account.user.entity.CreateUserRequest;
import com.example.jwallet.account.user.entity.UserResponse;

public interface UserFacade {

    UserResponse createUser(final CreateUserRequest createUserRequest);

    UserResponse getUser(final Long id);

}
