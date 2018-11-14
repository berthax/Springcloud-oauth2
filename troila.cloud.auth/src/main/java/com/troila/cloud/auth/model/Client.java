package com.troila.cloud.auth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

@Entity
public class Client {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String clientId;
	
	private String secret;

	@Transient
	private String scopes = StringUtils.arrayToCommaDelimitedString(new String[] {"openid"});
	
	@Transient
	private String authorities = StringUtils.arrayToCommaDelimitedString(new String[] {"ROLE_USER","ROLE_ADMIN"});
	
	@Transient
	private String authorizedGrantTypes = StringUtils.arrayToCommaDelimitedString(new String[] {"authorization_code","refresh_token","password"});
	
	@Transient
	private String autoApproveScopes = StringUtils.arrayToCommaDelimitedString(new String[] {".*"});
	
	public Client(String clientId, String secret) {
		super();
		this.clientId = clientId;
		this.secret = secret;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getScopes() {
		return scopes;
	}

	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public String getAutoApproveScopes() {
		return autoApproveScopes;
	}

	public String getAuthorities() {
		return authorities;
	}
}
