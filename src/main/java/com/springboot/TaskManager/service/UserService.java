package com.springboot.TaskManager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.TaskManager.enums.Role;
import com.springboot.TaskManager.exception.InvalidUsernameException;
import com.springboot.TaskManager.model.User;
import com.springboot.TaskManager.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passEncoder;

	public User signUp(User user) throws InvalidUsernameException {
		// check for username duplicacy
		Optional<User> optional = userRepository.findByUsername(user.getUsername());
		if (optional.isPresent()) {
			throw new InvalidUsernameException("Username already in use");
		}

		// encrypt the password
		String encryptedPass = passEncoder.encode(user.getPassword());
		user.setPassword(encryptedPass);

		user.setRole(Role.CUSTOMER);
		return userRepository.save(user);
	}
}
