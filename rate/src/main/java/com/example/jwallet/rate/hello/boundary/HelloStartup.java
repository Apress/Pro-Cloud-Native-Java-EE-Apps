package com.example.jwallet.rate.hello.boundary;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CountDownLatch;

import lombok.extern.java.Log;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
//@Startup
@Log
public class HelloStartup implements HealthCheck {
	CountDownLatch countDownLatch = new CountDownLatch(5);

	@Override
	public HealthCheckResponse call() {
		countDownLatch.countDown();
		LocalDateTime upSince = LocalDateTime.now(ZoneOffset.UTC);
		return countDownLatch.getCount() == 0
				? HealthCheckResponse.named("rate-startup")
				.withData("up_since", upSince.toString())
				.up()
				.build()
				: HealthCheckResponse.down("rate-startup");
	}

}
