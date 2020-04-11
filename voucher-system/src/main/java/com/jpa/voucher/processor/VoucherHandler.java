package com.jpa.voucher.processor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpa.voucher.data.PublishVoucher;
import com.jpa.voucher.data.ServiceResponse;
import com.jpa.voucher.data.Status;
import com.jpa.voucher.data.TransactionException;
import com.jpa.voucher.data.Voucher;
import com.jpa.voucher.data.VoucherImage;

@Component
public class VoucherHandler {

	@Autowired
	private VoucherProcessor voucherProcessor;

	public ServiceResponse getAllVoucher(int currentPage, int pageSize, String token) {
		try {
			List<Voucher> lacq = voucherProcessor.getAllVoucher(currentPage, pageSize, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lacq);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getVoucherByProduct(String id, int currentPage, int pageSize, String token) {
		try {
			List<Voucher> lacq = voucherProcessor.getVoucherByProduct(id, currentPage, pageSize, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lacq);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getVoucherByID(int id, String token) {
		try {
			Voucher lacq = voucherProcessor.getVoucherByID(id, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lacq);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getVoucherByCode(String code, String token) {
		try {
			Voucher lacq = voucherProcessor.getVoucherByCode(code, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lacq);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse createVoucher(Map<String, Object> payload, VoucherImage img, String token) {
		try {
			voucherProcessor.createVoucher(payload, img, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, null);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse voucherPublish(Map<String, Object> payload, String token) {
		try {
			PublishVoucher v = voucherProcessor.publishVoucher(payload, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, v);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse searchVoucherByRefID(String refID, String token) {
		try {
			PublishVoucher v = voucherProcessor.searchVoucherByRefID(refID, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, v);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}
}
