package com.job.repository;

import com.job.dto.CandidateApplicationDTO;
import com.job.enums.CommonEnums.ApplicationStatus;
import com.job.model.Application;
import com.job.model.Job;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
//Long id, Long candidateId, Long jobId, String resumeUrl, ApplicationStatus status, BigDecimal score, LocalDateTime appliedAt
    public List<Application> findAll() {
        String sql = "SELECT * FROM applications";
        List<Application> applications = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Application.class));
        System.out.println("findAll: Retrieved " + applications.size() + " applications");
        return applications;
    }

    public List<CandidateApplicationDTO> findCandidatesAppliedToEmployerJobs(int employerUserId) {
        String sql = """
            SELECT 
                u.full_name, 
                j.title AS job_title, 
                a.resume_url, 
                u.avatar_url  
            FROM applications a
            JOIN jobs j ON a.job_id = j.id
            JOIN candidates c ON a.candidate_id = c.id 
            JOIN users u ON u.id = c.user_id
            JOIN employers e ON e.id = j.employer_id
            WHERE e.user_id = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{employerUserId}, (rs, rowNum) -> 
            new CandidateApplicationDTO(
                rs.getString("full_name"),
                rs.getString("job_title"),
                rs.getString("resume_url"),
                rs.getString("avatar_url")
            )
        );
    }

    public List<Application> findByCandidateId(Integer candidateId) {
        String sql = """
            SELECT 
                a.*, 
                j.id AS job_id, j.title, j.location, j.salary_min, j.salary_max, j.expired_at 
            FROM applications a
            JOIN jobs j ON a.job_id = j.id
            WHERE a.candidate_id = ?
            ORDER BY a.applied_at DESC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Application app = new Application();
            app.setId(rs.getInt("id"));
            app.setCandidateId(rs.getInt("candidate_id"));
            app.setJobId(rs.getInt("job_id"));
            app.setResumeUrl(rs.getString("resume_url"));
            app.setStatus(ApplicationStatus.valueOf(rs.getString("status")));
            app.setScore(rs.getBigDecimal("score"));
            app.setAppliedAt(rs.getTimestamp("applied_at").toLocalDateTime());

            Job job = new Job();
            job.setId(rs.getInt("job_id"));
            job.setTitle(rs.getString("title"));
            job.setLocation(rs.getString("location"));
            job.setSalaryMin(rs.getBigDecimal("salary_min"));
            job.setSalaryMax(rs.getBigDecimal("salary_max"));
            job.setExpiredAt(rs.getTimestamp("expired_at").toLocalDateTime());

            app.setJob(job);
            return app;
        }, candidateId);
    }
    public Application findByID(Integer applicationID) {
        String sql = "SELECT * FROM applications WHERE id = ?";
        List<Application> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Application.class), applicationID);
        System.out.println("findByID: id=" + applicationID + ", Found=" + (list.isEmpty() ? "null" : list.get(0).getStatus()));
        return list.isEmpty() ? null : list.get(0);
    }

    public void add(Application application) {
        String sql = "INSERT INTO applications (candidate_id, job_id, resume_url, status, score, applied_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                application.getCandidateId(),
                application.getJobId(),
                application.getResumeUrl(),
                application.getStatus(),
                application.getScore(),
                application.getAppliedAt() != null ? Timestamp.valueOf(application.getAppliedAt()) : null);
    }

    public void update(Application application) {
        String sql = "UPDATE applications SET candidate_id = ?, job_id = ?, resume_rrl = ?,status = ?,score = ?, applied_at = ?,  WHERE id = ?";
        jdbcTemplate.update(sql,
                application.getCandidateId(),
                application.getJobId(),
                application.getResumeUrl(),
                application.getStatus(),
                application.getScore(),
                application.getAppliedAt() != null ? Timestamp.valueOf(application.getAppliedAt()) : null);
    }

    public void deleteByID(Integer id) {
        String sql = "DELETE FROM applications WHERE id = ?";
        jdbcTemplate.update(sql, id);
        System.out.println("deleteByID: Deleted application with id=" + id);
    }

    public List<Application> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String sql = "SELECT * FROM applications WHERE LOWER(Status) LIKE ?";
        String like = "%" + keyword.trim().toLowerCase() + "%";
        List<Application> applications = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Application.class), like);
        System.out.println("search: Keyword='" + keyword + "', Found " + applications.size() + " applications");
        return applications;
    }

    public List<Application> getPage(List<Application> list, int page, int size) {
        if (list.isEmpty()) {
            System.out.println("getPage: Empty application list");
            return List.of();
        }
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) {
            System.out.println("getPage: Invalid page range, from=" + from + ", list size=" + list.size());
            return List.of();
        }
        List<Application> pagedapplications = list.subList(from, to);
        System.out.println("getPage: Page=" + page + ", Size=" + size + ", Returned " + pagedapplications.size() + " applications");
        return pagedapplications;
    }

    public int countPages(List<Application> list, int size) {
        int pages = (int) Math.ceil((double) list.size() / Math.max(1, size));
        System.out.println("countPages: List size=" + list.size() + ", Size=" + size + ", Pages=" + pages);
        return pages;
    }
    public boolean hasApplied(Integer candidateId, Integer jobId) {
        String sql = "SELECT COUNT(*) FROM applications WHERE candidate_id = ? AND job_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, candidateId, jobId);
        return count != null && count > 0;
    }
}