package com.project.commons.Security;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class JwtConfig {
	
	@Value("${security.jwt.uri:/auth/**}")
    private String uri;

	@Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{10*60}}")
    private int expiration;

    @Value("${security.jwt.secret:Jwt_for_Secret_Key}")
    private String secret;

}
