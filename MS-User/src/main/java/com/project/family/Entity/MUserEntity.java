package com.project.family.Entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name="M_USER")
public class MUserEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "phone_number", unique = true, nullable = false)
	private String phoneNumber;
	
	@Column(name = "account_non_expired")
	private boolean accountNonExpired;
	
	@Column(name = "account_non_locked")
	private boolean accountNonLocked;
	
	@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name = "change_pwd_counter")
	private int changePwdCounter;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Column(name = "gender")
	private String gender;
	
	@Email
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "fail_counter")
	private int failCounter;
	
	@Column(name = "last_login")
	private Instant lastLogin;
	
	@Column(name = "reset_token")
	private String resetToken;
	
	@Column(name = "user_role")
	private String userRole;

}
