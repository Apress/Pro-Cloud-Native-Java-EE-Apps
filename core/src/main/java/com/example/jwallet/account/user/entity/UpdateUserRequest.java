package com.example.jwallet.account.user.entity;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

import jakarta.json.bind.annotation.JsonbDateFormat;

@Getter
@Setter
public class UpdateUserRequest implements Serializable {
	private Long userId;
	private String firstName;

	private String lastName;

	@JsonbDateFormat("dd-MM-yyyy")
	private LocalDate birthDay;

	private CreateUserRequest.UserRegion userRegion;
}
