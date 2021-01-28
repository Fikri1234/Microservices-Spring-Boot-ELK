package com.project.auth.ServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.auth.DTO.MUserDTO;
import com.project.auth.Entity.MUserEntity;
import com.project.auth.Service.MUserService;


@Service("customUserDetailService")
public class CustomUserDetailServiceImpl implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MUserService mUserService;
	
	@Autowired
	private PasswordEncoder userPasswordEncoder;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
		logger.info("[CustomUserDetailService] loadUserByUsername username: {}",username);
		Optional<MUserEntity> mUserEntity = mUserService.findByUsername(username);
        
        if (mUserEntity.isPresent()) {    	
        		
    		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(mUserEntity.get().getUserRole()));
    		logger.info("auth: {}",authorities.toString());
    		
    		MUserDTO dto = new MUserDTO();
    		BeanUtils.copyProperties(mUserEntity.get(), dto);
    		logger.info("got username: {} dto: {}",mUserEntity.get().getUsername(), dto.getUsername());
        	return new User (dto.getUsername(), dto.getPassword(), 
        			dto.isEnabled(), dto.isAccountNonExpired(), dto.isCredentialsNonExpired(), dto.isAccountNonLocked(), authorities);
		}else {
			logger.error("userId not found: ",username);
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
	}
	
	public boolean validatePassword(String username, String password) {
		Optional<MUserEntity> account = mUserService.findByUsername(username);
		if(account.isPresent()){
			// Verify the encoded password obtained from storage matches the submitted raw password 
			// after it too is encoded. Returns true if the passwords match, false if they do not. 
			// The stored password itself is never decoded.
			return userPasswordEncoder.matches(password, account.get().getPassword());
		}
		return false;
	}

}
