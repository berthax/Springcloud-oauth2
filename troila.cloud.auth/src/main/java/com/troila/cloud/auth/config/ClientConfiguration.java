package com.troila.cloud.auth.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import com.troila.cloud.auth.repository.ClientRepository;

@Configuration
public class ClientConfiguration {
	
	@Bean
	ClientDetailsService clientDetailService(ClientRepository clientRepository) {
		
		return clientId->clientRepository.findByClientId(clientId).map(client->{
			BaseClientDetails details = new BaseClientDetails(client.getClientId(),null,client.getScopes(),client.getAuthorizedGrantTypes(),client.getAuthorities());
			details.setClientSecret(client.getSecret());
			details.setRegisteredRedirectUri(Collections.singleton("www.baidu.com"));
			return details;
		}).orElseThrow(()->new ClientRegistrationException(String.format("no client %s registered", clientId)));
	}
}
