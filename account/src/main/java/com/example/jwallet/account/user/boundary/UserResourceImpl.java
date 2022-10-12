package com.example.jwallet.account.user.boundary;

import java.net.URI;
import java.util.List;

import com.example.jwallet.account.user.control.UserService;
import com.example.jwallet.account.user.entity.*;
import com.example.jwallet.core.entity.SearchResult;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
public class UserResourceImpl implements UserResource {

	@Inject
	UserService userService;

	@POST
	@Override
	public Response createUser(@NotNull @Valid CreateUserRequest createUserRequest, @Context UriInfo uriInfo) {
		UserResponse user = userService.createUser(createUserRequest);
		URI uri = uriInfo.getAbsolutePathBuilder().path(user.getData().getUserId().toString()).build();
		return Response.created(uri).entity(user).build();
	}

	@GET
	@Path("{userId}")
	@Override
	public UserResponse getUser(@PathParam("userId") Long userId) {
		return userService.getUser(userId);
	}

	@GET
	@Path("search")
	public SearchResult<UserResponse> searchUsers(@NotNull @BeanParam UserSearchRequest userSearchRequest) {
		return userService.searchUsers(userSearchRequest);
	}

	@PUT
	public UserResponse updateUser(@NotNull UpdateUserRequest updateUserRequest) {
		return userService.updateUser(updateUserRequest);
	}

	@DELETE
	public void deleteUser(@NotNull @Positive Long userId) {
		userService.removeUser(userId);

	}

}
