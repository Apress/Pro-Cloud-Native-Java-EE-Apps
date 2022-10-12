package com.example.jwallet.account.user.control;

import com.example.jwallet.core.entity.ErrorResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

public class UserNotFoundException extends NotFoundException {

    @Override
    public Response getResponse() {
        ErrorResponse error = new ErrorResponse();
        error.getResponse().setResponseCode("11404");
        error.getResponse().setResponseMessage("User Not Found");
        return Response.fromResponse(super.getResponse()).entity(error).build();
    }
}
