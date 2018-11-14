package com.troila.cloud.resource.security.event;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AuthEventPublisher implements AuthenticationEventPublisher{
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		System.out.println("认证通过");
		System.out.println(authentication.getName());
		System.out.println(authentication.getDetails());
		System.out.println(authentication.getAuthorities());
		System.out.println(authentication.getPrincipal());
		System.out.println(authentication.getCredentials());
		System.out.println(((OAuth2AuthenticationDetails)authentication.getDetails()).getTokenValue());
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		// TODO Auto-generated method stub
		
	}

}
