package com.example.jwallet.account.user.control;

import com.example.jwallet.account.user.entity.CreateUserRequest;
import com.example.jwallet.account.user.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Singleton
@Startup
public class SecurityManager {

    @Inject
    UserService userService;

    @Inject
    UserSession userSession;

    @PostConstruct
    @PreDestroy
    private void init() {
        //Do prep work.
    }

    public void saveUser(final CreateUserRequest request) {
        userService.createUser(request);
    }

    public User getCurrentlyAuthenticatedUser() {
        //TODO - implement with UserSession
        final User user = new User();
        user.setFirstName("Question");
        user.setLastName("Instead");
        return user;
    }


}
