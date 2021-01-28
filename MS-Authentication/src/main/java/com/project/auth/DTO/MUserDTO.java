package com.project.auth.DTO;

import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.auth.Entity.MUserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MUserDTO {
	
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private int changePwdCounter;
	@JsonFormat(pattern="dd-MM-yyyy")
	private LocalDate birthDate;
	private String gender;
	private String email;
	private int failCounter;
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private Instant lastLogin;
	private String resetToken;
	private String userRole;
	
	public MUserDTO(MUserEntity entity) {
		super();
		this.id = entity.getId();
		this.username = entity.getUsername();
		this.password = entity.getPassword();
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.phoneNumber = entity.getPhoneNumber();
		this.accountNonExpired = entity.isAccountNonExpired();
		this.accountNonLocked = entity.isAccountNonLocked();
		this.credentialsNonExpired = entity.isCredentialsNonExpired();
		this.enabled = entity.isEnabled();
		this.changePwdCounter = entity.getChangePwdCounter();
		this.birthDate = entity.getBirthDate();
		this.gender = entity.getGender();
		this.email = entity.getEmail();
		this.failCounter = entity.getFailCounter();
		this.lastLogin = entity.getLastLogin();
		this.resetToken = entity.getResetToken();
		this.userRole = entity.getUserRole();
	}

}
