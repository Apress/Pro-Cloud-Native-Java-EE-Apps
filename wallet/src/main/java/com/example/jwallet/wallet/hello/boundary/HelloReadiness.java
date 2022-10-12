package com.example.jwallet.wallet.hello.boundary;

import java.util.concurrent.CountDownLatch;

import org.eclipse.microprofile.health.HealthCheckResponse;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
//@Readiness
public class HelloReadiness {

	CountDownLatch countDownLatch = new CountDownLatch(2);


	public HealthCheckResponse call() {
		countDownLatch.countDown();
		return countDownLatch.getCount() == 0
				? HealthCheckResponse.up("wallet-readiness")
				: HealthCheckResponse.down("wallet-readiness");
	}
}
