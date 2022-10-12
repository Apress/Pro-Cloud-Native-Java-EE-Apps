package com.example.jwallet.rate.rate.boundary;

import com.example.jwallet.rate.AbstractRateIT;
import com.example.jwallet.rate.rate.entity.ConvertCurrencyRequest;
import com.example.jwallet.rate.rate.entity.ConvertCurrencyResponse;
import jakarta.ws.rs.client.Entity;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RateResourceIT extends AbstractRateIT {

    @RepeatedTest(35)
    void convertCurrency() {
        ConvertCurrencyRequest convertCurrencyRequest = new ConvertCurrencyRequest();
        convertCurrencyRequest.setSourceCurrency("USD");
        convertCurrencyRequest.setTargetCurrency("USD");
        convertCurrencyRequest.setAmount(BigDecimal.TEN);

        ConvertCurrencyResponse convertCurrencyResponse = target.path("rates/convert").request()
                .post(Entity.json(convertCurrencyRequest), ConvertCurrencyResponse.class);

        assertEquals("0", convertCurrencyResponse.getResponse().getResponseCode());
        assertEquals("OK", convertCurrencyResponse.getResponse().getResponseMessage());

        assertEquals(convertCurrencyRequest.getTargetCurrency(), convertCurrencyResponse.getData().getCurrency());
        assertEquals(BigDecimal.TEN, convertCurrencyResponse.getData().getAmount());
    }

}
