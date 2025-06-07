package com.job.service;

import com.job.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class UserAdminService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserAdminService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getPage(List<User> list, int page, int size) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) {
            return new ArrayList<>();
        }
        return list.subList(from, to);
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public User findByID(int id) {
        String sql = "SELECT * FROM users WHERE userid = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void add(User user) {
        String sql = "INSERT INTO users (fullname, email, password, role, createdat) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getFullName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole(),
                java.sql.Date.valueOf(user.getCreatedAt()));
    }

    public void update(User user) {
        String sql = "UPDATE users SET fullname = ?, email = ?, password = ?, role = ? WHERE userid = ?";
        jdbcTemplate.update(sql,
                user.getFullName(),
                user.getEmail(),
                user.getPasswordHash());
                user.getRole();
                user.getUserID();
    }

    public void deleteByID(int id) {
        String sql = "DELETE FROM users WHERE userid = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<User> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String sql = "SELECT * FROM users WHERE LOWER(fullname) LIKE ? OR LOWER(email) LIKE ?";
        String likeKeyword = "%" + keyword.toLowerCase() + "%";
        return jdbcTemplate.query(sql,
                new Object[]{likeKeyword, likeKeyword},
                new BeanPropertyRowMapper<>(User.class));
    }

    public int countPages(List<User> list, int size) {
        return (int) Math.ceil((double) list.size() / Math.max(1, size));
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean verifyPassword(String rawPassword, String storedPassword) {
        boolean matches = rawPassword != null && rawPassword.equals(storedPassword);
        System.out.println("verifyPassword: rawPassword=" + rawPassword + ", storedPassword=" + storedPassword + ", matches=" + matches);
        return matches;
    }
}