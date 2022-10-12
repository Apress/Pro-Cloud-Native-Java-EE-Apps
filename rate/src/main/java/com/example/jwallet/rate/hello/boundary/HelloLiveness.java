package com.example.jwallet.rate.hello.boundary;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
//@Liveness
public class HelloLiveness implements HealthCheck {

	LocalDateTime init = LocalDateTime.now(ZoneOffset.UTC);

	@Override
	public HealthCheckResponse call() {
		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
		Duration upDuration = Duration.between(init, now);

		return HealthCheckResponse.builder()
				.up()
				.name("rate_service_live")
				.withData("up_since", upDuration.toMinutes())
				.build();
	}
}
