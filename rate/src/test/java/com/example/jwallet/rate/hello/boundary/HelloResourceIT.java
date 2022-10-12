package com.example.jwallet.rate.hello.boundary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.jwallet.rate.AbstractRateIT;

class HelloResourceIT extends AbstractRateIT {


	@Test
	void hello() {
		String response = target.path("hello").request().get(String.class);
		assertEquals("Hello rate module", response);
	}

}