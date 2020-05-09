package com.jpa.voucher.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpa.voucher.data.Member;
import com.jpa.voucher.data.ServiceResponse;
import com.jpa.voucher.data.SessionToken;
import com.jpa.voucher.data.Status;
import com.jpa.voucher.data.TransactionException;

@Component
public class MemberHandler {

	@Autowired
	private MemberProcessor memberProcessor;

	public ServiceResponse createJWTHMAC256(String username, String password) {
		try {
			SessionToken token = memberProcessor.createJWTHMAC256(username, password);
			return ResponseBuilder.getStatus(Status.PROCESSED, token);
		} catch (TransactionException e) {
			System.out.println(e.getMessage());
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getAllMember(int currentPage, int pageSize, String token) {
		try {
			List<Member> lacq = memberProcessor.getAllMember(currentPage, pageSize, token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lacq);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}

	public ServiceResponse getMemberInfo(String token) {
		try {
			Member lacq = memberProcessor.getMemberInfo(token);
			return ResponseBuilder.getStatus(Status.PROCESSED, lacq);
		} catch (TransactionException e) {
			return ResponseBuilder.getStatus(e.getMessage(), null);
		}
	}
}
