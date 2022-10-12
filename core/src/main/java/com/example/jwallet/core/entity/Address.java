package com.example.jwallet.core.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;

@Embeddable
@Getter
@Setter
public class Address implements Serializable {

	protected String apartment;
	@NotEmpty
	protected String street;
	@NotEmpty
	protected String city;
	@NotEmpty
	protected String state;
	@NotEmpty
	protected String zip;
	private PhoneType phoneType;
	protected String phone;

	public enum PhoneType {
		MOBILE,
		FIXED,
		VOIP
	}

}
