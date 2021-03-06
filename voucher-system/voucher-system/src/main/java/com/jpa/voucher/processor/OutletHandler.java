package com.jpa.voucher.processor;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jpa.voucher.data.Outlet;
import com.jpa.voucher.data.ServiceResponse;
import com.jpa.voucher.data.Status;
import com.jpa.voucher.data.TransactionException;

@Component
public class OutletHandler {

	@Autowired
	private OutletProcessor outletProcessor;

	public ServiceResponse createOutlet(String name, String address, String token) {
		try {
			outletProcessor.createOutlet(name, address, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, null);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse updateOutlet(String id, String name, String address, String token) {
		try {
			outletProcessor.updateOutlet(id, name, address, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, null);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getAllOutlet(int currentPage, int pageSize, String token) {
		try {
			Map<String, Object> outlet = outletProcessor.getAllOutlet(currentPage, pageSize, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, outlet);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getOutletByID(String id, String token) {
		try {
			Outlet outlet = outletProcessor.getOutletByID(id, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, outlet);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse addRedemptionPoint(String voucherID, String outletID, String token) {
		try {
			outletProcessor.addRedemptionPoint(voucherID, outletID, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, null);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}
	
	public ServiceResponse loadRedemptionPoint(String voucherID, String token) {
		try {
			List<Outlet> lo = outletProcessor.loadRedemptionPoint(voucherID, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lo);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}
}
