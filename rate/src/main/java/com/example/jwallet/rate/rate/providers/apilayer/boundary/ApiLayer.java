package com.example.jwallet.rate.rate.providers.apilayer.boundary;

import com.example.jwallet.rate.rate.providers.apilayer.entity.ApiLayerConvertRequest;
import com.example.jwallet.rate.rate.providers.apilayer.entity.ApiLayerConvertResponse;
import jakarta.json.JsonObject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static com.example.jwallet.rate.rate.providers.apilayer.boundary.ApiLayer.BASE_URI;

@RegisterRestClient(baseUri = BASE_URI)
@ClientHeaderParam(name = "apikey", value = "YBZxFT9eDKbOVDDD7E9ql3G59ljHTw5o")
@Path("exchangerates_data")
public interface ApiLayer {

    String NAME = "ApiLayer";
    String BASE_URI = "https://api.apilayer.com";

    @GET
    @Path("convert")
    ApiLayerConvertResponse convertCurrency(@BeanParam ApiLayerConvertRequest apiLayerConvertRequest);

    @GET
    @Path("list")
    JsonObject getCurrencySymbols();
}
