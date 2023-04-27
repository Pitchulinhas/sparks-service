package com.sparks.service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparks.service.entities.User;
import com.sparks.service.mappers.UserMapper;
import com.sparks.service.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;

	@Autowired
	private UserRepository userRepository;

	public User createUser(User createUserInput) {
		return this.userRepository.insert(createUserInput);
	}

	public List<User> findAllUsers() {
		return this.userRepository.findAll();
	}

	public User findUserById(String id) {
		return this.userRepository.findById(id).orElse(null);
	}

	public User updateUserById(User updateUserInput) {
		User userFound = this.userRepository.findById(updateUserInput.getId()).orElse(null);

		if (userFound != null) {
			this.mapper.updateUser(updateUserInput, userFound);

			return this.userRepository.save(userFound);
		}

		return userFound;
	}

	public User deleteUserById(String id) {
		User userFound = this.userRepository.findById(id).orElse(null);

		if (userFound != null) {
			this.userRepository.deleteById(id);
		}

		return userFound;
	}
}
