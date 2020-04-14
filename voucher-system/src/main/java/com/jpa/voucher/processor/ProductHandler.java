package com.jpa.voucher.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jpa.voucher.data.Product;
import com.jpa.voucher.data.ServiceResponse;
import com.jpa.voucher.data.Status;
import com.jpa.voucher.data.TransactionException;

@Component
public class ProductHandler {

	@Autowired
	private ProductProcessor productProcessor;

	public ServiceResponse getAllProduct(int currentPage, int pageSize, String token) {
		try {
			List<Product> lacq = productProcessor.getAllProduct(currentPage, pageSize, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lacq);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse createProduct(String name, String description, String token) {
		try {
			productProcessor.createProduct(name, description, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, null);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse updateProduct(int id, String name, String description, String token) {
		try {
			productProcessor.updateProduct(id, name, description, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, null);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getProductByID(int id, String token) {
		try {
			Product lacq = productProcessor.getProductByID(id, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lacq);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}
}
