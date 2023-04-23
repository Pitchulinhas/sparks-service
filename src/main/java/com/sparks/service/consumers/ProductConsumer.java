package com.sparks.service.consumers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sparks.service.entities.Product;
import com.sparks.service.mappers.ProductMapper;
import com.sparks.service.repositories.ProductRepository;
import com.sparks.service.responses.ServiceResponse;
import com.sparks.utils.StringUtils;

@Component
public class ProductConsumer {
	@Autowired
	private ProductMapper mapper;

	@Autowired
	private ProductRepository repository;

	private Gson gson;

	public ProductConsumer() {
		this.gson = new Gson();
	}

	@KafkaListener(id = "${spring.kafka.topics.product.create}", topics = "${spring.kafka.topics.product.create}")
	@SendTo
	public String create(String createProductInputAsJson) {
		ServiceResponse<Product> response = new ServiceResponse<>();

		try {
			createProductInputAsJson = StringUtils.trimText(createProductInputAsJson, "\"");

			createProductInputAsJson = StringUtils.removeBackSlash(createProductInputAsJson);

			Product createProductInput = new Gson().fromJson(createProductInputAsJson, Product.class);

			Product productCreated = this.repository.insert(createProductInput);

			response.setData(productCreated);
		} catch (Exception ex) {
			if (ex.getMessage().contains("name dup key")) {
				response.setErrorMessage("Já existe um produto com esse nome");
			} else {
				response.setErrorMessage(ex.getMessage());
			}
		}

		return gson.toJson(response);
	}

	@KafkaListener(id = "${spring.kafka.topics.product.find-all}", topics = "${spring.kafka.topics.product.find-all}")
	@SendTo
	public String findAll(String in) {
		ServiceResponse<List<Product>> response = new ServiceResponse<>();

		try {
			List<Product> productsFound = this.repository.findAll();

			response.setData(productsFound);
		} catch (Exception ex) {
			response.setErrorMessage(ex.getMessage());
		}

		return gson.toJson(response);
	}

	@KafkaListener(id = "${spring.kafka.topics.product.find-by-id}", topics = "${spring.kafka.topics.product.find-by-id}")
	@SendTo
	public String findById(String id) {
		ServiceResponse<Product> response = new ServiceResponse<>();

		try {
			id = StringUtils.trimText(id, "\"");

			Product productFound = this.repository.findById(id).orElse(null);

			response.setData(productFound);
		} catch (Exception ex) {
			response.setErrorMessage(ex.getMessage());
		}

		return gson.toJson(response);
	}

	@KafkaListener(id = "${spring.kafka.topics.product.update-by-id}", topics = "${spring.kafka.topics.product.update-by-id}")
	@SendTo
	public String updateById(String updateProductInputAsJson) {
		ServiceResponse<Product> response = new ServiceResponse<>();

		try {
			updateProductInputAsJson = StringUtils.trimText(updateProductInputAsJson, "\"");

			updateProductInputAsJson = StringUtils.removeBackSlash(updateProductInputAsJson);

			Product updateProductInput = new Gson().fromJson(updateProductInputAsJson, Product.class);

			Product productFound = this.repository.findById(updateProductInput.getId()).orElse(null);

			if (productFound != null) {
				this.mapper.updateProduct(updateProductInput, productFound);

				Product productUpdated = this.repository.save(productFound);

				response.setData(productUpdated);
			}
		} catch (Exception ex) {
			if (ex.getMessage().contains("name dup key")) {
				response.setErrorMessage("Já existe um produto com esse nome");
			} else {
				response.setErrorMessage(ex.getMessage());
			}
		}

		return gson.toJson(response);
	}

	@KafkaListener(id = "${spring.kafka.topics.product.delete-by-id}", topics = "${spring.kafka.topics.product.delete-by-id}")
	@SendTo
	public String deleteById(String id) {
		ServiceResponse<Product> response = new ServiceResponse<>();

		try {
			id = StringUtils.trimText(id, "\"");

			Product productFound = this.repository.findById(id).orElse(null);

			if (productFound != null) {
				this.repository.deleteById(id);

				response.setData(productFound);
			}
		} catch (Exception ex) {
			response.setErrorMessage(ex.getMessage());
		}

		return gson.toJson(response);
	}
}
