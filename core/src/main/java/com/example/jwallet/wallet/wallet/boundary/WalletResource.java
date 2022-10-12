package com.example.jwallet.wallet.wallet.boundary;

import com.example.jwallet.wallet.wallet.entity.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static com.example.jwallet.wallet.wallet.boundary.WalletResource.BASE_URI;

@RegisterRestClient(baseUri = BASE_URI)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("wallets")
@RolesAllowed("teller")
public interface WalletResource {

    String BASE_URI = "http://wallet:3001/wallet/api";

    @POST
    BalanceResponse createWallet(@Valid CreateWalletRequest createWalletRequest);

    @GET
    @Path("{walletId}/balance")
    BalanceResponse getBalance(@PathParam("walletId") long walletId);

    @GET
    @Path("{walletId}/transactions")
    TransactionsResponse getTransactions(@PathParam("walletId") long walletId);

    @POST
    @Path("{walletId}/transactions/debit")
    TransactionResponse debit(@PathParam("walletId") long walletId, @Valid TransactionRequest transactionsRequest);

    @POST
    @Path("{walletId}/transactions/credit")
    TransactionResponse credit(@PathParam("walletId") long walletId, @Valid TransactionRequest transactionsRequest);
}
