package com.jpa.voucher.processor;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.jpa.voucher.data.Member;
import com.jpa.voucher.data.Outlet;
import com.jpa.voucher.data.Product;
import com.jpa.voucher.data.PublishVoucher;
import com.jpa.voucher.data.Status;
import com.jpa.voucher.data.TransactionException;
import com.jpa.voucher.data.Voucher;
import com.jpa.voucher.data.VoucherImage;
import com.noctarius.snowcast.Snowcast;
import com.noctarius.snowcast.SnowcastEpoch;
import com.noctarius.snowcast.SnowcastSequencer;
import com.noctarius.snowcast.SnowcastSystem;

@Component
public class VoucherProcessor {

	@Autowired
	private MemberProcessor memberProcessor;
	@Autowired
	private VoucherRepository voucherRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private HazelcastInstance hazelcastInstance;
	@Autowired
	private OutletRepository outletRepository;
	private SnowcastSequencer snowcastSequencer;

	@PostConstruct
	public void init() {
		Snowcast snowcast = SnowcastSystem.snowcast(hazelcastInstance);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(2014, Calendar.JANUARY, 1, 0, 0, 0);
		SnowcastEpoch epoch = SnowcastEpoch.byInstant(calendar.toInstant());
		snowcastSequencer = snowcast.createSequencer("voucherSequence", epoch);
	}

	public List<Voucher> getAllVoucher(int currentPage, int pageSize, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		List<Voucher> lv = voucherRepository.getAllVoucher(m.getId(), currentPage, pageSize);
		List<Integer> ids = new LinkedList<Integer>();
		for (int i = 0; i < lv.size(); i++) {
			ids.add(lv.get(i).getProduct().getId());
		}

		List<Voucher> lvc = new ArrayList<Voucher>(lv);
		Map<Integer, Product> pm = productRepository.getProductInMap(ids);
		for (int i = 0; i < lv.size(); i++) {
			lvc.get(i).setProduct(pm.get(lv.get(i).getProduct().getId()));
			lvc.get(i).setMember(m);
		}
		return lvc;
	}

