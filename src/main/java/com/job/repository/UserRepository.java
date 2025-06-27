package com.job.repository;

import com.job.enums.CommonEnums.Gender;
import com.job.model.User;
import com.job.enums.CommonEnums.Role;
import com.job.enums.CommonEnums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getObject("id", Integer.class));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        String role = rs.getString("role");
        user.setRole(role != null ? Role.valueOf(role) : Role.EMPLOYER);
        String status = rs.getString("status");
        user.setStatus(status != null ? Status.valueOf(status) : Status.ACTIVE);
        String gender = rs.getString("gender");
        user.setGender(gender != null ? Gender.valueOf(gender.toUpperCase()) : Gender.Other);
        user.setAvatarUrl(rs.getString("avatar_Url"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return user;
    };

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        System.out.println("findAll: Retrieved " + users.size() + " users");
        return users;
    }

    public User findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, userRowMapper);
        } catch (Exception e) {
            logger.error("Error finding job by ID: {}, {}", id, e.getMessage());
            return null;
        }
//        try {
//            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class) {
//                @Override
//                public User mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
//                    User user = new User();
//                    user.setId(rs.getInt("id"));
//                    user.setFullName(rs.getString("full_name"));
//                    user.setEmail(rs.getString("email"));
//                    user.setPassword(rs.getString("password"));
//                    user.setPhone(rs.getString("phone"));
//                    String roleStr = rs.getString("role");
//                    user.setRole(roleStr != null ? Role.valueOf(roleStr) : null);
//                    String statusStr = rs.getString("status");
//                    user.setStatus(statusStr != null ? Status.valueOf(statusStr) : null);
//                    String genderStr = rs.getString("gender");
//                    user.setStatus(genderStr != null ? Status.valueOf(genderStr) : null);
//                    user.setAvatarUrl(rs.getString("avatar_Url"));
//                    user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
//                    return user;
//                }
//            });
//        } catch (Exception e) {
//            System.err.println("Error finding user by ID: " + id + ", " + e.getMessage());
//            return null;
//        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, new BeanPropertyRowMapper<>(User.class));
            System.out.println("findByEmail: email=" + email + ", Found=" + (user != null ? user.getId() : "null"));
            return user;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("findByEmail: email=" + email + ", Not found");
            return null;
        }
    }

    public void add(User user) {
        String sql = "INSERT INTO users (full_name, email, password, phone, role, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        logger.info("Inserting user: email={}", user.getEmail());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole() != null ? user.getRole().name() : "ADMIN");
            ps.setString(6, user.getStatus() != null ? user.getStatus().name() : "ACTIVE");
            ps.setTimestamp(7, user.getCreatedAt() != null ? Timestamp.valueOf(user.getCreatedAt()) : new Timestamp(System.currentTimeMillis()));
            return ps;
        }, keyHolder);

        Integer generatedId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
        if (generatedId == null) {
            logger.error("Failed to retrieve generated ID for user: email={}", user.getEmail());
            throw new RuntimeException("Cannot retrieve generated ID");
        }

        user.setId(generatedId);
        logger.info("Inserted user: id={}, email={}", generatedId, user.getEmail());
    }

    public void update(User user) {
        String sql = "UPDATE users SET full_name = ?, email = ?, password = ?, phone = ?, role = ?, status = ?, created_at = ? WHERE id = ?";
        System.out.println("Updating user: id=" + user.getId() + ", role=" + (user.getRole() != null ? user.getRole().name() : "ADMIN"));
        jdbcTemplate.update(sql,
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getRole() != null ? user.getRole().name() : "ADMIN",
                user.getStatus() != null ? user.getStatus().name() : "ACTIVE",
                user.getCreatedAt(),
                user.getId());
    }

    public void updateUserProfile(User user) {
        String sql = "UPDATE users SET full_name = ?, gender = ?, phone = ?, avatar_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getFullName(), user.getGender(), user.getPhone(),
                user.getAvatarUrl(), user.getId());
    }

    public void save(User user) {
        String sql = "INSERT INTO users (full_name, email, password, phone, role, status, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        System.out.println("Saving user: email=" + user.getEmail() + ", role=" + (user.getRole() != null ? user.getRole().name() : "CANDIDATE"));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole() != null ? user.getRole().name() : "CANDIDATE");
            ps.setString(6, user.getStatus() != null ? user.getStatus().name() : "ACTIVE");
            ps.setTimestamp(7, java.sql.Timestamp.valueOf(user.getCreatedAt()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            user.setId(key.intValue());
            System.out.println("User saved: id=" + user.getId() + ", email=" + user.getEmail());
        }
    }

    @Transactional
    public boolean deleteById(Integer id) {
        System.out.println("deleteById: Starting deletion for UserID=" + id);
        try {
            // Get candidate_id for the user (if exists)
            Integer candidateId = jdbcTemplate.queryForObject(
                    "SELECT id FROM candidates WHERE user_id = ?",
                    new Object[]{id},
                    Integer.class
            );

            // Delete from job_recommendations
            String deleteJobRecommendationsSql = "DELETE FROM job_recommendations WHERE candidate_id = ?";
            int jobRecommendationsDeleted = candidateId != null ? jdbcTemplate.update(deleteJobRecommendationsSql, candidateId) : 0;
            System.out.println("deleteById: Deleted " + jobRecommendationsDeleted + " JobRecommendations for UserID=" + id);

            // Delete from recent_searches
            String deleteRecentSearchesSql = "DELETE FROM recent_searches WHERE candidate_id = ?";
            int recentSearchesDeleted = candidateId != null ? jdbcTemplate.update(deleteRecentSearchesSql, candidateId) : 0;
            System.out.println("deleteById: Deleted " + recentSearchesDeleted + " RecentSearches for UserID=" + id);

            // Delete from favorite_jobs
            String deleteFavoriteJobsSql = "DELETE FROM favorite_jobs WHERE candidate_id = ?";
            int favoriteJobsDeleted = candidateId != null ? jdbcTemplate.update(deleteFavoriteJobsSql, candidateId) : 0;
            System.out.println("deleteById: Deleted " + favoriteJobsDeleted + " FavoriteJobs for UserID=" + id);

            // Delete from saved_searches
            String deleteSavedSearchesSql = "DELETE FROM saved_searches WHERE candidate_id = ?";
            int savedSearchesDeleted = candidateId != null ? jdbcTemplate.update(deleteSavedSearchesSql, candidateId) : 0;
            System.out.println("deleteById: Deleted " + savedSearchesDeleted + " SavedSearches for UserID=" + id);

            // Delete from applications
            String deleteApplicationsSql = "DELETE FROM applications WHERE candidate_id = ?";
            int applicationsDeleted = candidateId != null ? jdbcTemplate.update(deleteApplicationsSql, candidateId) : 0;
            System.out.println("deleteById: Deleted " + applicationsDeleted + " Applications for UserID=" + id);

            // Delete from feedbacks
            String deleteFeedbacksSql = "DELETE FROM feedbacks WHERE user_id = ?";
            int feedbacksDeleted = jdbcTemplate.update(deleteFeedbacksSql, id);
            System.out.println("deleteById: Deleted " + feedbacksDeleted + " Feedbacks for UserID=" + id);

            // Delete from messages (as sender or receiver)
            String deleteMessagesSql = "DELETE FROM messages WHERE sender_id = ? OR receiver_id = ?";
            int messagesDeleted = jdbcTemplate.update(deleteMessagesSql, id, id);
            System.out.println("deleteById: Deleted " + messagesDeleted + " Messages for UserID=" + id);

            // Delete from notifications
            String deleteNotificationsSql = "DELETE FROM notifications WHERE user_id = ?";
            int notificationsDeleted = jdbcTemplate.update(deleteNotificationsSql, id);
            System.out.println("deleteById: Deleted " + notificationsDeleted + " Notifications for UserID=" + id);

            // Delete from users (cascades to employers, candidates, jobs, subscriptions)
            String deleteUserSql = "DELETE FROM users WHERE id = ?";
            int usersDeleted = jdbcTemplate.update(deleteUserSql, id);
            System.out.println("deleteById: Deleted " + usersDeleted + " Users for UserID=" + id);

            if (usersDeleted == 0) {
                System.err.println("deleteById: No user found with UserID=" + id);
                return false;
            }
            return true;
        } catch (EmptyResultDataAccessException e) {
            // Handle case where candidate_id is not found
            System.out.println("deleteById: No candidate found for UserID=" + id + ", proceeding with other deletions");
            try {
                // Delete from feedbacks
                String deleteFeedbacksSql = "DELETE FROM feedbacks WHERE user_id = ?";
                int feedbacksDeleted = jdbcTemplate.update(deleteFeedbacksSql, id);
                System.out.println("deleteById: Deleted " + feedbacksDeleted + " Feedbacks for UserID=" + id);

                // Delete from messages
                String deleteMessagesSql = "DELETE FROM messages WHERE sender_id = ? OR receiver_id = ?";
                int messagesDeleted = jdbcTemplate.update(deleteMessagesSql, id, id);
                System.out.println("deleteById: Deleted " + messagesDeleted + " Messages for UserID=" + id);

                // Delete from notifications
                String deleteNotificationsSql = "DELETE FROM notifications WHERE user_id = ?";
                int notificationsDeleted = jdbcTemplate.update(deleteNotificationsSql, id);
                System.out.println("deleteById: Deleted " + notificationsDeleted + " Notifications for UserID=" + id);

                // Delete from users
                String deleteUserSql = "DELETE FROM users WHERE id = ?";
                int usersDeleted = jdbcTemplate.update(deleteUserSql, id);
                System.out.println("deleteById: Deleted " + usersDeleted + " Users for UserID=" + id);

                if (usersDeleted == 0) {
                    System.err.println("deleteById: No user found with UserID=" + id);
                    return false;
                }
                return true;
            } catch (DataIntegrityViolationException ex) {
                System.err.println("deleteById: Constraint violation for UserID=" + id + ": " + ex.getMessage());
                ex.printStackTrace();
                return false;
            } catch (Exception ex) {
                System.err.println("deleteById: Unexpected error for UserID=" + id + ": " + ex.getMessage());
                ex.printStackTrace();
                return false;
            }
        } catch (DataIntegrityViolationException e) {
            System.err.println("deleteById: Constraint violation for UserID=" + id + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("deleteById: Unexpected error for UserID=" + id + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Long> getRegistrationStatsByRoleAndDate(int days) {
        String sql = "SELECT DATE(created_at) as reg_date, role, COUNT(*) as count "
                + "FROM users "
                + "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL ? DAY) "
                + "GROUP BY DATE(created_at), role";

        return jdbcTemplate.query(sql, new Object[]{days}, rs -> {
            Map<String, Long> stats = new HashMap<>();
            while (rs.next()) {
                String date = rs.getDate("reg_date").toLocalDate().toString(); // yyyy-MM-dd
                String key = rs.getString("role") + "_" + date;
                stats.put(key, rs.getLong("count"));
            }
            return stats;
        });
    }

    public List<User> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String sql = "SELECT * FROM users WHERE LOWER(full_name) LIKE ? OR LOWER(email) LIKE ?";
        String likeKeyword = "%" + keyword.toLowerCase() + "%";
        List<User> users = jdbcTemplate.query(sql,
                new Object[]{likeKeyword, likeKeyword},
                new BeanPropertyRowMapper<>(User.class));
        System.out.println("search: Keyword='" + keyword + "', Found " + users.size() + " users");
        return users;
    }

    public int countPages(List<User> list, int size) {
        if (list == null || list.isEmpty()) {
            System.out.println("countPages: Empty list, returning 0 pages");
            return 0;
        }
        size = Math.max(1, size);
        int pages = (int) Math.ceil((double) list.size() / size);
        System.out.println("countPages: List size=" + list.size() + ", Size=" + size + ", Pages=" + pages);
        return pages;
    }

    public List<User> getPage(List<User> list, int page, int size) {
        if (list == null || list.isEmpty()) {
            System.out.println("getPage: Empty user list");
            return new ArrayList<>();
        }
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) {
            System.out.println("getPage: Invalid page range, from=" + from + ", list size=" + list.size());
            return new ArrayList<>();
        }
        List<User> pagedList = list.subList(from, to);
        System.out.println("getPage: Page=" + page + ", Size=" + size + ", Returned " + pagedList.size() + " users");
        return pagedList;
    }

    public boolean verifyRawPassword(String rawPassword, String storedPassword) {
        boolean matches = rawPassword != null && rawPassword.equals(storedPassword);
        System.out.println("verifyPassword: rawPassword=****, storedPassword=****, matches=" + matches);
        return matches;
    }
}
