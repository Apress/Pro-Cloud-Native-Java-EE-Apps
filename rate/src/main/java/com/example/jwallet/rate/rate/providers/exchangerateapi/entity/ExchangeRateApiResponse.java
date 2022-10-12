package com.example.jwallet.rate.rate.providers.exchangerateapi.entity;

import jakarta.json.bind.annotation.JsonbProperty;

import java.math.BigDecimal;

public class ExchangeRateApiResponse {

    @JsonbProperty("conversion_result")
    private BigDecimal result;

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }
}
