package com.example.jwallet.account.hello.boundary;

import java.util.concurrent.CountDownLatch;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
//@Readiness
public class HelloReadiness implements HealthCheck {

	CountDownLatch countDownLatch = new CountDownLatch(2);

	@Override
	public HealthCheckResponse call() {
		countDownLatch.countDown();
		return countDownLatch.getCount() == 0
				? HealthCheckResponse.up("account-readiness")
				: HealthCheckResponse.down("account-readiness");
	}
}
