package com.example.jwallet.wallet.wallet.boundary;

import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.opentracing.Traced;

import com.example.jwallet.wallet.wallet.control.WalletService;
import com.example.jwallet.wallet.wallet.entity.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("wallets")
@RolesAllowed("teller")
public class WalletResourceImpl implements WalletResource {

	@Inject
	WalletService walletService;
	@Context
	SecurityContext securityContext;

	@POST
	@Traced(operationName = "create-wallet")
	@SimplyTimed(name = "create_wallet_timer")
	@Override
	public BalanceResponse createWallet(CreateWalletRequest createWalletRequest) {

		return walletService.createWallet(createWalletRequest);
	}

	@GET
	@Path("{walletId}/balance")
	@Traced(operationName = "get-wallet-balance")
	@SimplyTimed(name = "get_wallet_balance_timer")
	@Override
	public BalanceResponse getBalance(@PathParam("walletId") long walletId) {

		return walletService.getBalance(walletId);
	}

	@GET
	@Path("{walletId}/transactions")
	@Traced(operationName = "get-wallet-transactions")
	@SimplyTimed(name = "get_wallet_transactions_timer")
	@Override
	public TransactionsResponse getTransactions(@PathParam("walletId") long walletId) {
		return walletService.getTransactions(walletId);
	}

	@POST
	@Path("{walletId}/transactions/debit")
	@Traced(operationName = "debit-wallet")
	@SimplyTimed(name = "debit_wallet_timer")
	@Override
	public TransactionResponse debit(@PathParam("walletId") long walletId, TransactionRequest transactionsRequest) {
		return walletService.debit(transactionsRequest);
	}

	@POST
	@Path("{walletId}/transactions/credit")
	@Traced(operationName = "credit-wallet")
	@SimplyTimed(name = "credit_wallet_timer")
	@Override
	public TransactionResponse credit(@PathParam("walletId") long walletId, TransactionRequest transactionsRequest) {
		return walletService.credit(transactionsRequest);
	}
}
