package com.example.jwallet.rate.hello.boundary;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.extern.java.Log;

import org.eclipse.microprofile.health.*;

import com.example.jwallet.rate.rate.providers.apilayer.boundary.ApiLayer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;

@ApplicationScoped
@Liveness
@Readiness
@Startup
public class HelloHealth implements HealthCheck {
	LocalDateTime upSince = LocalDateTime.now(ZoneOffset.UTC);

	@Inject
	ApiLayer apiLayer;


	@Override
	public HealthCheckResponse call() {
		JsonObject currencySymbols = apiLayer.getCurrencySymbols();
		boolean success = currencySymbols.getBoolean("success", false);
		return success
				? HealthCheckResponse.named("rate-service")
				.withData("up_since", upSince.toString())
				.withData("rate_api_provider", "up")
				.up()
				.build()
				: HealthCheckResponse.down("rate-service");
	}
}
