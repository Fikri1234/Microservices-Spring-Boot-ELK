package com.project.auth.Security;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.project.auth.Entity.MUserEntity;
import com.project.auth.Exception.BadCredentialExceptionHandler;
import com.project.auth.Exception.LockedExceptionHandler;
import com.project.auth.Service.MUserService;



@Component("customAuthenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(LimitLoginAuthenticationProvider.class);
	
	@Autowired
	private MUserService mUserService;
	
	@Autowired
	@Qualifier("customUserDetailService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) 
          throws AuthenticationException {

		try {
			Authentication auth = super.authenticate(authentication);
			logger.info("masuk limit: {}",authentication.getName());
			Optional<MUserEntity> MUserEntity = mUserService.findByUsername(authentication.getName());
			logger.info("user: {}",MUserEntity.get().getUsername());	
			
//			Long countUser = activeSessionService.countUsername(authentication.getName());
//			logger.error("user login {} count: {}", authentication.getName(), countUser);
//			//user already login
//			if(countUser > 0){
//				logger.error("user already login {} count: {}", authentication.getName(), countUser);
//				throw new AuthenticationExceptionHandler( authentication.getName() +" already login");
//			}
//			
//			//if reach here, means login success, else an exception will be thrown
//			//reset the user_attempts
//			
//			MUserEntity.get().setFailCounter(0);			/*reset fail counter*/
//			MUserEntity.get().setAccountNonLocked(true);	/*account non locked = true*/
//			MUserEntity.get().setLastLogin(Instant.now());
//			mUserService.updateMstUser(MUserEntity.get());
				
			return auth;
		} catch (BadCredentialsException e) {
			logger.error("user/password wrong", e);
			//invalid login, update to user_attempts
//			customUserDetailServiceImpl.increaseLoginFailCounter(authentication.getName());
			//throw e;
			throw new BadCredentialExceptionHandler("user/password wrong", e.getCause());
		} catch (LockedException e) {
			//this user is locked!
			logger.error("user "+authentication.getName()+" is locked", e);
			throw new LockedExceptionHandler("User has been locked! Please call Administrator.", e.getCause());
		} catch (AccountExpiredException ex) {
            // this user is expired
            throw ex;
        } 
		
	}
}
