package com.job.repository;

import com.job.model.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FavoriteEmployerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Lưu công ty vào mục yêu thích
    public void save(int candidateId, int employerId) {
        String sql = "INSERT INTO favorite_employers (candidate_id, employer_id, saved_at) VALUES (?, ?, NOW())";
        jdbcTemplate.update(sql, candidateId, employerId);
    }

    // Xóa công ty khỏi danh sách yêu thích
    public void remove(int candidateId, int employerId) {
        String sql = "DELETE FROM favorite_employers WHERE candidate_id = ? AND employer_id = ?";
        jdbcTemplate.update(sql, candidateId, employerId);
    }

    // Lấy danh sách employer mà candidate đã lưu
    public List<Employer> findByCandidateId(int candidateId) {
        String sql = """
            SELECT e.* FROM employers e
            JOIN favorite_employers f ON f.employer_id = e.id
            WHERE f.candidate_id = ?
            ORDER BY f.saved_at DESC
        """;
        return jdbcTemplate.query(sql, new EmployerRowMapper(), candidateId);
    }

    // Kiểm tra xem đã lưu employer chưa
    public boolean isFavorited(int candidateId, int employerId) {
        String sql = "SELECT COUNT(*) FROM favorite_employers WHERE candidate_id = ? AND employer_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, candidateId, employerId);
        return count != null && count > 0;
    }

    // RowMapper cho Employer
    private static class EmployerRowMapper implements RowMapper<Employer> {
        @Override
        public Employer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employer employer = new Employer();
            employer.setId(rs.getInt("id"));
            employer.setUserId(rs.getInt("user_id"));
            employer.setCompanyName(rs.getString("company_name"));
            employer.setCompanySize(rs.getString("company_size"));
            employer.setAddress(rs.getString("address"));
            employer.setWebsite(rs.getString("website"));
            employer.setDescription(rs.getString("description"));
            employer.setLogoUrl(rs.getString("logo_url"));
            return employer;
        }
    }
}