package com.jpa.voucher.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpa.voucher.data.Member;
import com.jpa.voucher.data.Outlet;
import com.jpa.voucher.data.Status;
import com.jpa.voucher.data.TransactionException;

@Component
public class OutletProcessor {

	@Autowired
	private MemberProcessor memberProcessor;
	@Autowired
	private OutletRepository outletRepository;

	public Outlet getOutletByID(String id, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		Outlet outlet = outletRepository.loadOutletByID(id, m.getId());
		if (outlet == null) {
			throw new TransactionException(Status.INVALID_OUTLET);
		}
		return outlet;
	}

	public Map<String, Object> getAllOutlet(int currentPage, int pageSize, String token) throws TransactionException {
		Map<String, Object> mo = new HashMap<String, Object>();

		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		List<Outlet> lo = outletRepository.getAllOutlet(currentPage, pageSize, m.getId());
		Integer count = outletRepository.countOutlets(m.getId());

		mo.put("body", lo);
		mo.put("totalRecord", count);
		return mo;
	}

	public void createOutlet(String name, String address, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		outletRepository.createOutlet(name, address, m.getId());
	}

}
