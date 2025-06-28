package com.job.repository;

import com.job.model.CartItem;
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
import java.sql.Timestamp;
import java.util.List;
// import java.util.Optional; // Không cần import này nếu không trả về Optional

@Repository
public class CartItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<CartItem> cartItemRowMapper = new RowMapper<CartItem>() {
        @Override
        public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            CartItem item = new CartItem();
            item.setId(rs.getInt("id"));
            item.setCartId(rs.getInt("cart_id"));
            item.setPackageId(rs.getInt("package_id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPriceAtAddition(rs.getBigDecimal("price_at_addition"));
            item.setAddedAt(rs.getTimestamp("added_at"));
            return item;
        }
    };

    // Đã thay đổi: Trả về CartItem hoặc null
    public CartItem findById(Integer id) {
        String sql = "SELECT * FROM cart_items WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, cartItemRowMapper).stream().findFirst().orElse(null);
    }

    public List<CartItem> findByCartId(Integer cartId) {
        String sql = "SELECT * FROM cart_items WHERE cart_id = ?";
        return jdbcTemplate.query(sql, new Object[]{cartId}, cartItemRowMapper);
    }

    // Đã thay đổi: Trả về CartItem hoặc null
    public CartItem findByCartIdAndPackageId(Integer cartId, Integer packageId) {
        String sql = "SELECT * FROM cart_items WHERE cart_id = ? AND package_id = ?";
        return jdbcTemplate.query(sql, new Object[]{cartId, packageId}, cartItemRowMapper).stream().findFirst().orElse(null);
    }

    public Integer add(CartItem item) {
        String sql = "INSERT INTO cart_items (cart_id, package_id, quantity, price_at_addition, added_at) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, item.getCartId());
            ps.setInt(2, item.getPackageId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getPriceAtAddition());
            ps.setTimestamp(5, item.getAddedAt() != null ? item.getAddedAt() : new Timestamp(System.currentTimeMillis()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
    }

    public void update(CartItem item) {
        String sql = "UPDATE cart_items SET quantity = ?, price_at_addition = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                item.getQuantity(),
                item.getPriceAtAddition(),
                item.getId());
    }

    public void updateQuantity(Integer id, Integer quantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, id);
    }

    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public boolean deleteByCartId(Integer cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        return jdbcTemplate.update(sql, cartId) > 0;
    }
}