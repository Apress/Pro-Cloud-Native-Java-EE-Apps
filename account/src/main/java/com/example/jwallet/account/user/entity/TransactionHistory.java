package com.example.jwallet.account.user.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import com.example.jwallet.core.entity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;

@Entity
@Getter
@Setter
public class TransactionHistory extends AbstractEntity {
	private String sourceCurrency;
	private String targetCurrency;
	private String amount;
	private LocalDateTime transactionDate;

	@ManyToOne
	@MapKeyColumn
	private User accountOwner;

}
