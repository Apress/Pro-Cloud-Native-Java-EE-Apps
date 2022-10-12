package com.example.jwallet.rate.rate.providers.exchangerateapi.entity;

import jakarta.ws.rs.PathParam;

public class ExchangeRateApiRequest {

    @PathParam("apiKey")
    private final String apiKey = "b3c64438a499c390cab54648";
    @PathParam("from")
    private String from;
    @PathParam("to")
    private String to;
    @PathParam("amount")
    private String amount;

    public String getApiKey() {
        return apiKey;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
