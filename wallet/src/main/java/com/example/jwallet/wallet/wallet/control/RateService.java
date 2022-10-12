package com.example.jwallet.wallet.wallet.control;

import com.example.jwallet.rate.rate.boundary.RateResource;
import com.example.jwallet.rate.rate.entity.ConvertCurrencyRequest;
import com.example.jwallet.rate.rate.entity.ConvertCurrencyResponse;
import com.example.jwallet.wallet.wallet.entity.TransactionRequest;
import com.example.jwallet.wallet.wallet.entity.Wallet;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;

@ApplicationScoped
public class RateService {

    @Inject
    @RestClient
    RateResource rateResource;

//    @Fallback(fallbackMethod = "calculateAmountFallback")
    @Fallback(RateCalcFallback.class)
    @Timeout
    @Retry
    public BigDecimal calculateAmount(final TransactionRequest transactionsRequest, final Wallet wallet) {
        ConvertCurrencyRequest convertCurrencyRequest = new ConvertCurrencyRequest();
        convertCurrencyRequest.setSourceCurrency(wallet.getCurrency());
        convertCurrencyRequest.setTargetCurrency(transactionsRequest.getCurrency());
        convertCurrencyRequest.setAmount(transactionsRequest.getAmount());

        ConvertCurrencyResponse convertCurrencyResponse = rateResource.convertCurrency(convertCurrencyRequest);
        return convertCurrencyResponse.getData().getAmount();
    }

    public BigDecimal calculateAmountFallback(final TransactionRequest transactionsRequest, final Wallet wallet) {
        return transactionsRequest.getAmount();
    }
}
