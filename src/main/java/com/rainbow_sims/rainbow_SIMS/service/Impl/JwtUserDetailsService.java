package com.rainbow_sims.rainbow_SIMS.service.Impl;

import java.util.ArrayList;
import java.util.List;


import com.rainbow_sims.rainbow_SIMS.repository.UserRepository;
import com.rainbow_sims.rainbow_SIMS.request.ResetPasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rainbow_sims.rainbow_SIMS.dao.UserDao;
import com.rainbow_sims.rainbow_SIMS.model.User;


@Service
public class JwtUserDetailsService  implements UserDetailsService{
	
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	public int save(User user) {
		String role = user.getRole() != null ? user.getRole().toUpperCase() : "";

		if (!role.equals("ADMIN") && !role.equals("REF")) {
			throw new IllegalArgumentException("Invalid role. Only 'admin' and 'ref' roles are allowed.");
		}

		User newUser = new User(user.getUsername(), bcryptEncoder.encode(user.getPassword()), role);
		newUser.setAddress(user.getAddress());  // Set address
		newUser.setMobile(user.getMobile());    // Set mobile

		userRepository.save(newUser);
		return newUser.getId();
	}
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}


	public User getUserByUsername(String username) {
		return userDao.findUserByUserName(username);
	}
	public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
		String username = resetPasswordRequest.getUsername();
		String newPassword = resetPasswordRequest.getNewPassword();
		String confirmPassword = resetPasswordRequest.getConfirmPassword();

		// Step 1: Validate that the new password and confirm password match
		if (!newPassword.equals(confirmPassword)) {
			throw new IllegalArgumentException("Passwords do not match.");
		}

		// Step 2: Validate password complexity (optional)
		if (newPassword.length() < 8) {
			throw new IllegalArgumentException("Password must be at least 8 characters.");
		}

		// Step 3: Find the user by username
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			throw new IllegalArgumentException("User not found with username: " + username);
		}

		// Step 4: Update the user's password
		user.setPassword(bcryptEncoder.encode(newPassword));
		userRepository.save(user);

		return "Password updated successfully.";
	}

}
