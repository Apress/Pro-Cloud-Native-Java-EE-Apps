package com.example.jwallet.wallet.wallet.control;

import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

public class DatabaseConfigSource implements ConfigSource {
	@Override
	public Set<String> getPropertyNames() {
		return ConfigRepoManager.getPropertyNames();
	}

	@Override
	public String getValue(final String key) {
		return ConfigRepoManager.getPropertyValue(key);
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public int getOrdinal() {
		return 450;
	}
}
