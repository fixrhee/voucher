package com.jpa.voucher.processor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
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
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

@Component
@Repository
public class MemberRepository {

	private JdbcTemplate jdbcTemplate;

	public Member validateAccess(String username, String secret) {
		try {
			Member member = this.jdbcTemplate.queryForObject(
					"SELECT id, username, name, email, password, secret, notify_url, active, created_date FROM members WHERE username = ? AND password = MD5(?);",
					new Object[] { username, secret }, new RowMapper<Member>() {
						public Member mapRow(ResultSet rs, int arg1) throws SQLException {
							Member member = new Member();
							member.setId(rs.getInt("id"));
							member.setName(rs.getString("name"));
							member.setEmail(rs.getString("email"));
							member.setUsername(rs.getString("username"));
							member.setPassword(rs.getString("password"));
							member.setSecret(rs.getString("secret"));
							member.setNotifyURL(rs.getString("notify_url"));
							member.setActive(rs.getBoolean("active"));
							member.setCreatedDate(rs.getTimestamp("created_date"));
							return member;
						}
					});
			return member;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Member getMemberByID(Object id) {
		try {
			Member member = this.jdbcTemplate.queryForObject(
					"SELECT id, username, name, email, password, secret, notify_url, active, created_date FROM members WHERE id = ?;",
					new Object[] { id }, new RowMapper<Member>() {
						public Member mapRow(ResultSet rs, int arg1) throws SQLException {
							Member member = new Member();
							member.setId(rs.getInt("id"));
							member.setName(rs.getString("name"));
							member.setEmail(rs.getString("email"));
							member.setUsername(rs.getString("username"));
							member.setPassword(rs.getString("password"));
							member.setSecret(rs.getString("secret"));
							member.setNotifyURL(rs.getString("notify_url"));
							member.setActive(rs.getBoolean("active"));
							member.setCreatedDate(rs.getTimestamp("created_date"));
							return member;
						}
					});
			return member;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Member getMemberByUsername(String username) {
		try {
			Member member = this.jdbcTemplate.queryForObject(
					"SELECT id, username, name, email, password, secret, notify_url, active, created_date FROM members WHERE username = ?;",
					new Object[] { username }, new RowMapper<Member>() {
						public Member mapRow(ResultSet rs, int arg1) throws SQLException {
							Member member = new Member();
							member.setId(rs.getInt("id"));
							member.setName(rs.getString("name"));
							member.setEmail(rs.getString("email"));
							member.setUsername(rs.getString("username"));
							member.setPassword(rs.getString("password"));
							member.setSecret(rs.getString("secret"));
							member.setNotifyURL(rs.getString("notify_url"));
							member.setActive(rs.getBoolean("active"));
							member.setCreatedDate(rs.getTimestamp("created_date"));
							return member;
						}
					});
			return member;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Member> getAllMember(int pageSize, int rowNum) {
		try {
			List<Member> member = this.jdbcTemplate.query(
					"SELECT id, username, name, email, password, secret, notify_url, active, created_date FROM members ORDER BY id DESC LIMIT ?,?;",
					new Object[] { pageSize, rowNum }, new RowMapper<Member>() {
						public Member mapRow(ResultSet rs, int arg1) throws SQLException {
							Member member = new Member();
							member.setId(rs.getInt("id"));
							member.setName(rs.getString("name"));
							member.setEmail(rs.getString("email"));
							member.setUsername(rs.getString("username"));
							member.setPassword(rs.getString("password"));
							member.setSecret(rs.getString("secret"));
							member.setNotifyURL(rs.getString("notify_url"));
							member.setActive(rs.getBoolean("active"));
							member.setCreatedDate(rs.getTimestamp("created_date"));
							return member;
						}
					});
			return member;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Map<Integer, Member> getMemberInMap(List<Integer> ids) {
		String sql = "select * from members where id in (:memberID)";
		Map<String, List<Integer>> paramMap = Collections.singletonMap("memberID", ids);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
		Map<Integer, Member> mapRet = template.query(sql, paramMap, new ResultSetExtractor<Map<Integer, Member>>() {
			@Override
			public Map<Integer, Member> extractData(ResultSet rs) throws SQLException, DataAccessException {
				HashMap<Integer, Member> mapRet = new HashMap<Integer, Member>();
				while (rs.next()) {
					Member member = new Member();
					member.setId(rs.getInt("id"));
					member.setName(rs.getString("name"));
					member.setEmail(rs.getString("email"));
					member.setUsername(rs.getString("username"));
					member.setPassword(rs.getString("password"));
					member.setSecret(rs.getString("secret"));
					member.setNotifyURL(rs.getString("notify_url"));
					member.setActive(rs.getBoolean("active"));
					member.setCreatedDate(rs.getTimestamp("created_date"));
					mapRet.put(rs.getInt("id"), member);
				}
				return mapRet;
			}
		});
		return mapRet;
	}

	public void createMember(Member member) {
		jdbcTemplate.update(
				"insert into members (username, name, email, password, secret, notify_url, active) values (?, ?, ?, ?, ?, ?, ?)",
				member.getUsername(), member.getName(), member.getEmail(), member.getPassword(), member.getSecret(),
				member.getNotifyURL(), member.isActive());
	}

	public void updateMember(String id, Member member) {
		jdbcTemplate.update(
				"update members set name = ?, email = ?, password = ?, secret = ?, active = ?, notify_url = ? where id = ?",
				member.getName(), member.getEmail(), member.getPassword(), member.getSecret(), member.isActive(),
				member.getNotifyURL(), id);
	}

	public void deleteMember(String id) {
		jdbcTemplate.update("delete from members  where id = ?", id);
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