	public List<Voucher> getVoucherByProduct(String id, int currentPage, int pageSize, String token)
			throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		Product pd = productRepository.getProductByID(id, m.getId());
		if (pd == null) {
			throw new TransactionException(Status.PRODUCT_NOT_FOUND);
		}
		List<Voucher> lv = voucherRepository.getVoucherByProduct(m.getId(), id, currentPage, pageSize);
		if (lv.size() == 0) {
			throw new TransactionException(Status.VOUCHER_NOT_FOUND);
		}
		return lv;
	}

	public List<PublishVoucher> getVoucherByCode(String code, String refID, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		Voucher v = voucherRepository.getVoucherByCode(code, m.getId());
		if (v == null) {
			throw new TransactionException(Status.VOUCHER_NOT_FOUND);
		}
		List<PublishVoucher> lv = voucherRepository.searchVoucherByCodeRefID(refID, v, m.getId());
		return lv;
	}

	public Voucher getVoucherByID(int id, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		Voucher pd = voucherRepository.getVoucherByID(id, m.getId());
		if (pd == null) {
			throw new TransactionException(Status.VOUCHER_NOT_FOUND);
		}
		return pd;
	}

	public List<PublishVoucher> searchVoucherByRefID(String refID, int currentPage, int pageSize, String token)
			throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		List<PublishVoucher> pv = voucherRepository.searchVoucherByRefID(refID, currentPage, pageSize, m.getId());
		if (pv.size() == 0) {
			throw new TransactionException(Status.VOUCHER_NOT_FOUND);
		}
		List<Integer> ids = new LinkedList<Integer>();
		for (int i = 0; i < pv.size(); i++) {
			ids.add(pv.get(i).getVoucher().getId());
		}

		List<PublishVoucher> pvc = new ArrayList<PublishVoucher>(pv);
		Map<Integer, Voucher> pm = voucherRepository.getVoucherInMap(ids);
		for (int i = 0; i < pv.size(); i++) {
			pvc.get(i).setVoucher(pm.get(pv.get(i).getVoucher().getId()));
			pvc.get(i).setMember(m);
		}

		return pvc;
	}

	public void createVoucher(Map<String, Object> payload, VoucherImage img, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		Product p = productRepository.getProductByID(payload.get("productID"), m.getId());
		if (p == null) {
			throw new TransactionException(Status.PRODUCT_NOT_FOUND);
		}
		String publishExpiry = (String) payload.get("publishExpiry");
		boolean pExpiry = Boolean.valueOf(publishExpiry);
		payload.put("publishExpiry", pExpiry);
		if (pExpiry == true) {
			if (payload.get("publishExpiredDate") == null) {
				throw new TransactionException(Status.INVALID_PARAMETER);
			} else {
				try {
					Utils.validateDate((String) payload.get("publishExpiredDate"));
				} catch (DateTimeParseException e) {
					throw new TransactionException(Status.INVALID_DATE);
				}
			}
		}
		String redeemExpiry = (String) payload.get("redeemExpiry");
		boolean rExpiry = Boolean.valueOf(redeemExpiry);
		payload.put("redeemExpiry", rExpiry);
		if (rExpiry == true) {
			if (payload.get("redeemExpiredDate") == null) {
				throw new TransactionException(Status.INVALID_PARAMETER);
			} else {
				try {
					Utils.validateDate((String) payload.get("redeemExpiredDate"));
				} catch (DateTimeParseException e) {
					throw new TransactionException(Status.INVALID_DATE);
				}
			}
		}

		boolean valCode = voucherRepository.validateCode(payload.get("code"), m.getId());
		if (valCode == false) {
			throw new TransactionException(Status.VOUCHER_CODE_ALREADY_USED);
		}

		String imgURL = img.getFileName() + "." + img.getFileExtension();
		voucherRepository.createVoucher(payload, m.getId(), p.getId(), imgURL);
	}

	public PublishVoucher publishVoucher(Map<String, Object> payload, String token) throws TransactionException {
		try {
			Member m = memberProcessor.Authenticate(token);
			if (m == null) {
				throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
			}
			Voucher pd = voucherRepository.getVoucherByID(payload.get("voucherID"), m.getId());
			if (pd == null) {
				throw new TransactionException(Status.VOUCHER_NOT_FOUND);
			}
			if (pd.getActive() == null || pd.getActive() == false) {
				throw new TransactionException(Status.VOUCHER_INACTIVE);
			}
			if (pd.getPublishExpiry() != null && pd.getPublishExpiry() == true) {
				if (pd.getPublishExpiredDate().compareTo(new Date()) <= 0) {
					throw new TransactionException(Status.REGISTRATION_TIME_EXPIRED);
				}
			}

			if (pd.getRedeemExpiry() == false) {
				pd.setRedeemExpiredDate(null);
			}

			String uid = "";

			if (pd.getQuota() > 0) {
				// LOCK Voucher to check QUOTA
				IMap<Integer, String> qLock = hazelcastInstance.getMap("QuotaLock");
				qLock.lock(pd.getId(), 80000, TimeUnit.MILLISECONDS);
				Integer currentQuota = voucherRepository.countQuota(pd.getId());
				if (currentQuota.compareTo(pd.getQuota()) >= 0) {
					throw new TransactionException(Status.QUOTA_EXCEEDED);
				}
				uid = String.valueOf(snowcastSequencer.next());
				voucherRepository.publishVoucher(payload, m.getId(), uid, pd.getRedeemExpiredDate());
				qLock.unlock(pd.getId());
			} else {
				uid = String.valueOf(snowcastSequencer.next());
				voucherRepository.publishVoucher(payload, m.getId(), uid, pd.getRedeemExpiredDate());
			}

			PublishVoucher pv = new PublishVoucher();
			pv.setUid(uid);
			pv.setSessionID((String) payload.get("sessionID"));
			pv.setVoucher(pd);
			return pv;
		} catch (InterruptedException e) {
			throw new TransactionException(Status.UNKNOWN_ERROR);
		}

	}

	public PublishVoucher unpublishVoucher(String uid, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		PublishVoucher pv = voucherRepository.searchVoucherByUID(uid, m.getId());
		if (pv == null) {
			throw new TransactionException(Status.VOUCHER_NOT_FOUND);
		}
		if (pv.getStatus().equalsIgnoreCase("USED")) {
			throw new TransactionException(Status.VOUCHER_ALREADY_USED);
		} else {
			voucherRepository.unpublishVoucher(uid, m.getId());
		}
		return pv;
	}

	public PublishVoucher inquiryVoucher(String uid, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		PublishVoucher pv = voucherRepository.searchVoucherByUID(uid, m.getId());
		if (pv == null) {
			throw new TransactionException(Status.VOUCHER_NOT_FOUND);
		}
		return pv;
	}

	public PublishVoucher redeemVoucher(String uid, String oid, String rn, String token) throws TransactionException {
		Member m = memberProcessor.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		PublishVoucher pv = voucherRepository.searchVoucherByUID(uid, m.getId());
		if (pv == null) {
			throw new TransactionException(Status.VOUCHER_NOT_FOUND);
		}
		if (pv.getStatus().equalsIgnoreCase("USED")) {
			throw new TransactionException(Status.VOUCHER_ALREADY_USED);
		}
		Outlet outlet = outletRepository.loadOutletByID(oid, m.getId());
		if (outlet != null) {
			if (outletRepository.validateOutletRedeem(outlet.getId(), pv.getVoucher().getId()) == false) {
				throw new TransactionException(Status.INVALID_REDEMPTION_POINT);
			}
		} else {
			throw new TransactionException(Status.INVALID_OUTLET);
		}
		if (pv.getRedeemExpiredDate() != null) {
			if (pv.getRedeemExpiredDate().compareTo(new Date()) <= 0) {
				throw new TransactionException(Status.REDEMPTION_TIME_EXPIRED);
			}
		}

		voucherRepository.redeemVoucher(uid, oid, rn, m.getId());
		return pv;
	}

	public OutletRepository getOutletRepository() {
		return outletRepository;
	}

	public void setOutletRepository(OutletRepository outletRepository) {
		this.outletRepository = outletRepository;
	}

}
