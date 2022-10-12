package com.example.jwallet.account.user.boundary;

import com.example.jwallet.account.AbstractAccountIT;
import com.example.jwallet.account.user.entity.CreateUserRequest;
import com.example.jwallet.account.user.entity.UserResponse;
import jakarta.ws.rs.client.Entity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserResourceIT extends AbstractAccountIT {

    @Test
    void createUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setFirstName("John");
        createUserRequest.setLastName("Doe");
        createUserRequest.setEmail("john.doe@example.com");
        createUserRequest.setBirthDay(LocalDate.of(2000, 1, 1));
        createUserRequest.setPassword("password");

        UserResponse createdUser = target.path("users")
                .request().post(Entity.json(createUserRequest), UserResponse.class);

        assertEquals("0", createdUser.getResponse().getResponseCode());
        assertEquals("OK", createdUser.getResponse().getResponseMessage());

        assertNotNull(createdUser.getData().getUserId());
        assertEquals(createUserRequest.getFirstName(), createdUser.getData().getFirstName());
        assertEquals(createUserRequest.getLastName(), createdUser.getData().getLastName());

        // get created user
        UserResponse userResponse = target.path("users/{userId}")
                .resolveTemplate("userId", createdUser.getData().getUserId()).request()
                .get(UserResponse.class);

        assertEquals("0", userResponse.getResponse().getResponseCode());
        assertEquals("OK", userResponse.getResponse().getResponseMessage());

        assertNotNull(userResponse.getData().getUserId());
        assertEquals(createUserRequest.getFirstName(), userResponse.getData().getFirstName());
        assertEquals(createUserRequest.getLastName(), userResponse.getData().getLastName());
    }


}
