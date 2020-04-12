package com.jpa.voucher.processor;

import java.util.List;

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

	public ServiceResponse getAllOutlet(int currentPage, int pageSize, String token) {
		try {
			List<Outlet> outlet = outletProcessor.getAllOutlet(currentPage, pageSize, token);
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
}
