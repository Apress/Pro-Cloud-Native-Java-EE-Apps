package com.example.jwallet.rate.rate.providers.exchangerateapi.boundary;

import com.example.jwallet.rate.rate.providers.exchangerateapi.entity.ExchangeRateApiRequest;
import com.example.jwallet.rate.rate.providers.exchangerateapi.entity.ExchangeRateApiResponse;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static com.example.jwallet.rate.rate.providers.exchangerateapi.boundary.ExchangeRateApi.BASE_URI;

@RegisterRestClient(baseUri = BASE_URI)
@Path("v6")
public interface ExchangeRateApi {

    String NAME = "ExchangeRate-API";
    String BASE_URI = "https://v6.exchangerate-api.com";

    @GET
    @Path("{apiKey}/pair/{from}/{to}/{amount}")
    ExchangeRateApiResponse convertCurrency(@BeanParam ExchangeRateApiRequest exchangeRateApiRequest);

}
