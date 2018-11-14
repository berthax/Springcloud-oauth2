package com.troila.cloud.auth.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import com.troila.cloud.auth.utils.MyRedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final String DEMO_RESOURCE_ID = "order";
	String finalSecret = new BCryptPasswordEncoder().encode("123456");
	@Autowired
	private AuthenticationManager authenticationManager;
	
	 @Autowired
     RedisConnectionFactory redisConnectionFactory;
	
//	@Bean
//	public TokenStore tokenStore() {        
//		//使用内存的tokenStore,令牌（Access Token）会保存到内存  	      
//		return new RedisTokenStore(redisConnectionFactory);   
//		}

	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.tokenStore(new MyRedisTokenStore(redisConnectionFactory))
		.authenticationManager(authenticationManager);
		//配置TokenService参数
		DefaultTokenServices tokenService = new DefaultTokenServices();
		tokenService.setTokenStore(endpoints.getTokenStore());
		tokenService.setSupportRefreshToken(true);
		tokenService.setRefreshTokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(7));//7天
		tokenService.setClientDetailsService(endpoints.getClientDetailsService());
		tokenService.setTokenEnhancer(endpoints.getTokenEnhancer());
		tokenService.setAccessTokenValiditySeconds((int)TimeUnit.HOURS.toSeconds(1)); //1小时
		endpoints.tokenServices(tokenService);
	}


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("client_1")
        .resourceIds(DEMO_RESOURCE_ID,"file-api")
        .authorizedGrantTypes("client_credentials", "refresh_token")
        .scopes("select")
        .authorities("client")
        .secret(finalSecret)
        .and().withClient("client_2")
        .resourceIds(DEMO_RESOURCE_ID,"file-api")
        .authorizedGrantTypes("password", "refresh_token")
        .scopes("select")
        .authorities("client")
        .secret(finalSecret).autoApprove(true);
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//允许表单认证
		//这里增加拦截器到安全认证链中，实现自定义认证，包括图片验证，短信验证，微信小程序，第三方系统，CAS单点登录
		//addTokenEndpointAuthenticationFilter(IntegrationAuthenticationFilter())
		//IntegrationAuthenticationFilter 采用 @Component 注入
		security.allowFormAuthenticationForClients()
		.tokenKeyAccess("isAuthenticated()")
		.checkTokenAccess("permitAll()");
		
	}
	
}
