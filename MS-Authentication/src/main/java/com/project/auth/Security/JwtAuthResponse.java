package com.project.auth.Security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtAuthResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8251934278017076183L;
	
	private String jwttoken;
	private Long expires_in;
	private String message;

}
