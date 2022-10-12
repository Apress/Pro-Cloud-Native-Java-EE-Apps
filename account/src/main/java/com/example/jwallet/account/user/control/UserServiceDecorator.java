package com.example.jwallet.account.user.control;

import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.jwallet.account.user.entity.CreateUserRequest;
import com.example.jwallet.account.user.entity.CreateUserRequest.UserRegion;
import com.example.jwallet.account.user.entity.User;
import com.example.jwallet.account.user.entity.UserResponse;
import com.example.jwallet.wallet.wallet.boundary.Database;
import com.example.jwallet.wallet.wallet.boundary.Database.DB;
import com.example.jwallet.wallet.wallet.boundary.MessageOption;
import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.persistence.EntityManager;

@Decorator
@Priority(Interceptor.Priority.APPLICATION)
public abstract class UserServiceDecorator implements UserFacade {

	//	@Inject
	//	@MessageOption(MessageOption.MessageType.SMS)
	//	Event<UserResponse> smsEvent;
	//
	//	@Inject
	//	@MessageOption(MessageOption.MessageType.SMS)
	//	Event<UserResponse> emailEvent;

	@Inject
	private Logger logger;

	@Inject
	Event<UserResponse> userResponseEvent;
	@Inject
	@Database(DB.POSTGRES)
	private EntityManager entityManager;

	@Inject
	@Delegate
	private UserService userService;

	@Override
	public UserResponse createUser(final CreateUserRequest createUserRequest) {
		final User user = UserService.fromUserWrapper(createUserRequest);
		if (createUserRequest.getUserRegion() == UserRegion.EU) {
			entityManager.persist(user);
			final UserResponse userResponse = userService.getUser(user.getId());

			//			userResponseEvent.fire(userResponse);
			userResponseEvent.select(getAnnotation(UserRegion.EU)).fireAsync(userResponse)
					.handle((payload, exception) -> {
						if (exception != null) {
							for (final Throwable suppressedException : exception.getSuppressed()) {
								logger.log(Level.SEVERE, "An exception occurred while sending message",
										suppressedException);
							}
						}

						return payload;
					}).thenAccept(payload -> logger.log(Level.INFO, () -> "Closing resource..."));

			return UserService.toUserResponse(user);
		}
		final UserResponse userResponse = userService.createUser(createUserRequest);
		userResponseEvent.select(getAnnotation(UserRegion.OTHERS)).fireAsync(userResponse)
				.handle((payload, exception) -> {
					if (exception != null) {
						for (final Throwable suppressedException : exception.getSuppressed()) {
							logger.log(Level.SEVERE, "An exception occurred while sending message",
									suppressedException);
						}
					}

					return payload;
				}).thenAccept(payload -> logger.log(Level.INFO, () -> "Closing resource..."));

		return userResponse;
	}

	private MessageOption getAnnotation(final UserRegion userRegion) {
		if (userRegion == UserRegion.EU) {
			return new MessageOption() {
				@Override
				public MessageType value() {
					return MessageType.EMAIL;
				}

				@Override
				public Class<? extends Annotation> annotationType() {
					return MessageOption.class;
				}
			};
		}
		return new MessageOption() {
			@Override
			public MessageType value() {
				return MessageType.SMS;
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				return MessageOption.class;
			}
		};
	}

}
