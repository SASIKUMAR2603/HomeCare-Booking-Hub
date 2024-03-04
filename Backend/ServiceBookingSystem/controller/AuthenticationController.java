package com.example.ServiceBookingSystem.controller;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ServiceBookingSystem.dto.AuthenticationRequest;
import com.example.ServiceBookingSystem.dto.SignupRequestDTO;
import com.example.ServiceBookingSystem.dto.UserDto;
import com.example.ServiceBookingSystem.entity.User;
import com.example.ServiceBookingSystem.repository.UserRepository;
import com.example.ServiceBookingSystem.service.jwt.UserDetailsServiceImpl;
import com.example.ServiceBookingSystem.services.authentication.AuthService;
import com.example.ServiceBookingSystem.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailServiceImpl;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String HEADER_PREFIX = "Authorization ";

	private static final String HEADER_STRING = null;
	
	
	
	@PostMapping("/client/sign-up")
	public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO){
		
		if(authService.presentByEmail(signupRequestDTO.getEmail())) {
			return new ResponseEntity<>("Client already exists with this Email", HttpStatus.NOT_ACCEPTABLE);
		}
		
		UserDto createdUserDto = authService.signupClient(signupRequestDTO);
		
		return new ResponseEntity<>(createdUserDto, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/company/sign-up")
	public ResponseEntity<?> signupCompany(@RequestBody SignupRequestDTO signupRequestDTO){
		
		if(authService.presentByEmail(signupRequestDTO.getEmail())) {
			return new ResponseEntity<>("Company already exists with this Email", HttpStatus.NOT_ACCEPTABLE);
		}
		
		UserDto createdUserDto = authService.signupCompany(signupRequestDTO);
		
		return new ResponseEntity<>(createdUserDto, HttpStatus.OK);
		
	}
	
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletResponse response, UserDetailsServiceImpl userDetailsService) 
	throws IOException, JSONException{
		
		
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
					( authenticationRequest.getUsername(),authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password",e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());
		
		response.getWriter().write(new JSONObject()
		.put("userId",user.getId())
		.put("role",user.getRole())
		.toString()
		);
		
		response.addHeader("Access-Control-Exposs-Headers","Authorization");
		response.addHeader("Access-Control-Allow-Headers","Authorization,"+ "X-PINGOTHER, Origin, "
				+ "X-Requested-With,Content-Type,Accept,X-Coustomer-header");
		
		response.addHeader(HEADER_STRING,  TOKEN_PREFIX +jwt);
	}


	public UserDetailsServiceImpl getUserDetailServiceImpl() {
		return userDetailServiceImpl;
	}


	public void setUserDetailServiceImpl(UserDetailsServiceImpl userDetailServiceImpl) {
		this.userDetailServiceImpl = userDetailServiceImpl;
	}
	
}
