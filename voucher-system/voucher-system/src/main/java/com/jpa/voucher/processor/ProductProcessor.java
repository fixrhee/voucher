package com.jpa.voucher.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jpa.voucher.data.Member;
import com.jpa.voucher.data.Product;
import com.jpa.voucher.data.Status;
import com.jpa.voucher.data.TransactionException;

@Component
public class ProductProcessor {

	@Autowired
	private MemberProcessor memberProcessor;
	@Autowired
	private ProductRepository productRepository;

	public Map<String, Object> getAllProduct(int currentPage, int pageSize, String token) throws TransactionException {
		Map<String, Object> po = new HashMap<String, Object>();
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		List<Product> pd = productRepository.getAllProduct(m.getId(), currentPage, pageSize);
		Integer count = productRepository.countProducts(m.getId());

		po.put("body", pd);
		po.put("totalRecord", count);
		return po;
	}

	public Product getProductByID(int id, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		Product pd = productRepository.getProductByID(id, m.getId());
		if (pd == null) {
			throw new TransactionException(Status.PRODUCT_NOT_FOUND);
		}
		return pd;
	}

	public void createProduct(String name, String description, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		productRepository.createProduct(name, description, m.getId());
	}

	public void updateProduct(int id, String name, String description, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		Product pd = productRepository.getProductByID(id, m.getId());
		if (pd == null) {
			throw new TransactionException(Status.PRODUCT_NOT_FOUND);
		}
		productRepository.updateProduct(name, description, id);
	}

}
