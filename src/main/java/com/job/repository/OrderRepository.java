package com.job.repository;

import com.job.model.Order;
import com.job.enums.CommonEnums.OrderStatus;
import com.job.enums.CommonEnums.PaymentStatus;
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
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Order> orderRowMapper = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setUserId(rs.getInt("user_id"));
            order.setOrderDate(rs.getTimestamp("order_date"));
            order.setTotalAmount(rs.getBigDecimal("total_amount"));
            order.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));
            order.setPaymentMethod(rs.getString("payment_method"));
            order.setPaymentStatus(PaymentStatus.valueOf(rs.getString("payment_status").toUpperCase()));
            return order;
        }
    };

    // Đã thay đổi: Trả về Order hoặc null
    public Order findById(Integer id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, orderRowMapper).stream().findFirst().orElse(null);
    }

    public List<Order> findByUserId(Integer userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        return jdbcTemplate.query(sql, new Object[]{userId}, orderRowMapper);
    }

    public List<Order> findAll() {
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        return jdbcTemplate.query(sql, orderRowMapper);
    }

    public Integer add(Order order) {
        String sql = "INSERT INTO orders (user_id, order_date, total_amount, status, payment_method, payment_status) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setTimestamp(2, order.getOrderDate() != null ? order.getOrderDate() : new Timestamp(System.currentTimeMillis()));
            ps.setBigDecimal(3, order.getTotalAmount());
            ps.setString(4, order.getStatus().name());
            ps.setString(5, order.getPaymentMethod());
            ps.setString(6, order.getPaymentStatus().name());
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
    }

    public void update(Order order) {
        String sql = "UPDATE orders SET user_id = ?, order_date = ?, total_amount = ?, status = ?, payment_method = ?, payment_status = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                order.getUserId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getPaymentMethod(),
                order.getPaymentStatus().name(),
                order.getId());
    }

    public void updateStatus(Integer id, OrderStatus status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status.name(), id);
    }

    public void updatePaymentStatus(Integer id, PaymentStatus paymentStatus) {
        String sql = "UPDATE orders SET payment_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, paymentStatus.name(), id);
    }

    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}