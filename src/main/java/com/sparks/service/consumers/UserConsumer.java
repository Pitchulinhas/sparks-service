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
import com.sparks.service.responses.ServiceResponse;
import com.sparks.utils.StringUtils;

@Component
public class UserConsumer {
	@Autowired
	private UserMapper mapper;

	@Autowired
	private UserRepository repository;

	private Gson gson;

	public UserConsumer() {
		this.gson = new Gson();
	}

	@KafkaListener(id = "${spring.kafka.topics.user.create}", topics = "${spring.kafka.topics.user.create}")
	@SendTo
	public String create(String createUserInputAsJson) {
		ServiceResponse<User> response = new ServiceResponse<>();

		try {
			createUserInputAsJson = StringUtils.trimText(createUserInputAsJson, "\"");

			createUserInputAsJson = StringUtils.removeBackSlash(createUserInputAsJson);

			User createUserInput = new Gson().fromJson(createUserInputAsJson, User.class);

			User userCreated = this.repository.insert(createUserInput);

			response.setData(userCreated);
		} catch (Exception ex) {
			if (ex.getMessage().contains("email dup key")) {
				response.setErrorMessage("Já existe um usuário com esse e-mail");
			} else {
				response.setErrorMessage(ex.getMessage());
			}
		}

		return gson.toJson(response);
	}

	@KafkaListener(id = "${spring.kafka.topics.user.find-all}", topics = "${spring.kafka.topics.user.find-all}")
	@SendTo
	public String findAll(String in) {
		ServiceResponse<List<User>> response = new ServiceResponse<>();

		try {
			List<User> usersFound = this.repository.findAll();

			response.setData(usersFound);
		} catch (Exception ex) {
			response.setErrorMessage(ex.getMessage());
		}

		return gson.toJson(response);
	}

	@KafkaListener(id = "${spring.kafka.topics.user.find-by-id}", topics = "${spring.kafka.topics.user.find-by-id}")
	@SendTo
	public String findById(String id) {
		ServiceResponse<User> response = new ServiceResponse<>();

		try {
			id = StringUtils.trimText(id, "\"");

			User userFound = this.repository.findById(id).orElse(null);

			response.setData(userFound);
		} catch (Exception ex) {
			response.setErrorMessage(ex.getMessage());
		}

		return gson.toJson(response);
	}

	@KafkaListener(id = "${spring.kafka.topics.user.update-by-id}", topics = "${spring.kafka.topics.user.update-by-id}")
	@SendTo
	public String updateById(String updateUserInputAsJson) {
		ServiceResponse<User> response = new ServiceResponse<>();

		try {
			updateUserInputAsJson = StringUtils.trimText(updateUserInputAsJson, "\"");

			updateUserInputAsJson = StringUtils.removeBackSlash(updateUserInputAsJson);

			User updateUserInput = new Gson().fromJson(updateUserInputAsJson, User.class);

			User userFound = this.repository.findById(updateUserInput.getId()).orElse(null);

			if (userFound != null) {
				this.mapper.updateUser(updateUserInput, userFound);

				User userUpdated = this.repository.save(userFound);

				response.setData(userUpdated);
			}
		} catch (Exception ex) {
			if (ex.getMessage().contains("email dup key")) {
				response.setErrorMessage("Já existe um usuário com esse e-mail");
			} else {
				response.setErrorMessage(ex.getMessage());
			}
		}

		return gson.toJson(response);
	}

	@KafkaListener(id = "${spring.kafka.topics.user.delete-by-id}", topics = "${spring.kafka.topics.user.delete-by-id}")
	@SendTo
	public String deleteById(String id) {
		ServiceResponse<User> response = new ServiceResponse<>();

		try {
			id = StringUtils.trimText(id, "\"");

			User userFound = this.repository.findById(id).orElse(null);

			if (userFound != null) {
				this.repository.deleteById(id);

				response.setData(userFound);
			}
		} catch (Exception ex) {
			response.setErrorMessage(ex.getMessage());
		}

		return gson.toJson(response);
	}
}
