package com.sparks.service.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("products")
public class Product {
	@Id
	private String id;
	
	@Indexed(unique = true)
	private String name;
	
	private Float price;
	private String picture;
	private Integer available;
}
