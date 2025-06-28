package com.job.repository;

import com.job.model.OrderItem;
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
import java.util.List;
// import java.util.Optional; // Không cần import này nếu không trả về Optional

@Repository
public class OrderItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<OrderItem> orderItemRowMapper = new RowMapper<OrderItem>() {
        @Override
        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderItem item = new OrderItem();
            item.setId(rs.getInt("id"));
            item.setOrderId(rs.getInt("order_id"));
            item.setPackageId(rs.getInt("package_id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPricePerItem(rs.getBigDecimal("price_per_item"));
            item.setSubtotal(rs.getBigDecimal("subtotal"));
            return item;
        }
    };

    // Đã thay đổi: Trả về OrderItem hoặc null
    public OrderItem findById(Integer id) {
        String sql = "SELECT * FROM order_items WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, orderItemRowMapper).stream().findFirst().orElse(null);
    }

    public List<OrderItem> findByOrderId(Integer orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        return jdbcTemplate.query(sql, new Object[]{orderId}, orderItemRowMapper);
    }

    public List<OrderItem> findAll() {
        String sql = "SELECT * FROM order_items";
        return jdbcTemplate.query(sql, orderItemRowMapper);
    }

    public Integer add(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, package_id, quantity, price_per_item, subtotal) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getPackageId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getPricePerItem());
            ps.setBigDecimal(5, item.getSubtotal());
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
    }

    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM order_items WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public boolean deleteByOrderId(Integer orderId) {
        String sql = "DELETE FROM order_items WHERE order_id = ?";
        return jdbcTemplate.update(sql, orderId) > 0;
    }
}