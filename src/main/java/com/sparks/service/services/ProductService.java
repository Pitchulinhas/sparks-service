package com.sparks.service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparks.service.entities.Product;
import com.sparks.service.mappers.ProductMapper;
import com.sparks.service.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductMapper mapper;

	@Autowired
	private ProductRepository productRepository;

	public Product createProduct(Product createProductInput) {
		return this.productRepository.insert(createProductInput);
	}

	public List<Product> findAllProducts() {
		return this.productRepository.findAll();
	}

	public Product findProductById(String id) {
		return this.productRepository.findById(id).orElse(null);
	}

	public Product updateProductById(Product updateProductInput) {
		Product productFound = this.productRepository.findById(updateProductInput.getId()).orElse(null);

		if (productFound != null) {
			this.mapper.updateProduct(updateProductInput, productFound);

			return this.productRepository.save(productFound);
		}

		return productFound;
	}

	public Product deleteProductById(String id) {
		Product productFound = this.productRepository.findById(id).orElse(null);

		if (productFound != null) {
			this.productRepository.deleteById(id);
		}

		return productFound;
	}
}
