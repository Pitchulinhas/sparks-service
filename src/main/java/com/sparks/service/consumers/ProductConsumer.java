package com.sparks.service.consumers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sparks.service.entities.Product;
import com.sparks.service.responses.ServiceResponse;
import com.sparks.service.services.ProductService;
import com.sparks.utils.StringUtils;

@Component
public class ProductConsumer {
	@Autowired
	private ProductService productService;

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

			Product productCreated = this.productService.createProduct(createProductInput);

			response.setData(productCreated);
		} catch (Exception ex) {
			if (ex.getMessage().contains("barCode dup key")) {
				response.setErrorMessage("Já existe um produto com esse código de barras");
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
			List<Product> productsFound = this.productService.findAllProducts();

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

			Product productFound = this.productService.findProductById(id);

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

			Product productUpdated = this.productService.updateProductById(updateProductInput);

			response.setData(productUpdated);
		} catch (Exception ex) {
			if (ex.getMessage().contains("barCode dup key")) {
				response.setErrorMessage("Já existe um produto com esse código de barras");
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

			Product productDeleted = this.productService.deleteProductById(id);

			response.setData(productDeleted);
		} catch (Exception ex) {
			response.setErrorMessage(ex.getMessage());
		}

		return gson.toJson(response);
	}
}
