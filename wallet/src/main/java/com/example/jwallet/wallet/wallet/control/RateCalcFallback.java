package com.example.jwallet.wallet.wallet.control;

import java.math.BigDecimal;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

import com.example.jwallet.wallet.wallet.entity.TransactionRequest;

public class RateCalcFallback implements FallbackHandler<BigDecimal> {
	@Override
	public BigDecimal handle(final ExecutionContext context) {
		final TransactionRequest transactionRequest = (TransactionRequest) context.getParameters()[0];
		return transactionRequest.getAmount();
	}
}
