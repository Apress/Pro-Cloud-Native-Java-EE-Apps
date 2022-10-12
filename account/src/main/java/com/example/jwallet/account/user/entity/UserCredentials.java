package com.example.jwallet.account.user.entity;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.example.jwallet.core.entity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Setter
@Getter
public class UserCredentials extends AbstractEntity {

	@Column(unique = true, length = 30, nullable = false)
	@NotEmpty
	@Email
	private String email;
	@Column(nullable = false)
	@NotEmpty
	@Size(min = 8, max = 30)
	private String password;
	private String salt;

	@OneToOne
	@JoinColumn(name = "wallet_user_id")
	private User user;

	@ElementCollection
	private Set<String> emailHistory;
}
