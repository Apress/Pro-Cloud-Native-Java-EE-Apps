package com.example.jwallet.rate.hello.boundary;

import java.util.concurrent.CountDownLatch;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
//@Readiness
public class HelloReadiness implements HealthCheck {

	CountDownLatch countDownLatch = new CountDownLatch(2);

	@Override
	public HealthCheckResponse call() {
		countDownLatch.countDown();
		return countDownLatch.getCount() == 0
				? HealthCheckResponse.up("rate-readiness")
				: HealthCheckResponse.down("rate-readiness");
	}
}
