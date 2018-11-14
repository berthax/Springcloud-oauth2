package com.troila.cloud.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	String finalPassword = new BCryptPasswordEncoder().encode("123456");
	
	/*@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("user_1").password(finalPassword).authorities("USER").build());
		manager.createUser(User.withUsername("user_2").password(finalPassword).authorities("USER").build());
		return manager;
	}*/

	@Autowired
	private UserDetailsService userDetailsService;
	
  	@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().anyRequest().and().authorizeRequests().antMatchers("/oauth/*").permitAll();
	}

}
