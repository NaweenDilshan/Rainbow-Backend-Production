package com.rainbow_sims.rainbow_SIMS.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.rainbow_sims.rainbow_SIMS.model.User;

@Component
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int save(User user) {
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		final String INSERT_SQL = "INSERT INTO user (username, password, role, is_active, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		return jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getRole());
			ps.setBoolean(4, user.isActive());
			ps.setTimestamp(5, Timestamp.valueOf(user.getCreatedAt()));
			ps.setTimestamp(6, Timestamp.valueOf(user.getUpdatedAt()));
			return ps;
		}, keyHolder);
	}


	public User findUserByUserName(String username) {
		List<User> users = jdbcTemplate.query("select * from user where username = ?", new Object[]{username},
				(rs, rowNum) -> new User(rs.getString("username"), rs.getString("password"), rs.getString("role")));

		if (users.isEmpty()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return users.get(0); // Return the first user or implement logic to choose one
	}


}
