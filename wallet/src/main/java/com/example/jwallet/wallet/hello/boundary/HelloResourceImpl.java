package com.example.jwallet.wallet.hello.boundary;

import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import com.example.jwallet.core.boundary.HelloResource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("hello")
public class HelloResourceImpl implements HelloResource {


    @GET
    @SimplyTimed
    @Override
    public String hello() {
        return "Hello wallet module";
    }

}