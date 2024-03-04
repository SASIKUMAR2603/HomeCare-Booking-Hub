package com.example.ServiceBookingSystem.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ServiceBookingSystem.service.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

   

    @Autowired
    private JwtRequestFilter requestFilter;
    
    
    @SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	return http.csrf().disable()
    			.authorizeHttpRequests()
    			.requestMatchers("api/auth/authenticate","api/auth/company/sign-up","api/auth/client/sign-up","api/auth/ads","api/auth/search/{service}").permitAll()
    			.and()
    			.authorizeHttpRequests().requestMatchers("/api/**")
    			.authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterAt(requestFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}