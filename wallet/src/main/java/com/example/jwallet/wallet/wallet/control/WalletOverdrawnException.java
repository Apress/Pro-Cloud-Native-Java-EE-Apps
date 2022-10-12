package com.example.jwallet.wallet.wallet.control;

import com.example.jwallet.core.entity.ErrorResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;

public class WalletOverdrawnException extends BadRequestException {

    @Override
    public Response getResponse() {
        ErrorResponse error = new ErrorResponse();
        error.getResponse().setResponseCode("10400");
        error.getResponse().setResponseMessage("Wallet Overdrawn");
        return Response.fromResponse(super.getResponse()).entity(error).build();
    }
}
