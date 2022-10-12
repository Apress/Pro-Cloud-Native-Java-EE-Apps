package com.example.jwallet.wallet.hello.boundary;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.jwallet.wallet.AbstractWalletIT;

class HelloResourceIT extends AbstractWalletIT {


	@Test
	void hello() {
		String response = target.path("hello").request().get(String.class);
		assertEquals("Hello wallet module", response);
	}

}