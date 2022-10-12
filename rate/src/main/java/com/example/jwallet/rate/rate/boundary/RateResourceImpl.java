package com.example.jwallet.rate.rate.boundary;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.*;

import com.example.jwallet.rate.rate.control.RateService;
import com.example.jwallet.rate.rate.entity.ConvertCurrencyRequest;
import com.example.jwallet.rate.rate.entity.ConvertCurrencyResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("rates")
public class RateResourceImpl implements RateResource {

	@Inject
	RateService rateService;

	@Path("convert")
	@POST
	@Override
	@SimplyTimed(name = "convert_currency_simple_timer", absolute = true)
	@Counted(name = "convert_currency_counted",
			absolute = true,
			displayName = "Convert currency method",
			description = "Method to convert one currency to the other",
			tags = { "info=conversions", "type=meta" })
	@ConcurrentGauge(name = "convert_currency_concurrent_gauge",
			absolute = true,
			description = "Method to convert one currency to the other",
			tags = { "convert=custom" }
	)
	@Metered(name = "convert_currency_meter", absolute = true)
	@Timed(name = "convert_currency_timer", absolute = true)
	public ConvertCurrencyResponse convertCurrency(ConvertCurrencyRequest convertCurrencyRequest) {
		return rateService.convertCurrency(convertCurrencyRequest);
	}


}
