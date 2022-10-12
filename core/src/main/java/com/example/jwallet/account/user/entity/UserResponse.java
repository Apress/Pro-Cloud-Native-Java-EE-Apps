package com.example.jwallet.account.user.entity;

import lombok.Getter;
import lombok.Setter;

import com.example.jwallet.core.entity.Response;

public class UserResponse extends Response<UserResponse.UserResponseData> {

	public static UserResponse of(UserResponseData data) {
		UserResponse userResponse = new UserResponse();
		userResponse.setData(data);
		return userResponse;
	}

	@Getter
	@Setter
	public static class UserResponseData {

		private Long userId;
		private String firstName;
		private String lastName;
		private String username;

	}

}
