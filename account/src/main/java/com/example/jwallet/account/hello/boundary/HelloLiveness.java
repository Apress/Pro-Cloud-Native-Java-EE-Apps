package com.example.jwallet.account.hello.boundary;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
//@Liveness
public class HelloLiveness implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("account-liveness");
	}
}
