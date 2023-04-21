package com.sparks.service.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("users")
public class User {
	@Id
	private String id;
	
	private String name;
	private String email;
	private String password;
	private String phone;
	private String cpf;
	
	public User(String name, String email, String password, String phone, String cpf) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.cpf = cpf;
	}
}
