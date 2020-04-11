package com.jpa.voucher.processor;

import java.util.List;

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

	public List<Product> getAllProduct(int currentPage, int pageSize, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		List<Product> pd = productRepository.getAllProduct(m.getId(), currentPage, pageSize);
		return pd;
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

}
