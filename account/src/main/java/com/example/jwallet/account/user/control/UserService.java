package com.example.jwallet.account.user.control;

import java.util.List;
import java.util.stream.Collectors;

import com.example.jwallet.account.user.entity.*;
import com.example.jwallet.account.user.entity.UserResponse.UserResponseData;
import com.example.jwallet.core.control.Action;
import com.example.jwallet.core.entity.SearchResult;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Action
public class UserService implements UserFacade {

	@Inject
	UserRepository userRepository;

	@Inject
	Event<UserResponse> userResponseEvent;

	@Override
	public UserResponse createUser(final CreateUserRequest createUserRequest) {
		User user = fromUserWrapper(createUserRequest);

		user = userRepository.save(user);
		UserResponse userResponse = getUser(user.getId());
		userResponseEvent.fire(userResponse);
		//		userResponseEvent.fireAsync(userResponse);
		return userResponse;

	}

	public UserResponse updateUser(final UpdateUserRequest updateUserRequest) {
		User user = userRepository.findById(updateUserRequest.getUserId());
		if (updateUserRequest.getFirstName() != null) {
			user.setFirstName(updateUserRequest.getFirstName());

		}
		if (updateUserRequest.getLastName() != null) {
			user.setLastName(updateUserRequest.getLastName());

		}

		if (updateUserRequest.getBirthDay() != null) {
			user.setBirthDay(updateUserRequest.getBirthDay());
		}

		if (updateUserRequest.getUserRegion() != null) {
			user.setUserRegion(updateUserRequest.getUserRegion());
		}
		userRepository.merge(user);
		return toUserResponse(user);
	}

	@Override
	public UserResponse getUser(final Long userId) {
		final User user = userRepository.findById(userId);
		return toUserResponse(user);
	}

	public static User fromUserWrapper(final CreateUserRequest request) {
		final User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUserRegion(request.getUserRegion());
		user.setBirthDay(request.getBirthDay());

		final UserCredentials credentials = new UserCredentials();
		credentials.setEmail(request.getEmail());
		//TODO --> Only for demo purposes
		//Chapter 11 should fix this.
		credentials.setPassword(request.getPassword());
		credentials.setUser(user);
		user.setUserCredentials(credentials);

		return user;
	}

	public SearchResult<UserResponse> searchUsers(final UserSearchRequest userSearchRequest) {
		return userRepository.searchUsers(userSearchRequest);
	}

	public List<UserResponse> getAllUsers() {
		return userRepository.loadAllUsers().stream().map(UserService::toUserResponse).collect(
				Collectors.toList());
	}

	public void removeUser(final Long id) {
		userRepository.remove(userRepository.findById(id));

	}

	public static UserResponse toUserResponse(final User user) {
		final UserResponseData data = new UserResponseData();
		data.setUserId(user.getId());
		data.setFirstName(user.getFirstName());
		data.setLastName(user.getLastName());

		return UserResponse.of(data);
	}

}
