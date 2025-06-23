package com.job.repository;

import com.job.model.CV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CVRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<CV> rowMapper = new RowMapper<CV>() {
        @Override
        public CV mapRow(ResultSet rs, int rowNum) throws SQLException {
            CV cv = new CV();
            cv.setId(rs.getInt("id"));
            cv.setCandidateId(rs.getInt("candidate_id"));
            cv.setTitle(rs.getString("title"));
            cv.setSummary(rs.getString("summary"));
            cv.setFileUrl(rs.getString("file_url"));
            cv.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            cv.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return cv;
        }
    };

    public List<CV> findByCandidateId(int candidateId) {
        String sql = "SELECT * FROM cv WHERE candidate_id = ? ORDER BY updated_at DESC";
        return jdbcTemplate.query(sql, rowMapper, candidateId);
    }

    public void save(CV cv) {
        String sql = "INSERT INTO cv(candidate_id, title, summary, file_url) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, cv.getCandidateId(), cv.getTitle(), cv.getSummary(), cv.getFileUrl());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM cv WHERE id = ?", id);
    }
} 
