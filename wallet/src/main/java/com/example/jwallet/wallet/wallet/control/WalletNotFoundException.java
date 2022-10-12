package com.example.jwallet.wallet.wallet.control;

import com.example.jwallet.core.entity.ErrorResponse;
import com.example.jwallet.wallet.wallet.entity.BalanceResponse;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class WalletNotFoundException extends NotFoundException {

	@Override
	public Response getResponse() {
		ErrorResponse error = new ErrorResponse();
		error.getResponse().setResponseCode("10404");
		error.getResponse().setResponseMessage("Wallet Not Found");
		return Response.fromResponse(super.getResponse()).entity(error).build();
	}
}
