package com.sparks.service.consumers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sparks.service.entities.User;
import com.sparks.service.responses.ServiceResponse;
import com.sparks.service.services.UserService;
import com.sparks.utils.StringUtils;

@Component
public class UserConsumer {
	@Autowired
	private UserService userService;

	private Gson gson;

	public UserConsumer() {
		this.gson = new Gson();
	}

	@KafkaListener(id = "${spring.kafka.topics.user.create}", topics = "${spring.kafka.topics.user.create}")
	@SendTo
	public String createUser(String createUserInputAsJson) {
		ServiceResponse<User> response = new ServiceResponse<>();

		try {
			createUserInputAsJson = StringUtils.trimText(createUserInputAsJson, "\"");

			createUserInputAsJson = StringUtils.removeBackSlash(createUserInputAsJson);

			User createUserInput = new Gson().fromJson(createUserInputAsJson, User.class);

			User userCreated = this.userService.createUser(createUserInput);

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
	public String findAllUsers(String in) {
		ServiceResponse<List<User>> response = new ServiceResponse<>();

		try {
			List<User> usersFound = this.userService.findAllUsers();

			response.setData(usersFound);
		} catch (Exception ex) {
			response.setErrorMessage(ex.getMessage());
		}

		return gson.toJson(response);
	}

	@KafkaListener(id = "${spring.kafka.topics.user.find-by-id}", topics = "${spring.kafka.topics.user.find-by-id}")
	@SendTo
	public String findUserById(String id) {
		ServiceResponse<User> response = new ServiceResponse<>();

		try {
			id = StringUtils.trimText(id, "\"");

			User userFound = this.userService.findUserById(id);

			response.setData(userFound);
		} catch (Exception ex) {
			response.setErrorMessage(ex.getMessage());
		}

		return gson.toJson(response);
	}

	@KafkaListener(id = "${spring.kafka.topics.user.update-by-id}", topics = "${spring.kafka.topics.user.update-by-id}")
	@SendTo
	public String updateUserById(String updateUserInputAsJson) {
		ServiceResponse<User> response = new ServiceResponse<>();

		try {
			updateUserInputAsJson = StringUtils.trimText(updateUserInputAsJson, "\"");

			updateUserInputAsJson = StringUtils.removeBackSlash(updateUserInputAsJson);

			User updateUserInput = new Gson().fromJson(updateUserInputAsJson, User.class);

			User userUpdated = this.userService.updateUserById(updateUserInput);

			response.setData(userUpdated);
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

			User userDeleted = this.userService.deleteUserById(id);

			response.setData(userDeleted);
		} catch (Exception ex) {
			response.setErrorMessage(ex.getMessage());
		}

		return gson.toJson(response);
	}
}
