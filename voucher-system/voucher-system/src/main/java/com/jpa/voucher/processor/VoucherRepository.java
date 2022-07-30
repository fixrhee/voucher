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

	public List<PublishVoucher> loadAllPublishVoucherWithDate(String startDate, String endDate, int currentPage,
			int pageSize, int mid) {
		try {
			List<PublishVoucher> voucher = this.jdbcTemplate.query(
					"SELECT id, voucher_id, member_id, outlet_id, uid, member_ref_id, session_id, name, email, msisdn, address, id_card_no, status, publish_date, redeem_date, redeem_expired_date FROM voucher_publish WHERE member_id = ? AND (DATE(publish_date) BETWEEN ? AND ?)  ORDER BY id DESC LIMIT ?,?;",
					new Object[] { mid, startDate, endDate, currentPage, pageSize }, new RowMapper<PublishVoucher>() {
						public PublishVoucher mapRow(ResultSet rs, int arg1) throws SQLException {
							PublishVoucher voucher = new PublishVoucher();
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							Outlet outlet = new Outlet();
							outlet.setId(rs.getInt("outlet_id"));
							Voucher v = new Voucher();
							v.setId(rs.getInt("voucher_id"));
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

	public List<PublishVoucher> loadAllPublishVoucherWithDateAndStatus(String startDate, String endDate, String status,
			int currentPage, int pageSize, int mid) {
		try {
			List<PublishVoucher> voucher = this.jdbcTemplate.query(
					"SELECT id, voucher_id, member_id, outlet_id, uid, member_ref_id, session_id, name, email, msisdn, address, id_card_no, status, publish_date, redeem_date, redeem_expired_date FROM voucher_publish WHERE member_id = ? AND (DATE(publish_date) BETWEEN ? AND ?)  AND status = ? ORDER BY id DESC LIMIT ?,?;",
					new Object[] { mid, startDate, endDate, status, currentPage, pageSize },
					new RowMapper<PublishVoucher>() {
						public PublishVoucher mapRow(ResultSet rs, int arg1) throws SQLException {
							PublishVoucher voucher = new PublishVoucher();
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							Outlet outlet = new Outlet();
							outlet.setId(rs.getInt("outlet_id"));
							Voucher v = new Voucher();
							v.setId(rs.getInt("voucher_id"));
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

	public List<PublishVoucher> loadAllPublishVoucher(int currentPage, int pageSize, int mid) {
		try {
			List<PublishVoucher> voucher = this.jdbcTemplate.query(
					"SELECT id, voucher_id, member_id, outlet_id, uid, member_ref_id, session_id, name, email, msisdn, address, id_card_no, status, publish_date, redeem_date, redeem_expired_date FROM voucher_publish WHERE member_id = ? ORDER BY id DESC LIMIT ?,?;",
					new Object[] { mid, currentPage, pageSize }, new RowMapper<PublishVoucher>() {
						public PublishVoucher mapRow(ResultSet rs, int arg1) throws SQLException {
							PublishVoucher voucher = new PublishVoucher();
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							Outlet outlet = new Outlet();
							outlet.setId(rs.getInt("outlet_id"));
							Voucher v = new Voucher();
							v.setId(rs.getInt("voucher_id"));
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

	public List<PublishVoucher> loadAllPublishVoucherWithStatus(String status, int currentPage, int pageSize, int mid) {
		try {
			List<PublishVoucher> voucher = this.jdbcTemplate.query(
					"SELECT id, voucher_id, member_id, outlet_id, uid, member_ref_id, session_id, name, email, msisdn, address, id_card_no, status, publish_date, redeem_date, redeem_expired_date FROM voucher_publish WHERE member_id = ? AND status = ? ORDER BY id DESC LIMIT ?,?;",
					new Object[] { mid, status, currentPage, pageSize }, new RowMapper<PublishVoucher>() {
						public PublishVoucher mapRow(ResultSet rs, int arg1) throws SQLException {
							PublishVoucher voucher = new PublishVoucher();
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							Outlet outlet = new Outlet();
							outlet.setId(rs.getInt("outlet_id"));
							Voucher v = new Voucher();
							v.setId(rs.getInt("voucher_id"));
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

	public List<PublishVoucher> loadPublishVoucherWithRefID(String memberRefID, int mid) {
		try {
			List<PublishVoucher> voucher = this.jdbcTemplate.query(
					"SELECT id, voucher_id, member_id, outlet_id, uid, member_ref_id, session_id, name, email, msisdn, address, id_card_no, status, publish_date, redeem_date, redeem_expired_date FROM voucher_publish WHERE member_id = ? AND  member_ref_id = ? ORDER BY id DESC;",
					new Object[] { mid, memberRefID }, new RowMapper<PublishVoucher>() {
						public PublishVoucher mapRow(ResultSet rs, int arg1) throws SQLException {
							PublishVoucher voucher = new PublishVoucher();
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							Outlet outlet = new Outlet();
							outlet.setId(rs.getInt("outlet_id"));
							Voucher v = new Voucher();
							v.setId(rs.getInt("voucher_id"));
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

	public List<PublishVoucher> searchVoucherByCodeRefID(String rid, Voucher v, int currentPage, int pageSize,
			int mid) {
		try {
			List<PublishVoucher> voucher = this.jdbcTemplate.query(
					"SELECT id, voucher_id, member_id, outlet_id, uid, member_ref_id, session_id, name, email, msisdn, address, id_card_no, status, publish_date, redeem_date, redeem_expired_date FROM voucher_publish WHERE member_id = ? and voucher_id = ? and member_ref_id = ? ORDER BY id DESC LIMIT ?,?;",
					new Object[] { mid, v.getId(), rid, currentPage, pageSize }, new RowMapper<PublishVoucher>() {
						public PublishVoucher mapRow(ResultSet rs, int arg1) throws SQLException {
							PublishVoucher voucher = new PublishVoucher();
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

	public List<PublishVoucher> searchVoucherByVID(Voucher v, int currentPage, int pageSize, int mid) {
		try {
			List<PublishVoucher> voucher = this.jdbcTemplate.query(
					"SELECT id, voucher_id, member_id, outlet_id, uid, member_ref_id, session_id, name, email, msisdn, address, id_card_no, status, publish_date, redeem_date, redeem_expired_date FROM voucher_publish WHERE member_id = ? and voucher_id = ? ORDER BY id DESC LIMIT ?,?;",
					new Object[] { mid, v.getId(), currentPage, pageSize }, new RowMapper<PublishVoucher>() {
						public PublishVoucher mapRow(ResultSet rs, int arg1) throws SQLException {
							PublishVoucher voucher = new PublishVoucher();
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

	public List<PublishVoucher> searchVoucherByRefID(String rid, int currentPage, int pageSize, int mid) {
		try {
			List<PublishVoucher> voucher = this.jdbcTemplate.query(
					"SELECT id, voucher_id, member_id, outlet_id, uid, member_ref_id, session_id, name, email, msisdn, address, id_card_no, status, publish_date, redeem_date, redeem_expired_date FROM voucher_publish WHERE member_id = ? and member_ref_id = ? ORDER BY id DESC LIMIT ?,?;",
					new Object[] { mid, rid, currentPage, pageSize }, new RowMapper<PublishVoucher>() {
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

	public PublishVoucher searchVoucherByUID(String uid, int mid) {
		try {
			PublishVoucher voucher = this.jdbcTemplate.queryForObject(
					"SELECT id, voucher_id, member_id, outlet_id, uid, member_ref_id, session_id, name, email, msisdn, address, id_card_no, status, publish_date, redeem_date, redeem_expired_date FROM voucher_publish WHERE member_id = ? and uid = ? ORDER BY id DESC LIMIT 1;",
					new Object[] { mid, uid }, new RowMapper<PublishVoucher>() {
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

	public List<Voucher> getAllVoucherWithDate(String start, String end, int id, int pageSize, int rowNum) {
		try {
			List<Voucher> voucher = this.jdbcTemplate.query(
					"SELECT id, member_id, product_id, code, name, description, quota, active, amount, percentage, image_url, publish_expiry, redeem_expiry, publish_expired_date, redeem_expired_date, created_date FROM vouchers WHERE member_id = ? AND (DATE(created_date) BETWEEN ? AND ?) ORDER BY id DESC LIMIT ?,?;",
					new Object[] { id, start, end, pageSize, rowNum }, new RowMapper<Voucher>() {
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
					"SELECT id, member_id, product_id, code, name, description, quota, active, amount, percentage, image_url, publish_expiry, redeem_expiry, publish_expired_date, redeem_expired_date, created_date FROM vouchers WHERE member_id = ? AND product_id = ? ORDER BY id DESC LIMIT ?,?;",
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

	public Map<Integer, Voucher> getVoucherInMap(List<Integer> ids) {
		String sql = "select * from vouchers where id in (:voucherID)";
		Map<String, List<Integer>> paramMap = Collections.singletonMap("voucherID", ids);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
		Map<Integer, Voucher> mapRet = template.query(sql, paramMap, new ResultSetExtractor<Map<Integer, Voucher>>() {
			@Override
			public Map<Integer, Voucher> extractData(ResultSet rs) throws SQLException, DataAccessException {
				HashMap<Integer, Voucher> mapRet = new HashMap<Integer, Voucher>();
				while (rs.next()) {
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
					mapRet.put(rs.getInt("id"), voucher);
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

	public boolean validateCode(Object code, int memberID) {
		try {
			this.jdbcTemplate.queryForObject("SELECT id from vouchers WHERE code = ? AND member_id = ?;",
					new Object[] { code, memberID }, Integer.class);
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

	public Integer countVouchers(int mid) {
		Integer count = this.jdbcTemplate.queryForObject("SELECT COUNT(id) from vouchers WHERE member_id = ?;",
				new Object[] { mid }, Integer.class);
		return count;
	}

	public Integer countPublishVouchers(int mid) {
		Integer count = this.jdbcTemplate.queryForObject("SELECT COUNT(id) from voucher_publish WHERE member_id = ?;",
				new Object[] { mid }, Integer.class);
		return count;
	}

	public Integer countPublishVouchersByStatus(int mid, String status) {
		Integer count = this.jdbcTemplate.queryForObject(
				"SELECT COUNT(id) from voucher_publish WHERE member_id = ? AND status = ?;",
				new Object[] { mid, status }, Integer.class);
		return count;
	}

	public Integer countPublishVouchersWithDatesByStatus(String start, String end, int mid, String status) {
		Integer count = this.jdbcTemplate.queryForObject(
				"SELECT COUNT(id) from voucher_publish WHERE member_id = ? AND status = ? AND (DATE(publish_date) BETWEEN ? AND ?);",
				new Object[] { mid, status, start, end }, Integer.class);
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

	public void unpublishVoucher(String uid, int mid) {
		jdbcTemplate.update("DELETE FROM voucher_publish where member_id = ? and uid = ?", mid, uid);
	}

	public void redeemVoucher(String uid, String oid, String rn, int mid) {
		jdbcTemplate.update(
				"UPDATE voucher_publish set outlet_id = ?, redemption_number = ?, status = 'USED', redeem_date = NOW() where member_id = ? and uid = ?",
				oid, rn, mid, uid);
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void updateVoucher(Voucher payload, int vid, int mid) {
		jdbcTemplate.update(
				"UPDATE vouchers set code = ?, name = ?, description = ?, quota = ?, active = ?, amount = ?, percentage = ?, publish_expiry = ?, redeem_expiry = ?, publish_expired_date = ?, redeem_expired_date = ? where id = ?",
				payload.getCode(), payload.getName(), payload.getDescription(), payload.getQuota(), payload.getActive(),
				payload.getAmount(), payload.getPercentage(), payload.getPublishExpiry(), payload.getRedeemExpiry(),
				payload.getPublishExpiredDate(), payload.getRedeemExpiredDate(), vid);
	}

}
