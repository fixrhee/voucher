package com.jpa.voucher.data;

import java.util.Date;

public class PublishVoucher {

	private Integer id;
	private Voucher voucher;
	private Member member;
	private Outlet outlet;
	private String uid;
	private String memberRefNo;
	private String sessionID;
	private String name;
	private String email;
	private String msisdn;
	private String address;
	private String idCardNo;
	private String status;
	private Date publishDate;
	private Date redeemDate;
	private Date redeemExpiredDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getMemberRefNo() {
		return memberRefNo;
	}

	public void setMemberRefNo(String memberRefNo) {
		this.memberRefNo = memberRefNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getRedeemDate() {
		return redeemDate;
	}

	public void setRedeemDate(Date redeemDate) {
		this.redeemDate = redeemDate;
	}

	public Date getRedeemExpiredDate() {
		return redeemExpiredDate;
	}

	public void setRedeemExpiredDate(Date redeemExpiredDate) {
		this.redeemExpiredDate = redeemExpiredDate;
	}

}
