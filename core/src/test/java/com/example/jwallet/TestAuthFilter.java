package com.example.jwallet;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;

import java.io.IOException;

public class TestAuthFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        clientRequestContext.getHeaders().add("Authorization", AbstractIT.AUTH_HEADER);
    }
}
