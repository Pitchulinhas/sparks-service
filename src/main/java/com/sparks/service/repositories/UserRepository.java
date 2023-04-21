package com.sparks.service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sparks.service.entities.User;

public interface UserRepository extends MongoRepository<User, String> {

}
