package com.project.auth.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ATI-User
 * Jul 29, 2020
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class BadCredentialExceptionHandler extends BadCredentialsException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadCredentialExceptionHandler(String exception) {
		super(exception);
	}
	
	public BadCredentialExceptionHandler(String exception, Throwable cause) {
		super(exception, cause);
	}

}
