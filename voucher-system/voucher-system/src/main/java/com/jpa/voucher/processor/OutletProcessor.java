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
import com.jpa.voucher.data.Voucher;

@Component
public class OutletProcessor {

	@Autowired
	private MemberProcessor memberProcessor;
	@Autowired
	private OutletRepository outletRepository;
	@Autowired
	private VoucherRepository voucherRepository;

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

	public void updateOutlet(String id, String name, String address, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}

		Outlet outlet = outletRepository.loadOutletByID(id, m.getId());
		if (outlet == null) {
			throw new TransactionException(Status.INVALID_OUTLET);
		}

		outletRepository.updateOutlet(id, name, address);
	}

	public void addRedemptionPoint(String voucherID, String outletID, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}

		Outlet outlet = outletRepository.loadOutletByID(outletID, m.getId());
		if (outlet == null) {
			throw new TransactionException(Status.INVALID_OUTLET);
		}

		Voucher pd = voucherRepository.getVoucherByID(voucherID, m.getId());
		if (pd == null) {
			throw new TransactionException(Status.VOUCHER_NOT_FOUND);
		}

		outletRepository.addRedemptionPoint(voucherID, outletID);

	}

	public List<Outlet> loadRedemptionPoint(String voucherID, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}

		Voucher pd = voucherRepository.getVoucherByID(voucherID, m.getId());
		if (pd == null) {
			throw new TransactionException(Status.VOUCHER_NOT_FOUND);
		}

		return outletRepository.loadRedemptionPoint(voucherID);
	}

}
