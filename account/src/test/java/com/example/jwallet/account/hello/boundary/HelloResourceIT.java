package com.example.jwallet.account.hello.boundary;

import com.example.jwallet.account.AbstractAccountIT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloResourceIT extends AbstractAccountIT {


    @Test
    void hello() {
        String response = target.path("hello").request().get(String.class);
        assertEquals("Hello account module", response);
    }

}