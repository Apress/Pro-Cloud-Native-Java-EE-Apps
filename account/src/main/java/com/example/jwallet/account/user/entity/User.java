package com.example.jwallet.account.user.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.example.jwallet.core.entity.AbstractEntity;
import com.example.jwallet.core.entity.Address;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
@Table(name = "WALLET_USER")
@Getter
@Setter
@NamedQuery(name = User.LOAD_USERS_BY_CONVERSIONS, query = "select u.userCredentials from User  u where u.totalConversions >= :totalConversions")
@NamedQuery(name = User.LOAD_ALL_USERS, query = "select u from User  u")
@NamedQuery(name = User.LOAD_USER_CREDENTIAL, query = "select u.userCredentials from User  u join u.userCredentials c ")
@NamedQuery(name = User.COUNT_USERS_BY_CONVERSIONS, query = "select COUNT(u) from User  u where u.totalConversions >= :totalConversions")
@NamedQuery(name = User.LOAD_USER_WITH_TRANSACTIONS, query = "select u from User  u join fetch  u.transactionHistory")
@NamedQuery(name = User.LOAD_USER_NAME_EMAIL,
		query = "select u.lastName, u.lastName, u.userCredentials.email from User  u")
public class User extends AbstractEntity {
	public static final String LOAD_ALL_USERS = "User.loadAllUsers";
	public static final String LOAD_USER_CREDENTIAL = "User.loadUserCredential";
	public static final String LOAD_USER_NAME_EMAIL = "User.loadUserNameAndEmail";
	public static final String LOAD_USER_WITH_TRANSACTIONS = "Usr.loadWithTransactions";

	public static final String COUNT_USERS_BY_CONVERSIONS = "User.countByConversion";
	public static final String LOAD_USERS_BY_CONVERSIONS = "User.loadUsersByConversion";
	@Column(nullable = false)
	@NotEmpty
	private String firstName;
	@Column(nullable = false)
	@NotEmpty
	private String lastName;

	@Column(precision = 19, scale = 2)
	private BigDecimal totalConversions;
	@NotNull
	@Past
	@JsonbDateFormat("dd-MM-yyyy")
	private LocalDate birthDay;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] picture;
	@Enumerated(EnumType.STRING)
	private UserType userType;
	@Enumerated(EnumType.STRING)
	private CreateUserRequest.UserRegion userRegion;
	@Embedded
//	@Valid
	private Address address;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//	@Valid
	private UserCredentials userCredentials;

	@OneToMany(mappedBy = "accountOwner", cascade = CascadeType.ALL)
	private List<TransactionHistory> transactionHistory = new ArrayList<>();

	private transient int age;

	@PostLoad
	private void computeAge() {
		age = (int) ChronoUnit.YEARS.between(birthDay, LocalDate.now(ZoneOffset.UTC));
	}

}
