package com.example.jwallet.wallet.wallet.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConfigRepoManager {

	private static DataSource dataSource;

	private ConfigRepoManager() {

	}

	public static String getPropertyValue(final String keyName) {
		//		try {
		//			dataSource = (DataSource) new InitialContext().lookup("jdbc/jwallet");
		//			final PreparedStatement query;
		//			try (final Connection connection = dataSource.getConnection()) {
		//				query = connection.prepareStatement(
		//						"SELECT PROPERTY_VALUE FROM CUSTOM_CONFIGURATIONS WHERE PROPERTY_KEY = ?");
		//				query.setString(1, keyName);
		//				final ResultSet propertyValue = query.executeQuery();
		//				if (propertyValue.next()) {
		//					return propertyValue.getString(1);
		//				}
		//				propertyValue.close();
		//				query.close();
		//			}
		//		} catch (final Exception e) {
		//			e.printStackTrace();
		//		}
		return null;
	}

	public static Set<String> getPropertyNames() {
		final Set<String> propertyNames = new HashSet<>();

		//		try {
		//			final PreparedStatement query;
		//			dataSource = (DataSource) new InitialContext().lookup("jdbc/jwallet-derby");
		//
		//			try (final Connection connection = dataSource.getConnection()) {
		//				query = connection.prepareStatement(
		//						"SELECT PROPERTY_KEY FROM CUSTOM_CONFIGURATIONS");
		//			}
		//			final ResultSet propertyValue = query.executeQuery();
		//			if (propertyValue.next()) {
		//				propertyNames.add(propertyValue.getString(1));
		//			}
		//			propertyValue.close();
		//			query.close();
		//		} catch (final Exception e) {
		//			e.printStackTrace();
		//		}
		return propertyNames;
	}

}
