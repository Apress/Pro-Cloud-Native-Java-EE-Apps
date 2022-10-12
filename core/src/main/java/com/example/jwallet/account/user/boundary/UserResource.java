package com.example.jwallet.account.user.boundary;

import com.example.jwallet.account.user.entity.CreateUserRequest;
import com.example.jwallet.account.user.entity.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static com.example.jwallet.account.user.boundary.UserResource.BASE_URI;

@RegisterRestClient(baseUri = BASE_URI)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
public interface UserResource {

	String BASE_URI = "http://account:3003/account/api";

	@POST
	Response createUser(@NotNull @Valid CreateUserRequest createUserRequest, @Context UriInfo uriInfo);

	@GET
	@Path("{userId}")
	UserResponse getUser(@PathParam("userId") Long userId);

}
