package com.example.jwallet.wallet.wallet.boundary;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.inject.Qualifier;

@Qualifier
@Retention(RUNTIME)
@Target({ FIELD, TYPE, METHOD, PARAMETER })
public @interface MessageOption {

	MessageType value();

	enum MessageType {
		SMS,
		EMAIL
	}
}
