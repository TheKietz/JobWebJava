package com.job.repository;

import com.job.model.Candidate;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CandidateRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Candidate> candidateRowMapper = new RowMapper<Candidate>() {
        @Override
        public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
            Candidate c = new Candidate();
            c.setId(rs.getInt("id"));
            c.setUserId(rs.getInt("user_id"));
            c.setResumeUrl(rs.getString("resume_url"));
            c.setSkills(rs.getString("skills"));
            c.setLocation(rs.getString("location"));
            c.setExperienceLevel(rs.getString("experience_level"));
            return c;
        }
    };

    public List<Candidate> findAll() {
        String sql = "SELECT * FROM candidates";
        try {
            return jdbcTemplate.query(sql, candidateRowMapper);
        } catch (Exception e) {
            System.err.println("Error fetching all candidates: " + e.getMessage());
            return List.of();
        }
    }

    public Candidate findByID(Integer id) {
        String sql = "SELECT * FROM candidates WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, candidateRowMapper);
        } catch (Exception e) {
            System.err.println("Error finding candidate by ID: " + id + ", " + e.getMessage());
            return null;
        }
    }

    public Candidate findByUserID(Integer userId) {
        String sql = "SELECT * FROM candidates WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userId}, candidateRowMapper);
        } catch (Exception e) {
            System.err.println("Error finding candidate by user ID: " + userId + ", " + e.getMessage());
            return null;
        }
    }

    public void add(Candidate candidate) {
        String sql = "INSERT INTO candidates (user_id, resume_url, skills, location, experience_level) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    candidate.getUserId(),
                    candidate.getResumeUrl(),
                    candidate.getSkills(),
                    candidate.getLocation(),
                    candidate.getExperienceLevel());
            System.out.println("Added candidate: user_id=" + candidate.getUserId());
        } catch (Exception e) {
            System.err.println("Error adding candidate: " + e.getMessage());
            throw e;
        }
    }

    public void update(Candidate candidate) {
        String sql = "UPDATE candidates SET user_id = ?, resume_url = ?, skills = ?, location = ?, experience_level = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql,
                    candidate.getUserId(),
                    candidate.getResumeUrl(),
                    candidate.getSkills(),
                    candidate.getLocation(),
                    candidate.getExperienceLevel(),
                    candidate.getId());
            System.out.println("Updated candidate: id=" + candidate.getId());
        } catch (Exception e) {
            System.err.println("Error updating candidate: " + e.getMessage());
            throw e;
        }
    }

    public boolean deleteByID(Integer id) {
        String sql = "DELETE FROM candidates WHERE id = ?";
        try {
            int rows = jdbcTemplate.update(sql, id);
            System.out.println("Deleted candidate ID: " + id + ", rows affected: " + rows);
            return rows > 0;
        } catch (Exception e) {
            System.err.println("Error deleting candidate by ID: " + id + ", " + e.getMessage());
            return false;
        }
    }

    public int countCandidates() {
        String sql = "SELECT COUNT(*) FROM candidates";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    public int countCandidatesByDateRange(LocalDate from, LocalDate to) {
        String sql = """
        SELECT COUNT(*) 
        FROM candidates e 
        JOIN users u ON e.user_id = u.id
        WHERE u.created_at BETWEEN ? AND ?
    """;

        Timestamp fromTime = Timestamp.valueOf(from.atStartOfDay());
        Timestamp toTime = Timestamp.valueOf(to.plusDays(1).atStartOfDay()); // để bao trọn ngày to

        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{fromTime, toTime}, Integer.class);
        return count != null ? count : 0;
    }

    public List<Candidate> search(String keyword) {
        try {
            if (keyword == null || keyword.isBlank()) {
                return findAll();
            }
            String sql = "SELECT * FROM candidates WHERE skills LIKE ? OR location LIKE ?";
            String like = "%" + keyword.trim() + "%";
            return jdbcTemplate.query(sql, new Object[]{like, like}, candidateRowMapper);
        } catch (Exception e) {
            System.err.println("Error searching candidates with keyword: " + keyword + ", " + e.getMessage());
            return List.of();
        }
    }

    public List<Candidate> getPage(List<Candidate> list, int page, int size) {
        if (list.isEmpty()) {
            return List.of();
        }
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) {
            return List.of();
        }
        return list.subList(from, to);
    }

    public int countPages(List<Candidate> list, int size) {
        return (int) Math.ceil((double) list.size() / size);
    }
}
