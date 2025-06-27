package com.job.repository;

import com.job.model.Application;
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
        String sql = "UPDATE applications SET candidate_id = ?, job_id = ?, resume_url = ?,status = ?,score = ?, applied_at = ?,  WHERE id = ?";
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
        String sql = "SELECT * FROM applications WHERE LOWER(status) LIKE ?";
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

    public List<Application> findByCandidateId(Integer candidateId) {
        String sql = "SELECT * FROM applications WHERE candidate_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Application.class), candidateId);
    }

    public boolean hasApplied(Integer candidateId, Integer jobId) {
        String sql = "SELECT COUNT(*) FROM applications WHERE candidate_id = ? AND job_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, candidateId, jobId);
        return count != null && count > 0;
    }
}
