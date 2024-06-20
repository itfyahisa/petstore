package com.petstore.app.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {
	
	@Bean
	InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User.withUsername("user")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
