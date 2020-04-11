package com.jpa.voucher.processor;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jpa.voucher.data.Member;
import com.jpa.voucher.data.SessionToken;
import com.jpa.voucher.data.Status;
import com.jpa.voucher.data.TransactionException;

@Component
public class MemberProcessor {

	@Autowired
	private MemberRepository memberRepository;

	public List<Member> getAllMember(int currentPage, int pageSize, String token) throws TransactionException {
		Member m = this.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		List<Member> lacq = memberRepository.getAllMember(currentPage, pageSize);
		return lacq;
	}

	public Member getMemberInfo(String token) throws TransactionException {
		Member m = this.Authenticate(token);
		if (m == null) {
			throw new TransactionException(Status.UNAUTHORIZED_ACCESS);
		}
		Member lacq = memberRepository.getMemberByID(m.getId());
		if (lacq == null) {
			throw new TransactionException(Status.MEMBER_NOT_FOUND);
		}
		return lacq;
	}

	public SessionToken createJWTHMAC256(String username, String secret) throws TransactionException {
		SessionToken st = new SessionToken();
		Member member = memberRepository.validateAccess(username, secret);
		if (member == null) {
			throw new TransactionException(Status.ACCESS_DENIED);
		}
		try {
			String md5Hex = DigestUtils.md5Hex(secret);
			String token = JWT.create().withIssuer("Jatelindo").withSubject(username).sign(Algorithm.HMAC256(md5Hex));
			st.setToken(token);
			return st;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new TransactionException(Status.UNKNOWN_ERROR);
		}
	}

	public DecodedJWT verifyJWTHMAC256(String token, String secret) throws Exception {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withIssuer("Jatelindo").build();
		DecodedJWT jwt = verifier.verify(token);
		return jwt;
	}

	public String decodeJWTHMAC256(String token) throws Exception {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getSubject();
	}

	public Member Authenticate(String token) {
		try {
			String username = decodeJWTHMAC256(token);
			Member member = memberRepository.getMemberByUsername(username);
			verifyJWTHMAC256(token, member.getPassword());
			return member;
		} catch (Exception e) {
			return null;
		}
	}
}
