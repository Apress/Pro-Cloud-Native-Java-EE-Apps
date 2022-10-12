package com.example.jwallet.wallet.wallet.control;

import com.example.jwallet.core.control.Action;
import com.example.jwallet.core.control.Logged;
import com.example.jwallet.wallet.wallet.entity.BalanceResponse;
import com.example.jwallet.wallet.wallet.entity.BalanceResponse.BalanceResponseData;
import com.example.jwallet.wallet.wallet.entity.CreateWalletRequest;
import com.example.jwallet.wallet.wallet.entity.Transaction;
import com.example.jwallet.wallet.wallet.entity.TransactionRequest;
import com.example.jwallet.wallet.wallet.entity.TransactionResponse;
import com.example.jwallet.wallet.wallet.entity.TransactionResponse.TransactionResponseData;
import com.example.jwallet.wallet.wallet.entity.TransactionType;
import com.example.jwallet.wallet.wallet.entity.TransactionsResponse;
import com.example.jwallet.wallet.wallet.entity.Wallet;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Action
@NoArgsConstructor
public class WalletService {

	@Inject
	JsonWebToken jsonWebToken;

	@Inject
	@Claim(standard = Claims.sub)
	private String subject;

	@Inject
	@Claim(standard = Claims.raw_token)
	private ClaimValue<String> rawToken;

	WalletRepository walletRepository;
	TransactionRepository transactionRepository;
	RateService rateService;

	@Inject
	public WalletService(WalletRepository wallet, TransactionRepository txn, RateService rate) {
		this.walletRepository = wallet;
		this.transactionRepository = txn;
		this.rateService = rate;
	}

	public BalanceResponse getBalance(final long walletId) {
		Wallet wallet = walletRepository.findById(walletId);

		BalanceResponseData data = new BalanceResponseData();
		data.setWalletId(wallet.getId());
		data.setBalance(wallet.getBalance());
		data.setCurrency(wallet.getCurrency());

		return BalanceResponse.of(data);
	}

	public BalanceResponse createWallet(final CreateWalletRequest request) {

		Wallet wallet = new Wallet();
		wallet.setBalance(BigDecimal.ZERO);

		wallet.setCurrency(request.getCurrency());
		wallet.setAuthUserId(jsonWebToken.getSubject());
		wallet = walletRepository.save(wallet);

		return getBalance(wallet.getId());
	}

	public TransactionsResponse getTransactions(final long walletId) {
		Wallet wallet = walletRepository.findById(walletId);

		Set<TransactionResponseData> transactionResponseDataSet =
				wallet.getTransactions().stream().map(Transaction::toTransactionResponseData)
						.collect(Collectors.toSet());

		return TransactionsResponse.of(transactionResponseDataSet);
	}

	public TransactionResponse debit(final TransactionRequest transactionsRequest) {
		Wallet wallet = walletRepository.findById(transactionsRequest.getWalletId());

		Transaction transaction = new Transaction();
		transaction.setType(TransactionType.DEBIT);
		transaction.setWallet(wallet);
		transaction.setCurrency(transactionsRequest.getCurrency());
		transaction.setAmount(transactionsRequest.getAmount());
		transaction = transactionRepository.save(transaction);
		wallet.getTransactions().add(transaction);

		BigDecimal calculatedAmount = rateService.calculateAmount(transactionsRequest, wallet);
		wallet.debit(calculatedAmount);

		return TransactionResponse.of(transaction.toTransactionResponseData());
	}

	public TransactionResponse credit(final TransactionRequest transactionsRequest) {
		Wallet wallet = walletRepository.findById(transactionsRequest.getWalletId());

		Transaction transaction = new Transaction();
		transaction.setType(TransactionType.CREDIT);
		transaction.setWallet(wallet);
		transaction.setCurrency(transactionsRequest.getCurrency());
		transaction.setAmount(transactionsRequest.getAmount());
		transaction = transactionRepository.save(transaction);
		wallet.getTransactions().add(transaction);

		BigDecimal calculatedAmount = rateService.calculateAmount(transactionsRequest, wallet);
		wallet.credit(calculatedAmount);

		return TransactionResponse.of(transaction.toTransactionResponseData());
	}
}
