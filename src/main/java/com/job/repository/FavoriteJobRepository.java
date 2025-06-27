package com.job.repository;

import com.job.model.FavoriteJob;
import com.job.model.Job;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteJobRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JobRepository jobRepository; // 👈 Inject để tái dùng rowMapper

    private final RowMapper<FavoriteJob> rowMapper = (rs, rowNum) -> {
        FavoriteJob fav = new FavoriteJob();
        fav.setId(rs.getInt("id"));
        fav.setCandidateId(rs.getInt("candidate_id"));
        fav.setJobId(rs.getInt("job_id"));
        fav.setSavedAt(rs.getTimestamp("saved_at").toLocalDateTime());
        return fav;
    };

    public void save(int candidateId, int jobId) {
        String sql = "INSERT INTO favorite_jobs (candidate_id, job_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE saved_at = NOW()";
        jdbcTemplate.update(sql, candidateId, jobId);
    }

    public List<Job> findByCandidateId(int candidateId) {
        String sql = """
            SELECT j.* FROM jobs j
            INNER JOIN favorite_jobs f ON f.job_id = j.id
            WHERE f.candidate_id = ?
            ORDER BY f.saved_at DESC
        """;

        return jdbcTemplate.query(sql, jobRepository.getRowMapper(), candidateId); 
    }

    public void remove(int candidateId, int jobId) {
        String sql = "DELETE FROM favorite_jobs WHERE candidate_id = ? AND job_id = ?";
        jdbcTemplate.update(sql, candidateId, jobId);
    }

    public boolean exists(int candidateId, int jobId) {
        String sql = "SELECT COUNT(*) FROM favorite_jobs WHERE candidate_id = ? AND job_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, candidateId, jobId);
        return count != null && count > 0;
    }
}
