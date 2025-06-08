package com.job.service;

import com.job.model.Candidate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class CandidateAdminService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Candidate> candidateRowMapper = new RowMapper<Candidate>() {
        @Override
        public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
            Candidate c = new Candidate();
            c.setCandidateID(rs.getInt("CandidateID"));
            c.setUserID(rs.getInt("UserID"));
            c.setResumeUrl(rs.getString("ResumeUrl"));
            c.setBio(rs.getString("Bio"));
            c.setSkills(rs.getString("Skills"));
            return c;
        }
    };

    public List<Candidate> findAll() {
        String sql = "SELECT * FROM candidates";
        return jdbcTemplate.query(sql, candidateRowMapper);
    }

    public Candidate findByID(int id) {
        String sql = "SELECT * FROM candidates WHERE CandidateID = ?";
        List<Candidate> result = jdbcTemplate.query(sql, candidateRowMapper, id);
        return result.isEmpty() ? null : result.get(0);
    }

    public void add(Candidate candidate) {
        String sql = "INSERT INTO candidates (UserID, ResumeUrl, Bio, Skills) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, candidate.getUserID(), candidate.getResumeUrl(), candidate.getBio(), candidate.getSkills());
    }

    public void update(Candidate candidate) {
        String sql = "UPDATE candidates SET UserID = ?, ResumeUrl = ?, Bio = ?, Skills = ? WHERE CandidateID = ?";
        jdbcTemplate.update(sql, candidate.getUserID(), candidate.getResumeUrl(), candidate.getBio(), candidate.getSkills(), candidate.getCandidateID());
    }

    public void deleteByID(int id) {
        String sql = "DELETE FROM candidates WHERE CandidateID = ?";
        jdbcTemplate.update(sql, id);
    }

    public Candidate findByUserID(int userID) {
        String sql = "SELECT * FROM candidates WHERE UserID = ?";
        List<Candidate> result = jdbcTemplate.query(sql, candidateRowMapper, userID);
        return result.isEmpty() ? null : result.get(0);
    }

    // Phân trang thủ công (dành cho danh sách đã lấy từ DB)
    public List<Candidate> getPage(List<Candidate> list, int page, int size) {
        if (list.isEmpty()) return List.of();
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        return from >= list.size() ? List.of() : list.subList(from, to);
    }

    public int countPages(List<Candidate> list, int size) {
        return (int) Math.ceil((double) list.size() / size);
    }
}