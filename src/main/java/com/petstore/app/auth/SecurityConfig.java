//package com.petstore.app.auth;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//	
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		http
//			.csrf(Customizer.withDefaults())
//			.authorizeHttpRequests(authorize -> authorize
//					.anyRequest().permitAll()
////					.anyRequest().authenticated()
//			);
////			.httpBasic(Customizer.withDefaults());
//		return http.build();
//	}
//	
//}
