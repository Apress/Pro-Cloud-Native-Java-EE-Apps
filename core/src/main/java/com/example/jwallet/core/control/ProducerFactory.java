package com.example.jwallet.core.control;

import com.example.jwallet.wallet.wallet.boundary.Database;
import com.example.jwallet.wallet.wallet.boundary.Database.DB;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.logging.Logger;

public class ProducerFactory {

	//	@Produces
	//	@Database(DB.DERBY)
	//	@PersistenceContext(unitName = "jwallet-derby")
	//	EntityManager derby;

	@Produces
	@Database(DB.POSTGRES)
	@PersistenceContext(unitName = "jwallet")
	EntityManager postgres;

	//	@Produces
	//	@Database(DB.MS_SQL)
	//	@PersistenceContext(unitName = "jwallet-sql-server")
	//	EntityManager sqlServer;

	@Produces
	public Logger getLog(final InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
}
