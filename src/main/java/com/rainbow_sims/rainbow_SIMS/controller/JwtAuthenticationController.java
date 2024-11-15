package com.rainbow_sims.rainbow_SIMS.controller;

import com.rainbow_sims.rainbow_SIMS.request.ResetPasswordRequest;
import com.rainbow_sims.rainbow_SIMS.service.Impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.rainbow_sims.rainbow_SIMS.config.JwtTokenUtil;
import com.rainbow_sims.rainbow_SIMS.request.JwtRequest;
import com.rainbow_sims.rainbow_SIMS.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
public class JwtAuthenticationController {


	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		// Get the role from the UserDetails
		String role = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.findFirst().orElse("");

		// Check if role is empty or invalid
		if (role.isEmpty()) {
			throw new Exception("Role is missing or invalid.");
		}

		// Generate token based on the user's role
		final String token = jwtTokenUtil.generateToken(userDetails);

		// Prepare the response with role and token
		Map<String, Object> response = new HashMap<>();
		response.put("role", role.replace("ROLE_", "").toLowerCase());  // Remove "ROLE_" prefix
		response.put("token", token);

		return ResponseEntity.ok(response);
	}



	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
		// Ensure fields like address and mobile are set during registration
		userDetailsService.save(user);
		User savedUser = userDetailsService.getUserByUsername(user.getUsername());
		Map<String, Object> response = new HashMap<>();
		response.put("message", "User successfully added");
		response.put("user", savedUser);
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers() {
		List<User> users = userDetailsService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		try {
			String message = userDetailsService.resetPassword(resetPasswordRequest);
			return ResponseEntity.ok(message);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private void authenticate(String username, String password) throws Exception {
		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {

			System.out.print("user disabled");
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			System.out.print("invalide creditinalll");
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
