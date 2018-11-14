package com.troila.cloud.auth.component;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.troila.cloud.auth.model.AuUser;
import com.troila.cloud.auth.repository.AuUserRepository;

@Component
public class AuUserDetailsService implements UserDetailsService{

	@Autowired
	private AuUserRepository auUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		Optional<AuUser> auUserOpt = auUserRepository.findByName(name);
		AuUser auUser = auUserOpt.orElseThrow(()->new UsernameNotFoundException(String.format("用户 %s不存在！", name)));
		return new User(auUser.getName(), auUser.getPassword(), Collections.emptyList());
	}
	
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("123456"));
		String pwd1 = "$2a$10$L7NHXCnF1zvHFaeQgobRkuhbE0RGpuw7vAmPqnYAnNSjtEZ99P8P2";
		String pwd2 = "$2a$10$XTNGlaw3/TBaAgZWIfMr1uDnuzogUYSzcgX7wq2zlwuesyOYUSCY2";
		System.out.println(encoder.matches("123456", pwd1));
		System.out.println(encoder.matches("123456", pwd2));
	}

}
