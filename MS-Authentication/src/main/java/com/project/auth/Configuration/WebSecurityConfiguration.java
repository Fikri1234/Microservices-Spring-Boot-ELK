package com.project.auth.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.project.auth.Security.JwtAuthenticationEntryPoint;
import com.project.auth.Security.LimitLoginAuthenticationProvider;
import com.project.auth.ServiceImpl.CustomUserDetailServiceImpl;
import com.project.commons.Security.JwtConfig;



@Configuration
@EnableWebSecurity
@Import(Encoder.class)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("customAuthenticationProvider")
	AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtConfig jwtConfig;
	
	@Autowired
	private CustomUserDetailServiceImpl customUserDetailServiceImpl;
	
    @Autowired
    private PasswordEncoder userPasswordEncoder;
     
    @Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Bean
	public TokenBasedRememberMeServices rememberMeServices() {
		return new TokenBasedRememberMeServices("remember-me-key", customUserDetailServiceImpl);
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		if (authenticationProvider instanceof LimitLoginAuthenticationProvider) {
			((LimitLoginAuthenticationProvider) authenticationProvider).setPasswordEncoder(userPasswordEncoder);
		}
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
//		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
				// dont authenticate this particular request
				.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/master-user/api/register").permitAll()
				.antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
				// all other requests need to be authenticated
						.anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
						exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
//	@Override
//	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		// We don't need CSRF for this example
//		httpSecurity.csrf().disable()
//				// dont authenticate this particular request
//				.authorizeRequests().antMatchers(jwtConfig.getUri(), "/login", "/mst/user/register").permitAll().
//				// all other requests need to be authenticated
//						anyRequest().authenticated().and().
//				// make sure we use stateless session; session won't be used to
//				// store user's state.
//						exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}
	
	@Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }
	
	@Bean
	public JwtConfig jwtConfig() {
        	return new JwtConfig();
	}

	/**
     * This is essential to make sure that the Spring Security session registry 
     * is notified when the session is destroyed.
     * @return
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher1() {
      return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
}
