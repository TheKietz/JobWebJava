package com.job.repository;

import com.job.enums.CommonEnums.TransactionStatus;
import com.job.model.Transaction;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Transaction> rowMapper = (rs, rowNum) -> {
        Transaction t = new Transaction();
        t.setId(rs.getInt("id"));
        t.setUserId(rs.getInt("user_id"));
        t.setUserName(rs.getString("user_name"));
        t.setPackageId(rs.getInt("package_id"));
        t.setPackageName(rs.getString("package_name"));
        t.setAmount(rs.getBigDecimal("amount"));
        t.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        t.setPaymentMethod(rs.getString("payment_method"));
        String status = rs.getString("status");
        t.setStatus(status != null ? TransactionStatus.valueOf(status) : TransactionStatus.PENDING);
        return t;
    };

    public List<Transaction> findAll() {
        String sql = """
        SELECT t.*,
               u.full_name AS user_name,
               s.name AS package_name
        FROM transactions t
        LEFT JOIN users u ON t.user_id = u.id
        LEFT JOIN service_packages s ON t.package_id = s.id
        WHERE CAST(t.user_id AS CHAR) LIKE ?
           OR CAST(t.package_id AS CHAR) LIKE ?
           OR LOWER(t.payment_method) LIKE ?
           OR LOWER(u.full_name) LIKE ?
           OR LOWER(s.name) LIKE ?
        ORDER BY t.created_at DESC
    """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Transaction findById(int id) {

        String sql = """
            SELECT t.*, 
                   u.full_name AS user_name,
                   s.name AS package_name
            FROM transactions t
            LEFT JOIN users u ON t.user_id = u.id
            LEFT JOIN service_packages s ON t.package_id = s.id
            WHERE t.id = ?
    """;
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (Exception e) {
            return null;
        }
    }

    public void add(Transaction t) {
        String sql = "INSERT INTO transactions (user_id, package_id, amount, payment_method, status, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                t.getUserId(),
                t.getPackageId(),
                t.getAmount(),
                t.getPaymentMethod(),
                t.getStatus().name(),
                Timestamp.valueOf(t.getCreatedAt()));
    }

    public void update(Transaction t) {
        String sql = "UPDATE transactions SET user_id = ?, package_id = ?, amount = ?, payment_method = ?, status = ?, created_at = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                t.getUserId(),
                t.getPackageId(),
                t.getAmount(),
                t.getPaymentMethod(),
                t.getStatus().name(),
                Timestamp.valueOf(t.getCreatedAt()),
                t.getId());
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public BigDecimal getTotalRevenue() {
        String sql = "SELECT SUM(amount) FROM transactions WHERE status = ?";
        BigDecimal total = jdbcTemplate.queryForObject(sql, new Object[]{"SUCCESS"}, BigDecimal.class);
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getTotalRevenueToday() {
        String sql = "SELECT SUM(amount) FROM transactions WHERE DATE(created_at) = CURDATE()";
        BigDecimal total = jdbcTemplate.queryForObject(sql, BigDecimal.class);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public BigDecimal getTotalRevenueByDateRange(LocalDate from, LocalDate to) {
        String sql = """
        SELECT SUM(amount)
        FROM transactions
        WHERE status = 'SUCCESS'
          AND created_at BETWEEN ? AND ?
    """;

        Timestamp fromTime = Timestamp.valueOf(from.atStartOfDay());
        Timestamp toTime = Timestamp.valueOf(to.plusDays(1).atStartOfDay()); // để lấy đến hết ngày `to`

        BigDecimal total = jdbcTemplate.queryForObject(sql, new Object[]{fromTime, toTime}, BigDecimal.class);
        return total != null ? total : BigDecimal.ZERO;
    }
    public Map<String, BigDecimal> getRevenueByPackage() {
        String sql = "SELECT sp.name, COALESCE(SUM(t.amount), 0) as total " +
                     "FROM service_packages sp " +
                     "LEFT JOIN transactions t ON sp.id = t.package_id " +
                     "GROUP BY sp.name";
        return jdbcTemplate.query(sql, rs -> {
            Map<String, BigDecimal> revenueMap = new HashMap<>();
            while (rs.next()) {
                revenueMap.put(rs.getString("name"), rs.getBigDecimal("total"));
            }
            return revenueMap;
        });
    }
    public List<Transaction> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            keyword = "";
        }

        String sql = """
            SELECT t.*, 
                   u.full_name AS user_name,
                   s.name AS package_name
            FROM transactions t
            LEFT JOIN users u ON t.user_id = u.id
            LEFT JOIN service_packages s ON t.package_id = s.id
            WHERE CAST(t.user_id AS CHAR) LIKE ?
               OR CAST(t.package_id AS CHAR) LIKE ?
               OR LOWER(t.payment_method) LIKE ?
               OR LOWER(u.full_name) LIKE ?
               OR LOWER(s.name) LIKE ?
            ORDER BY t.created_at DESC
    """;

        String like = "%" + keyword.toLowerCase() + "%";
        return jdbcTemplate.query(sql, new Object[]{like, like, like, like, like}, rowMapper);
    }

    public List<Transaction> getPage(List<Transaction> list, int page, int size) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) {
            return new ArrayList<>();
        }
        return list.subList(from, to);
    }

    public int countPages(List<Transaction> list, int size) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return (int) Math.ceil((double) list.size() / size);
    }
}
