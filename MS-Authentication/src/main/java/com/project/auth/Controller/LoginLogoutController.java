package com.project.auth.Controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.auth.Entity.MUserEntity;
import com.project.auth.Security.JwtAuthRequest;
import com.project.auth.Security.JwtAuthResponse;
import com.project.auth.Security.JwtTokenUtil;
import com.project.auth.Service.MUserService;
import com.project.auth.ServiceImpl.CustomUserDetailServiceImpl;




@RestController
public class LoginLogoutController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private MUserService mUserService;

	@Autowired
	private CustomUserDetailServiceImpl userDetailsService;
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthRequest auth) throws Exception {
    	
    	Optional<MUserEntity> opt = mUserService.findByPhoneNumberOrEmail(auth.getPhoneNumber(), auth.getMail());

    	if (opt.isPresent()) {
    		
    		logger.info("ada: {}",opt.get().getUsername());
    		
    		try {
				authenticate(opt.get().getUsername(), auth.getPassword());
		
				final UserDetails userDetails = userDetailsService.loadUserByUsername(opt.get().getUsername());
		
				final String token = jwtTokenUtil.generateToken(userDetails);
		
				return ResponseEntity.ok(new JwtAuthResponse(token, Long.valueOf(jwtTokenUtil.getExpiredIn()), "Success"));
    		} catch (Exception e) {
				// TODO: handle exception
    			return ResponseEntity.ok(new JwtAuthResponse(null, null, e.getMessage()));
			}
    	} else {
    		return ResponseEntity.ok(new JwtAuthResponse(null, null, "User Not Found!"));
    	}
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	

}
