package com.sparks.service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sparks.service.entities.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
