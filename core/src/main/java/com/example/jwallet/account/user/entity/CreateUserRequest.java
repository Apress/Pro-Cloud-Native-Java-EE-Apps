package com.example.jwallet.account.user.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class CreateUserRequest {

    @NotEmpty
    private String firstName;
    @NotEmpty

    private String lastName;

    @NotEmpty
    @Email
    private String email;
    @NotNull
    @Past
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate birthDay;

    private UserRegion userRegion;
    @NotEmpty
    @Size(min = 8, max = 30)
    private String password;

    public enum UserRegion{
        US,
        EU,
        AFRICA,
        ASIA,
        AUSTRALIA,
        OTHERS
    }


}
