package com.jpa.voucher.data;

import java.math.BigDecimal;
import java.util.Date;

public class Voucher {

	private Integer id;
	private Member member;
	private Product product;
	private String code;
	private String name;
	private String description;
	private Integer quota;
	private Boolean active;
	private BigDecimal amount;
	private Double percentage;
	private String imageURL;
	private Boolean publishExpiry;
	private Boolean redeemExpiry;
	private Date publishExpiredDate;
	private Date redeemExpiredDate;
	private Date createdDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuota() {
		return quota;
	}

	public void setQuota(Integer quota) {
		this.quota = quota;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Date getPublishExpiredDate() {
		return publishExpiredDate;
	}

	public void setPublishExpiredDate(Date publishExpiredDate) {
		this.publishExpiredDate = publishExpiredDate;
	}

	public Date getRedeemExpiredDate() {
		return redeemExpiredDate;
	}

	public void setRedeemExpiredDate(Date redeemExpiredDate) {
		this.redeemExpiredDate = redeemExpiredDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getPublishExpiry() {
		return publishExpiry;
	}

	public void setPublishExpiry(Boolean publishExpiry) {
		this.publishExpiry = publishExpiry;
	}

	public Boolean getRedeemExpiry() {
		return redeemExpiry;
	}

	public void setRedeemExpiry(Boolean redeemExpiry) {
		this.redeemExpiry = redeemExpiry;
	}

}
