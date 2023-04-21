package com.sparks.service.consumers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sparks.service.entities.User;
import com.sparks.service.mappers.UserMapper;
import com.sparks.service.repositories.UserRepository;
import com.sparks.utils.StringUtils;

@Component
public class UserConsumer {
	private Gson gson;
	@Autowired
	private UserMapper mapper;
	@Autowired
	private UserRepository repository;

	public UserConsumer() {
		this.gson = new Gson();
	}

	@KafkaListener(id = "${spring.kafka.topics.user.create}", topics = "${spring.kafka.topics.user.create}")
	@SendTo
	public String create(String userAsString) {
		userAsString = StringUtils.trimText(userAsString, "\"");

		userAsString = StringUtils.removeBackSlash(userAsString);

		User user = new Gson().fromJson(userAsString, User.class);

		User userCreated = this.repository.insert(user);

		return gson.toJson(userCreated);
	}

	@KafkaListener(id = "${spring.kafka.topics.user.find-all}", topics = "${spring.kafka.topics.user.find-all}")
	@SendTo
	public String findAll(String in) {
		List<User> users = this.repository.findAll();

		return gson.toJson(users);
	}

	@KafkaListener(id = "${spring.kafka.topics.user.find-by-id}", topics = "${spring.kafka.topics.user.find-by-id}")
	@SendTo
	public String findById(String id) {
		id = StringUtils.trimText(id, "\"");

		User userFound = this.repository.findById(id).orElse(null);

		return gson.toJson(userFound);
	}

	@KafkaListener(id = "${spring.kafka.topics.user.update-by-id}", topics = "${spring.kafka.topics.user.update-by-id}")
	@SendTo
	public String updateById(String userAsString) {
		userAsString = StringUtils.trimText(userAsString, "\"");

		userAsString = StringUtils.removeBackSlash(userAsString);

		User newUser = new Gson().fromJson(userAsString, User.class);
		User user = this.repository.findById(newUser.getId()).orElse(null);

		User userUpdated = null;
		
		if (user != null) {
			this.mapper.updateUser(newUser, user);

			userUpdated = this.repository.save(user);
		}

		return gson.toJson(userUpdated);
	}
	
	@KafkaListener(id = "${spring.kafka.topics.user.delete-by-id}", topics = "${spring.kafka.topics.user.delete-by-id}")
	@SendTo
	public String deleteById(String id) {
		id = StringUtils.trimText(id, "\"");

		User userFound = this.repository.findById(id).orElse(null);
		
		if (userFound != null) {
			this.repository.deleteById(id);
		}

		return gson.toJson(userFound);
	}
}
