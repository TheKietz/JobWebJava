package com.job.repository;

import com.job.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; // Import này có thể bị thiếu
import java.util.List;
// import java.util.Optional; // Không cần import này nếu không trả về Optional

@Repository
public class CartRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Cart> cartRowMapper = new RowMapper<Cart>() {
        @Override
        public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cart cart = new Cart();
            cart.setId(rs.getInt("id"));
            cart.setUserId(rs.getInt("user_id"));
            cart.setCreatedAt(rs.getTimestamp("created_at"));
            cart.setUpdatedAt(rs.getTimestamp("updated_at"));
            return cart;
        }
    };

    // Đã thay đổi: Trả về Cart hoặc null
    public Cart findById(Integer id) {
        String sql = "SELECT * FROM carts WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, cartRowMapper).stream().findFirst().orElse(null);
    }

    // Đã thay đổi: Trả về Cart hoặc null
    public Cart findByUserId(Integer userId) {
        String sql = "SELECT * FROM carts WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, cartRowMapper).stream().findFirst().orElse(null);
    }

    public List<Cart> findAll() {
        String sql = "SELECT * FROM carts";
        return jdbcTemplate.query(sql, cartRowMapper);
    }

    public Integer add(Cart cart) {
        String sql = "INSERT INTO carts (user_id, created_at, updated_at) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cart.getUserId());
            ps.setTimestamp(2, cart.getCreatedAt() != null ? cart.getCreatedAt() : new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(3, cart.getUpdatedAt() != null ? cart.getUpdatedAt() : new Timestamp(System.currentTimeMillis()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
    }

    public void update(Cart cart) {
        String sql = "UPDATE carts SET user_id = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                cart.getUserId(),
                new Timestamp(System.currentTimeMillis()), // Cập nhật thời gian updated_at
                cart.getId());
    }

    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM carts WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}