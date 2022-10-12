package com.example.jwallet.rate.rate.entity;

import com.example.jwallet.core.entity.Response;

import java.math.BigDecimal;

public class ConvertCurrencyResponse extends Response<ConvertCurrencyResponse.ConvertCurrencyResponseData> {

    public static ConvertCurrencyResponse of(ConvertCurrencyResponseData data) {
        ConvertCurrencyResponse convertCurrencyResponse = new ConvertCurrencyResponse();
        convertCurrencyResponse.setData(data);
        return convertCurrencyResponse;
    }

    public static class ConvertCurrencyResponseData {

        private String currency;
        private BigDecimal amount;
        private String source;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }

}
