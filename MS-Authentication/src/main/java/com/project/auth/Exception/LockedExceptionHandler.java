package com.project.auth.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ATI-User
 * Jul 29, 2020
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class LockedExceptionHandler extends LockedException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LockedExceptionHandler(String exception) {
		super(exception);
	}
	
	public LockedExceptionHandler(String exception, Throwable cause) {
		super(exception, cause);
	}

}
