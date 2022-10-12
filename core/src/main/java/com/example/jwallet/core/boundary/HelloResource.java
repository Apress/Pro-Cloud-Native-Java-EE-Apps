package com.example.jwallet.core.boundary;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Application;

@Path("hello")
public interface HelloResource {

	@GET
	String hello();

}