package com.example.jwallet.core.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@ApplicationScoped
public class AbstractEntityEntityListener {

	@Inject
	JsonWebToken jsonWebToken;
	@PrePersist
	void init(final Object entity) {
		final AbstractEntity abstractEntity = (AbstractEntity) entity;
		abstractEntity.setCreated(LocalDateTime.now(ZoneOffset.UTC));
		abstractEntity.setCreatedBy(jsonWebToken.getClaim(Claims.upn));
	}

	@PreUpdate
	void update(final Object entity) {
		final AbstractEntity abstractEntity = (AbstractEntity) entity;
		abstractEntity.setUpdated(LocalDateTime.now(ZoneOffset.UTC));
		abstractEntity.setEditedBy(jsonWebToken.getClaim(Claims.upn));
	}

}
