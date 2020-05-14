package com.jpa.voucher.processor;

import java.util.HashMap;
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

	public ServiceResponse getAllVoucher(String start, String end, int currentPage, int pageSize, String token) {
		try {
			Map<String, Object> lacq = voucherProcessor.getAllVoucher(start, end, currentPage, pageSize, token);
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

	public ServiceResponse loadAllPublishVoucher(String start, String end, String status, int currentPage, int pageSize,
			String token) {
		try {
			Map<String, Object> lacq = voucherProcessor.loadAllPublishVoucher(start, end, status, currentPage, pageSize,
					token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lacq);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getPublishVoucherStat(String start, String end, String status, String token) {
		try {
			Map<String, Integer> cmap = new HashMap<String, Integer>();
			Integer stat = voucherProcessor.getPublishVoucherStat(start, end, status, token);
			cmap.put("totalRecord", stat);
			return ResponseBuilder.getStatus(Status.PROCESSED, cmap);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getVoucherByCode(String code, String refID, int currentPage, int pageSize, String token) {
		try {
			List<PublishVoucher> lpv = voucherProcessor.getVoucherByCode(code, refID, currentPage, pageSize, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lpv);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getVoucherByVID(String vid, int currentPage, int pageSize, String token) {
		try {
			List<PublishVoucher> lpv = voucherProcessor.getVoucherByVID(vid, currentPage, pageSize, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lpv);
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

	public ServiceResponse updateVoucher(Voucher payload, int vid, String token) {
		try {
			voucherProcessor.updateVoucher(payload, vid, token);
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

	public ServiceResponse voucherUnpublish(String uid, String token) {
		try {
			voucherProcessor.unpublishVoucher(uid, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, null);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse voucherInquiry(String uid, String token) {
		try {
			PublishVoucher pv = voucherProcessor.inquiryVoucher(uid, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, pv);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse voucherRedeem(String uid, String oid, String rn, String token) {
		try {
			voucherProcessor.redeemVoucher(uid, oid, rn, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, null);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse searchVoucherByRefID(String refID, int curentPage, int pageSize, String token) {
		try {
			List<PublishVoucher> v = voucherProcessor.searchVoucherByRefID(refID, curentPage, pageSize, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, v);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}
}
