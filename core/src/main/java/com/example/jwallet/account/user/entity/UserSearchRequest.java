package com.example.jwallet.account.user.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

@Getter
@Setter
public class UserSearchRequest implements Serializable {

	@QueryParam("firstName")
	private String firstName;

	@QueryParam("lastName")
	private String lastName;
	@QueryParam("totalConversions")
	private BigDecimal totalConversions;
	@QueryParam("conversionFilter")
	private ConversionFilter conversionFilter = ConversionFilter.EQ;
	@QueryParam("userType")
	private UserType userType;

	@QueryParam("limit")
	@DefaultValue("150")
	@Positive
	private Integer limit;

	@QueryParam("offset")
	@DefaultValue("0")
	private Integer offset;

	public enum ConversionFilter {
		GT,
		GTE,
		LT,
		LTE,
		EQ,
		NEQ
	}

}
