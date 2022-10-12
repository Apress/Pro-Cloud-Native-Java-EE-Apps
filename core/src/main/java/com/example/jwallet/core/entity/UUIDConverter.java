package com.example.jwallet.core.entity;

import java.util.UUID;

import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UUIDConverter implements JsonbAdapter<UUID, String>, AttributeConverter<UUID, String> {

	@Override
	public String adaptToJson(UUID uuid) throws Exception {
		return uuid.toString();
	}

	@Override
	public UUID adaptFromJson(String s) throws Exception {
		return UUID.fromString(s);
	}

	@Override
	public String convertToDatabaseColumn(UUID uuid) {
		return uuid.toString();
	}

	@Override
	public UUID convertToEntityAttribute(String s) {
		return UUID.fromString(s);
	}
}
