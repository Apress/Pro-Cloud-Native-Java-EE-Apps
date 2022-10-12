package com.example.jwallet.rate.rate.boundary;

import static com.example.jwallet.rate.rate.boundary.RateResource.BASE_URI;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.example.jwallet.rate.rate.entity.ConvertCurrencyRequest;
import com.example.jwallet.rate.rate.entity.ConvertCurrencyResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(baseUri = BASE_URI)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("rates")
public interface RateResource {

    String BASE_URI = "http://rate:3002/rate/api";

    @Path("convert")
    @POST
    ConvertCurrencyResponse convertCurrency(@Valid ConvertCurrencyRequest convertCurrencyRequest);
}
