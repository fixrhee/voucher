package com.jpa.voucher.processor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.jpa.voucher.data.Member;
import com.jpa.voucher.data.Outlet;

@Component
@Repository
public class OutletRepository {

	private JdbcTemplate jdbcTemplate;

	public Outlet loadOutletByID(String id, int mid) {
		try {
			Outlet outlet = this.jdbcTemplate.queryForObject(
					"SELECT id, member_id, name, address, created_date FROM outlets WHERE id = ? AND member_id = ?",
					new Object[] { id, mid }, new RowMapper<Outlet>() {
						public Outlet mapRow(ResultSet rs, int arg1) throws SQLException {
							Outlet outlet = new Outlet();
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							outlet.setMember(member);
							outlet.setId(rs.getInt("id"));
							outlet.setName(rs.getString("name"));
							outlet.setAddress(rs.getString("address"));
							outlet.setCreatedDate(rs.getTimestamp("created_date"));
							return outlet;
						}
					});
			return outlet;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public boolean validateOutletRedeem(int oid, int vid) {
		try {
			this.jdbcTemplate.queryForObject(
					"SELECT id from redemption_points WHERE voucher_id = ? AND outlet_id = ? AND active = true;",
					new Object[] { vid, oid }, Integer.class);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	public List<Outlet> getAllOutlet(int currentPage, int pageSize, Integer id) {
		try {
			List<Outlet> outlet = this.jdbcTemplate.query(
					"SELECT id, member_id, name, address, created_date FROM outlets WHERE member_id = ? ORDER BY id DESC LIMIT ?,?;",
					new Object[] { id, currentPage, pageSize }, new RowMapper<Outlet>() {
						public Outlet mapRow(ResultSet rs, int arg1) throws SQLException {
							Outlet outlet = new Outlet();
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							outlet.setMember(member);
							outlet.setId(rs.getInt("id"));
							outlet.setName(rs.getString("name"));
							outlet.setAddress(rs.getString("address"));
							outlet.setCreatedDate(rs.getTimestamp("created_date"));
							return outlet;
						}
					});
			return outlet;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void createOutlet(String name, String address, Integer id) {
		jdbcTemplate.update("insert into outlets (member_id, name, address) values (?, ?, ?)", id, name, address);
	}

	public Integer countOutlets(int mid) {
		Integer count = this.jdbcTemplate.queryForObject("SELECT COUNT(id) from outlets WHERE member_id = ?;",
				new Object[] { mid }, Integer.class);
		return count;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
