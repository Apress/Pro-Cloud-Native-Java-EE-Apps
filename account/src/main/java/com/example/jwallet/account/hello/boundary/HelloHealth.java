package com.example.jwallet.account.hello.boundary;

import org.eclipse.microprofile.health.*;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Liveness
@Readiness
@Startup
public class HelloHealth implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse
				.builder()
				.name("account_single_health_check")
				.up()
				.build();
	}
}
