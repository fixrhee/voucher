package com.jpa.voucher.processor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jpa.voucher.data.Member;
import com.jpa.voucher.data.Outlet;
import com.jpa.voucher.data.Product;
import com.jpa.voucher.data.PublishVoucher;
import com.jpa.voucher.data.Voucher;

@Component
@Repository
public class VoucherRepository {

	private JdbcTemplate jdbcTemplate;

	public Voucher getVoucherByID(Object id, int mid) {
		try {
			Voucher voucher = this.jdbcTemplate.queryForObject(
					"SELECT id, member_id, product_id, code, name, description, quota, active, amount, percentage, image_url, publish_expiry, redeem_expiry, publish_expired_date, redeem_expired_date, created_date FROM vouchers WHERE member_id = ? and id = ?",
					new Object[] { mid, id }, new RowMapper<Voucher>() {
						public Voucher mapRow(ResultSet rs, int arg1) throws SQLException {
							Voucher voucher = new Voucher();
							Product product = new Product();
							product.setId(rs.getInt("product_id"));
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							voucher.setId(rs.getInt("id"));
							voucher.setMember(member);
							voucher.setProduct(product);
							voucher.setCode(rs.getString("code"));
							voucher.setName(rs.getString("name"));
							voucher.setDescription(rs.getString("description"));
							voucher.setQuota(rs.getInt("quota"));
							voucher.setActive(rs.getBoolean("active"));
							voucher.setAmount(rs.getBigDecimal("amount"));
							voucher.setPercentage(rs.getDouble("percentage"));
							voucher.setImageURL(rs.getString("image_url"));
							voucher.setPublishExpiry(rs.getBoolean("publish_expiry"));
							voucher.setRedeemExpiry(rs.getBoolean("redeem_expiry"));
							voucher.setPublishExpiredDate(rs.getDate("publish_expired_date"));
							voucher.setRedeemExpiredDate(rs.getDate("redeem_expired_date"));
							voucher.setCreatedDate(rs.getTimestamp("created_date"));
							return voucher;
						}
					});
			return voucher;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Voucher getVoucherByCode(String code, int mid) {
		try {
			Voucher voucher = this.jdbcTemplate.queryForObject(
					"SELECT id, member_id, product_id, code, name, description, quota, active, amount, percentage, image_url, publish_expiry, redeem_expiry, publish_expired_date, redeem_expired_date, created_date FROM vouchers WHERE member_id = ? and code = ?",
					new Object[] { mid, code }, new RowMapper<Voucher>() {
						public Voucher mapRow(ResultSet rs, int arg1) throws SQLException {
							Voucher voucher = new Voucher();
							Product product = new Product();
							product.setId(rs.getInt("product_id"));
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							voucher.setId(rs.getInt("id"));
							voucher.setMember(member);
							voucher.setProduct(product);
							voucher.setCode(rs.getString("code"));
							voucher.setName(rs.getString("name"));
							voucher.setDescription(rs.getString("description"));
							voucher.setQuota(rs.getInt("quota"));
							voucher.setActive(rs.getBoolean("active"));
							voucher.setAmount(rs.getBigDecimal("amount"));
							voucher.setPercentage(rs.getDouble("percentage"));
							voucher.setImageURL(rs.getString("image_url"));
							voucher.setPublishExpiry(rs.getBoolean("publish_expiry"));
							voucher.setRedeemExpiry(rs.getBoolean("redeem_expiry"));
							voucher.setPublishExpiredDate(rs.getDate("publish_expired_date"));
							voucher.setRedeemExpiredDate(rs.getDate("redeem_expired_date"));
							voucher.setCreatedDate(rs.getTimestamp("created_date"));
							return voucher;
						}
					});
			return voucher;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public PublishVoucher searchVoucherByRefID(String rid, int mid) {
		try {
			PublishVoucher voucher = this.jdbcTemplate.queryForObject(
					"SELECT id, voucher_id, member_id, outlet_id, uid, member_ref_id, session_id, name, email, msisdn, address, id_card_no, status, publish_date, redeem_date, redeem_expired_date FROM voucher_publish WHERE member_id = ? and member_ref_id = ?",
					new Object[] { mid, rid }, new RowMapper<PublishVoucher>() {
						public PublishVoucher mapRow(ResultSet rs, int arg1) throws SQLException {
							PublishVoucher voucher = new PublishVoucher();
							Voucher v = new Voucher();
							v.setId(rs.getInt("voucher_id"));
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							Outlet outlet = new Outlet();
							outlet.setId(rs.getInt("outlet_id"));
							voucher.setId(rs.getInt("id"));
							voucher.setMember(member);
							voucher.setVoucher(v);
							voucher.setOutlet(outlet);
							voucher.setUid(rs.getString("uid"));
							voucher.setMemberRefNo(rs.getString("member_ref_id"));
							voucher.setSessionID(rs.getString("session_id"));
							voucher.setName(rs.getString("name"));
							voucher.setEmail(rs.getString("email"));
							voucher.setMsisdn(rs.getString("msisdn"));
							voucher.setAddress(rs.getString("address"));
							voucher.setIdCardNo(rs.getString("id_card_no"));
							voucher.setStatus(rs.getString("status"));
							voucher.setPublishDate(rs.getTimestamp("publish_date"));
							voucher.setRedeemDate(rs.getTimestamp("redeem_date"));
							voucher.setRedeemExpiredDate(rs.getDate("redeem_expired_date"));
							return voucher;
						}
					});
			return voucher;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Voucher> getAllVoucher(int id, int pageSize, int rowNum) {
		try {
			List<Voucher> voucher = this.jdbcTemplate.query(
					"SELECT id, member_id, product_id, code, name, description, quota, active, amount, percentage, image_url, publish_expiry, redeem_expiry, publish_expired_date, redeem_expired_date, created_date FROM vouchers WHERE member_id = ? ORDER BY id DESC LIMIT ?,?;",
					new Object[] { id, pageSize, rowNum }, new RowMapper<Voucher>() {
						public Voucher mapRow(ResultSet rs, int arg1) throws SQLException {
							Voucher voucher = new Voucher();
							Product product = new Product();
							product.setId(rs.getInt("product_id"));
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							voucher.setId(rs.getInt("id"));
							voucher.setMember(member);
							voucher.setProduct(product);
							voucher.setCode(rs.getString("code"));
							voucher.setName(rs.getString("name"));
							voucher.setDescription(rs.getString("description"));
							voucher.setQuota(rs.getInt("quota"));
							voucher.setActive(rs.getBoolean("active"));
							voucher.setAmount(rs.getBigDecimal("amount"));
							voucher.setPercentage(rs.getDouble("percentage"));
							voucher.setImageURL(rs.getString("image_url"));
							voucher.setPublishExpiry(rs.getBoolean("publish_expiry"));
							voucher.setRedeemExpiry(rs.getBoolean("redeem_expiry"));
							voucher.setPublishExpiredDate(rs.getDate("publish_expired_date"));
							voucher.setRedeemExpiredDate(rs.getDate("redeem_expired_date"));
							voucher.setCreatedDate(rs.getTimestamp("created_date"));
							return voucher;
						}
					});
			return voucher;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Voucher> getVoucherByProduct(int id, Object pid, int pageSize, int rowNum) {
		try {
			List<Voucher> voucher = this.jdbcTemplate.query(
					"SELECT id, member_id, product_id, code, name, description, quota, active, amount, percentage, image_url, publish_expiry, redeemExpiry, publish_expired_date, redeem_expired_date, created_date FROM vouchers WHERE member_id = ? AND product_id = ? ORDER BY id DESC LIMIT ?,?;",
					new Object[] { id, pid, pageSize, rowNum }, new RowMapper<Voucher>() {
						public Voucher mapRow(ResultSet rs, int arg1) throws SQLException {
							Voucher voucher = new Voucher();
							Product product = new Product();
							product.setId(rs.getInt("product_id"));
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							voucher.setId(rs.getInt("id"));
							voucher.setMember(member);
							voucher.setProduct(product);
							voucher.setCode(rs.getString("code"));
							voucher.setName(rs.getString("name"));
							voucher.setDescription(rs.getString("description"));
							voucher.setQuota(rs.getInt("quota"));
							voucher.setActive(rs.getBoolean("active"));
							voucher.setAmount(rs.getBigDecimal("amount"));
							voucher.setPercentage(rs.getDouble("percentage"));
							voucher.setImageURL(rs.getString("image_url"));
							voucher.setPublishExpiry(rs.getBoolean("publish_expiry"));
							voucher.setRedeemExpiry(rs.getBoolean("redeem_expiry"));
							voucher.setPublishExpiredDate(rs.getDate("publish_expired_date"));
							voucher.setRedeemExpiredDate(rs.getDate("redeem_expired_date"));
							voucher.setCreatedDate(rs.getTimestamp("created_date"));
							return voucher;
						}
					});
			return voucher;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Map<Integer, Product> getVoucherInMap(List<Integer> ids) {
		String sql = "select * from products where id in (:productID)";
		Map<String, List<Integer>> paramMap = Collections.singletonMap("productID", ids);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
		Map<Integer, Product> mapRet = template.query(sql, paramMap, new ResultSetExtractor<Map<Integer, Product>>() {
			@Override
			public Map<Integer, Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
				HashMap<Integer, Product> mapRet = new HashMap<Integer, Product>();
				while (rs.next()) {
					Product prod = new Product();
					Member member = new Member();
					member.setId(rs.getInt("member_id"));
					prod.setCreatedDate(rs.getTimestamp("created_date"));
					prod.setDescription(rs.getString("description"));
					prod.setId(rs.getInt("id"));
					prod.setMember(member);
					prod.setName(rs.getString("name"));
					mapRet.put(rs.getInt("id"), prod);
				}
				return mapRet;
			}
		});
		return mapRet;
	}

	public boolean validateRedemptionPoint(Object vid, Object oid) {
		try {
			this.jdbcTemplate.queryForObject("SELECT id from redemption_point WHERE voucher_id = ? AND outlet_id = ?;",
					new Object[] { vid, oid }, Integer.class);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	public boolean validateRedemption(String traceNo) {
		try {
			this.jdbcTemplate.queryForObject("SELECT id from voucher_redeem WHERE trace_number = ?;",
					new Object[] { traceNo }, Integer.class);
			return false;
		} catch (EmptyResultDataAccessException e) {
			return true;
		}
	}

	public Integer countQuota(int vid) {
		Integer count = this.jdbcTemplate.queryForObject("SELECT COUNT(id) from voucher_publish WHERE voucher_id = ?;",
				new Object[] { vid }, Integer.class);
		return count;
	}

	public void publishVoucher(Map<String, Object> payload, int mid, String uid, Date expiredDate) {
		jdbcTemplate.update(
				"insert into voucher_publish (voucher_id, member_id, uid, session_id, name, email, msisdn, address, id_card_no, member_ref_id, redeem_expired_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				payload.get("voucherID"), mid, uid, payload.get("sessionID"), payload.get("name"), payload.get("email"),
				payload.get("msisdn"), payload.get("address"), payload.get("idCardNo"), payload.get("memberRefID"),
				expiredDate);
	}

	public void createVoucher(Map<String, Object> payload, int mid, int pid, String imgURL) {
		jdbcTemplate.update(
				"insert into vouchers (member_id, product_id, code, name, description, quota, amount, percentage, image_url, publish_expiry, redeem_expiry, publish_expired_date, redeem_expired_date) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				mid, pid, payload.get("code"), payload.get("name"), payload.get("description"), payload.get("quota"),
				payload.get("amount"), payload.get("percentage"), imgURL, payload.get("publishExpiry"),
				payload.get("redeemExpiry"), payload.get("publishExpiredDate"), payload.get("redeemExpiredDate"));
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
