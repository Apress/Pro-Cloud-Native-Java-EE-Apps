package com.example.jwallet.rate.rate.control;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.example.jwallet.rate.rate.entity.ConvertCurrencyRequest;
import com.example.jwallet.rate.rate.entity.ConvertCurrencyResponse;
import com.example.jwallet.rate.rate.entity.ConvertCurrencyResponse.ConvertCurrencyResponseData;
import com.example.jwallet.rate.rate.providers.apilayer.boundary.ApiLayer;
import com.example.jwallet.rate.rate.providers.apilayer.entity.ApiLayerConvertRequest;
import com.example.jwallet.rate.rate.providers.apilayer.entity.ApiLayerConvertResponse;
import com.example.jwallet.rate.rate.providers.exchangerateapi.boundary.ExchangeRateApi;
import com.example.jwallet.rate.rate.providers.exchangerateapi.entity.ExchangeRateApiRequest;
import com.example.jwallet.rate.rate.providers.exchangerateapi.entity.ExchangeRateApiResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Provider;

@ApplicationScoped
public class RateService {

	@Inject
	@RestClient
	ApiLayer apiLayer;

	@Inject
	@RestClient
	ExchangeRateApi exchangeRateApi;

	@Inject
	Config config;

	@Inject
	@ConfigProperty(name = "convert_request")
	ConvertCurrencyRequest convertCurrencyRequest;
	@Inject
	@ConfigProperty(name = "convert_request")
	Optional<ConvertCurrencyRequest> convertRequest;

	@Inject
	@ConfigProperty(name = "convert_request")
	ConfigValue configValue;

	@Inject
	@ConfigProperty(name = "convert_request")
	Provider<ConvertCurrencyRequest> currencyRequestProvider;

	@Inject
	@ConfigProperty(name = "convert_request")
	Supplier<ConvertCurrencyRequest> convertCurrencyRequestSupplier;

	@Inject
	MetricRegistry registry;

	@Inject
	@Metric(name = "count_conversions_histogram",
			absolute = true,
			description = "A histogram for the count of conversions",
			tags = { "count=histogram", "metric=histogram" })
	Histogram histogram;

	@Gauge(name = "convert_currency_gauge_count", unit = MetricUnits.NONE, absolute = true)
	public int countConversions() {
		int conversionCount = ThreadLocalRandom.current().nextInt(250, 500 + 1);

		Metadata metadata = Metadata.builder()
				.withName("count_conversions_histogram")
				.withDescription("A histogram for the count of conversions")
				.withDisplayName("A histogram")
				.build();
		histogram.update(conversionCount);
		Histogram manualHistogram = registry.histogram(metadata);
		manualHistogram.update(conversionCount);

		return conversionCount;
	}

	@CircuitBreaker
	@Fallback(fallbackMethod = "convertCurrencyFallback")
	@Bulkhead
	public ConvertCurrencyResponse convertCurrency(final ConvertCurrencyRequest convertCurrencyRequest) {
		return convert(convertCurrencyRequest);
	}

	@Asynchronous
	@Retry
	@Bulkhead
	public CompletionStage<ConvertCurrencyResponse> convertCurrencyAsync(
			final ConvertCurrencyRequest convertCurrencyRequest) {
		return CompletableFuture.completedFuture(convert(convertCurrencyRequest));
	}

	public ConvertCurrencyResponse batchConvert() {
		return convertRequest.map(this::convert).orElseGet(ConvertCurrencyResponse::new);

	}

	public ConvertCurrencyResponse batchConvertWithConfig() {
		ConvertCurrencyRequest request = config.getValue("convert_request", ConvertCurrencyRequest.class);
		return convert(request);
	}

	public ConvertCurrencyResponse batchConvertWithSimpleProperty() {
		return convert(convertCurrencyRequest);
	}

	public ConvertCurrencyResponse batchConvertWithProvider() {
		return convert(currencyRequestProvider.get());
	}

	public ConvertCurrencyResponse batchConvertWithSupplier() {
		return convert(convertCurrencyRequestSupplier.get());
	}

	public ConvertCurrencyResponse convertCurrencyFallback(final ConvertCurrencyRequest convertCurrencyRequest) {
		ExchangeRateApiRequest exchangeRateApiRequest = new ExchangeRateApiRequest();
		exchangeRateApiRequest.setFrom(convertCurrencyRequest.getSourceCurrency());
		exchangeRateApiRequest.setTo(convertCurrencyRequest.getTargetCurrency());
		exchangeRateApiRequest.setAmount(convertCurrencyRequest.getAmount().toPlainString());

		ExchangeRateApiResponse exchangeRateApiResponse = exchangeRateApi.convertCurrency(exchangeRateApiRequest);

		ConvertCurrencyResponseData data = new ConvertCurrencyResponseData();
		data.setCurrency(convertCurrencyRequest.getTargetCurrency());
		data.setAmount(exchangeRateApiResponse.getResult());
		data.setSource(ExchangeRateApi.NAME);

		return ConvertCurrencyResponse.of(data);
	}

	private ConvertCurrencyResponse convert(final ConvertCurrencyRequest currencyRequest) {
		ApiLayerConvertRequest request = new ApiLayerConvertRequest();
		request.setFromCurrency(currencyRequest.getSourceCurrency());
		request.setToCurrency(currencyRequest.getTargetCurrency());
		request.setAmount(currencyRequest.getAmount().toPlainString());

		ApiLayerConvertResponse response = apiLayer.convertCurrency(request);

		ConvertCurrencyResponseData data = new ConvertCurrencyResponseData();
		data.setCurrency(currencyRequest.getTargetCurrency());
		data.setAmount(response.getResult());
		data.setSource(ApiLayer.NAME);

		return ConvertCurrencyResponse.of(data);
	}

}
