package com.example.jwallet.wallet.wallet.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "CUSTOM_CONFIGURATIONS")
@Getter
@Setter
public class CustomConfigurations {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "PROPERTY_VALUE")
	private String propertyValue;
	@Column(name = "PROPERTY_KEY")
	private String propertyKey;
}
