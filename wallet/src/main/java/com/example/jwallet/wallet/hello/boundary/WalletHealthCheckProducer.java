package com.example.jwallet.wallet.hello.boundary;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CountDownLatch;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class WalletHealthCheckProducer {
	CountDownLatch readinessCountdown = new CountDownLatch(2);
	CountDownLatch startupCountdown = new CountDownLatch(5);

	@Inject
	@ConfigProperty(name = "free.memory.limit")
	Integer freeMemoryLimit;

	@Produces
	@Liveness
	HealthCheck livenessCheck() {
		String healthCheckName = "wallet-liveness-health-check";

		return () -> {
			long totalMemory = Runtime.getRuntime().totalMemory();
			System.out.println("Total memory --> " + totalMemory);
			long freeMemory = Runtime.getRuntime().freeMemory();
			System.out.println("Free memory --> " + freeMemory);

			boolean adequateMemory = (((float) freeMemory / totalMemory) * 100) > freeMemoryLimit;
			return adequateMemory ?
					HealthCheckResponse
							.builder()
							.name(healthCheckName)
							.up()
							.build() :
					HealthCheckResponse
							.named(healthCheckName)
							.down()
							.build();

		};
	}

	@Produces
	@Readiness
	HealthCheck readinessCheck() {
		return () -> {
			readinessCountdown.countDown();
			return readinessCountdown.getCount() == 0 ?
					HealthCheckResponse
							.up("wallet-readiness") :
					HealthCheckResponse
							.down("wallet-readiness");
		};
	}

	@Produces
	@Startup
	HealthCheck startupCheck() {
		return () -> {
			startupCountdown.countDown();
			return startupCountdown.getCount() == 0 ?
					HealthCheckResponse
							.up("wallet-started") :
					HealthCheckResponse
							.down("wallet-startup-failed");
		};
	}

}
