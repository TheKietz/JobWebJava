/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.repository;

import com.job.enums.CommonEnums.FeedbackStatus;
import com.job.model.Feedback;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 11090
 */
@Repository
public class FeedbackRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(FeedbackRepository.class);

    @Autowired // Constructor injection là cách tốt nhất
    public FeedbackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper để ánh xạ ResultSet sang đối tượng Feedback
    private final RowMapper<Feedback> rowMapper = (rs, rowNum) -> {
        Feedback feedback = new Feedback();
        feedback.setId(rs.getInt("id"));
        feedback.setUserId(rs.getInt("user_id"));
        // RẤT QUAN TRỌNG: Lấy giá trị từ ResultSet bằng tên cột
        feedback.setSubject(rs.getString("subject"));
        feedback.setMessage(rs.getString("message"));
        feedback.setFeedbackType(rs.getString("feedback_type"));
        feedback.setReportCategory(rs.getString("report_category"));
        feedback.setAttachmentUrls(rs.getString("attachment_urls"));

        String statusString = rs.getString("status");
        feedback.setStatus(statusString != null ? FeedbackStatus.valueOf(statusString) : FeedbackStatus.PENDING);

        // Đảm bảo tên cột khớp với DB: 'submitted_at' hoặc 'created_at'
        // Dựa trên DB schema bạn cung cấp, nó là 'submitted_at'
        Timestamp submittedAtTimestamp = rs.getTimestamp("submitted_at");
        feedback.setSubmittedAt(submittedAtTimestamp != null ? submittedAtTimestamp.toLocalDateTime() : null);

        return feedback;
    };

    public List<Feedback> findAll() {
        String sql = "SELECT * FROM Feedbacks";
        List<Feedback> jobs = jdbcTemplate.query(sql, rowMapper);
        return jobs;
    }

    public void add(Feedback feedback) {
        String sql = "INSERT INTO feedbacks (user_id, subject, message, feedback_type, report_category, attachment_urls, status, submitted_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, feedback.getUserId());
                ps.setString(2, feedback.getSubject());
                ps.setString(3, feedback.getMessage());
                ps.setString(4, feedback.getFeedbackType());
                ps.setString(5, feedback.getReportCategory());
                ps.setString(6, feedback.getAttachmentUrls());
                ps.setString(7, feedback.getStatus() != null ? feedback.getStatus().name() : FeedbackStatus.PENDING.name()); // Luôn có giá trị mặc định
                ps.setTimestamp(8, feedback.getSubmittedAt() != null ? Timestamp.valueOf(feedback.getSubmittedAt()) : Timestamp.valueOf(LocalDateTime.now())); // Đảm bảo có giá trị

                return ps;
            }, keyHolder);

            // Sau khi update, lấy ID tự động tăng và set vào đối tượng Feedback
            if (keyHolder.getKey() != null) {
                feedback.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
                logger.info("Added new feedback with ID: {}", feedback.getId());
            } else {
                logger.warn("Failed to retrieve generated key for feedback.");
            }
        } catch (Exception e) {
            logger.error("Error adding feedback: {}", e.getMessage(), e);
            throw new RuntimeException("Error adding feedback", e); // Bọc và ném lại RuntimeException
        }
    }
    
    public void updateStatus(Integer feedbackId, FeedbackStatus newStatus) {
        String sql = "UPDATE feedbacks SET status = ? WHERE id = ?";
        try {
            int updatedRows = jdbcTemplate.update(sql, newStatus.name(), feedbackId);
            if (updatedRows > 0) {
                logger.info("Updated status of feedback ID {} to {}", feedbackId, newStatus);
            } else {
                logger.warn("No feedback found with ID {} to update status.", feedbackId);
            }
        } catch (Exception e) {
            logger.error("Error updating status for feedback ID {}: {}", feedbackId, e.getMessage(), e);
            throw new RuntimeException("Error updating feedback status", e);
        }
    }
    
    public Feedback findById(Integer id) {
        String sql = "SELECT * FROM feedbacks WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            logger.error("Error finding job by ID: {}, {}", id, e.getMessage());
            return null;
        }
    }

    public List<Feedback> search(String keyword) {
        try {
            if (keyword == null || keyword.isBlank()) {
                return findAll();
            }
            String sql = "SELECT * FROM jobs WHERE LOWER(title) LIKE ? OR LOWER(category) LIKE ?";
            String likeKeyword = "%" + keyword.trim().toLowerCase() + "%";
            List<Feedback> jobs = jdbcTemplate.query(sql, new Object[]{likeKeyword, likeKeyword}, new BeanPropertyRowMapper<>(Feedback.class));
            System.out.println("search: Keyword='" + keyword + "', Found " + jobs.size() + " jobs");
            return jobs;
        } catch (Exception e) {
            System.err.println("Error searching jobs with keyword: " + keyword + ", " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Feedback> getPage(List<Feedback> list, int page, int size) {
        if (list.isEmpty()) {
            System.out.println("getPage: Empty job list");
            return List.of();
        }
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) {
            System.out.println("getPage: Invalid page range, from=" + from + ", list size=" + list.size());
            return List.of();
        }
        List<Feedback> pagedFeedbacks = list.subList(from, to);
        System.out.println("getPage: Page=" + page + ", Size=" + size + ", Returned " + pagedFeedbacks.size() + " jobs");
        return pagedFeedbacks;
    }

    public int countPages(List<Feedback> list, int size) {
        int pages = (int) Math.ceil((double) list.size() / Math.max(1, size));
        System.out.println("countPages: List size=" + list.size() + ", Size=" + size + ", Pages=" + pages);
        return pages;
    }
}
