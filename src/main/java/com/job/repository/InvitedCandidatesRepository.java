/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.repository;
import com.job.model.InvitedCandidates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class InvitedCandidatesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper để ánh xạ ResultSet thành đối tượng InvitedCandidates
    private RowMapper<InvitedCandidates> rowMapper = new RowMapper<InvitedCandidates>() {
        @Override
        public InvitedCandidates mapRow(ResultSet rs, int rowNum) throws SQLException {
            InvitedCandidates invitedCandidate = new InvitedCandidates();
            invitedCandidate.setId(rs.getInt("id"));
            invitedCandidate.setEmployerId(rs.getInt("employer_id"));
            invitedCandidate.setCandidateId(rs.getInt("candidate_id"));
            invitedCandidate.setInvitedAt(rs.getDate("invited_at").toLocalDate());
            return invitedCandidate;
        }
    };

    public Integer add(InvitedCandidates invitedCandidate) {
        String sql = "INSERT INTO invited_candidates (employer_id, candidate_id, invited_at) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"}); // "id" là tên cột khóa chính
            ps.setInt(1, invitedCandidate.getEmployerId());
            ps.setInt(2, invitedCandidate.getCandidateId());
            ps.setDate(3, Date.valueOf(invitedCandidate.getInvitedAt()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public boolean update(InvitedCandidates invitedCandidate) {
        String sql = "UPDATE invited_candidates SET employer_id = ?, candidate_id = ?, invited_at = ? WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql,
                invitedCandidate.getEmployerId(),
                invitedCandidate.getCandidateId(),
                Date.valueOf(invitedCandidate.getInvitedAt()),
                invitedCandidate.getId());
        return affectedRows > 0;
    }

    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM invited_candidates WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);
        return affectedRows > 0;
    }
    
    public InvitedCandidates findById(Integer id) {
        String sql = "SELECT * FROM invited_candidates WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<InvitedCandidates> findAll() {
        String sql = "SELECT * FROM invited_candidates";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<InvitedCandidates> findByEmployerId(Integer employerId) {
        String sql = "SELECT * FROM invited_candidates WHERE employer_id = ?";
        return jdbcTemplate.query(sql, rowMapper, employerId);
    }

    public List<InvitedCandidates> findByCandidateId(Integer candidateId) {
        String sql = "SELECT * FROM invited_candidates WHERE candidate_id = ?";
        return jdbcTemplate.query(sql, rowMapper, candidateId);
    }

    
    public InvitedCandidates findByEmployerAndCandidateId(Integer employerId, Integer candidateId) {
        String sql = "SELECT * FROM invited_candidates WHERE employer_id = ? AND candidate_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, employerId, candidateId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null; 
        }
    }
}
