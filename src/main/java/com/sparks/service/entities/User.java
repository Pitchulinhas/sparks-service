package com.sparks.service.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("users")
public class User {
	@Id
	private String id;
	
	@Indexed(unique = true)
	private String email;

	private String name;
	private String password;
	private String phone;
	private String cpf;
}
