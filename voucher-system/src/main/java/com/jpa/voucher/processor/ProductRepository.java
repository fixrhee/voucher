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
import com.jpa.voucher.data.Product;

@Component
@Repository
public class ProductRepository {

	private JdbcTemplate jdbcTemplate;

	public Product getProductByID(Object id, int mid) {
		try {
			Product prod = this.jdbcTemplate.queryForObject(
					"SELECT id, member_id, name, description, created_date FROM products WHERE id = ? AND member_id = ?;",
					new Object[] { id, mid }, new RowMapper<Product>() {
						public Product mapRow(ResultSet rs, int arg1) throws SQLException {
							Product prod = new Product();
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							prod.setCreatedDate(rs.getTimestamp("created_date"));
							prod.setDescription(rs.getString("description"));
							prod.setId(rs.getInt("id"));
							prod.setMember(member);
							prod.setName(rs.getString("name"));
							return prod;
						}
					});
			return prod;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Product> getAllProduct(int id, int pageSize, int rowNum) {
		try {
			List<Product> prod = this.jdbcTemplate.query(
					"SELECT id, member_id, name, description, created_date FROM products WHERE member_id = ? ORDER BY id DESC LIMIT ?,?;",
					new Object[] { id, pageSize, rowNum }, new RowMapper<Product>() {
						public Product mapRow(ResultSet rs, int arg1) throws SQLException {
							Product prod = new Product();
							Member member = new Member();
							member.setId(rs.getInt("member_id"));
							prod.setCreatedDate(rs.getTimestamp("created_date"));
							prod.setDescription(rs.getString("description"));
							prod.setId(rs.getInt("id"));
							prod.setMember(member);
							prod.setName(rs.getString("name"));
							return prod;
						}
					});
			return prod;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Map<Integer, Product> getProductInMap(List<Integer> ids) {
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

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
