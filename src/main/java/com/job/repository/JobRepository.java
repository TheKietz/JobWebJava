
package com.job.repository;

import com.job.model.Job;
import com.job.enums.CommonEnums.JobStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JobRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Job> jobRowMapper = new RowMapper<Job>() {
        @Override
        public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
            Job job = new Job();
            job.setId(rs.getInt("id"));
            job.setEmployerId(rs.getInt("employer_id"));
            job.setTitle(rs.getString("title"));
            job.setDescription(rs.getString("description"));
            job.setLocation(rs.getString("location"));
            job.setSalaryMin(rs.getBigDecimal("salary_min"));
            job.setSalaryMax(rs.getBigDecimal("salary_max"));
            job.setJobType(rs.getString("job_type"));
            String status = rs.getString("status");
            job.setStatus(status != null ? JobStatus.valueOf(status) : JobStatus.APPROVED);
            job.setCategory(rs.getString("category"));
            Timestamp createdAt = rs.getTimestamp("created_at");
            job.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);
            Timestamp expiredAt = rs.getTimestamp("expired_at");
            job.setExpiredAt(expiredAt != null ? expiredAt.toLocalDateTime() : null);
            return job;
        }
    };

    public List<Job> findAll() {
        String sql = "SELECT * FROM jobs";
        try {
            List<Job> jobs = jdbcTemplate.query(sql, jobRowMapper);
            System.out.println("findAll: Retrieved " + jobs.size() + " jobs");
            return jobs;
        } catch (Exception e) {
            System.err.println("Error fetching all jobs: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    public Job findByID(int jobID) {
        String sql = "SELECT * FROM jobs WHERE id = ?";
        try {
            Job job = jdbcTemplate.queryForObject(sql, jobRowMapper, jobID);
            System.out.println("findByID: JobID=" + jobID + ", Found=" + (job != null ? job.getTitle() : "null"));
            return job;
        } catch (Exception e) {
            System.err.println("Error finding job by ID: " + jobID + ", " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void add(Job job) {
        String sql = "INSERT INTO jobs (employer_id, title, description, location, salary_min, salary_max, job_type, status, category, created_at, expired_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    job.getEmployerId(),
                    job.getTitle(),
                    job.getDescription(),
                    job.getLocation(),
                    job.getSalaryMin(),
                    job.getSalaryMax(),
                    job.getJobType(),
                    job.getStatus() != null ? job.getStatus().name() : "OPEN",
                    job.getCategory(),
                    job.getCreatedAt() != null ? Timestamp.valueOf(job.getCreatedAt()) : null,
                    job.getExpiredAt() != null ? Timestamp.valueOf(job.getExpiredAt()) : null);
            System.out.println("Added job: title=" + job.getTitle());
        } catch (Exception e) {
            System.err.println("Error adding job: " + e.getMessage());
            throw e;
        }
    }

    public void update(Job job) {
        String sql = "UPDATE jobs SET employer_id = ?, title = ?, description = ?, location = ?, salary_min = ?, salary_max = ?, job_type = ?, status = ?, category = ?, created_at = ?, expired_at = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql,
                    job.getEmployerId(),
                    job.getTitle(),
                    job.getDescription(),
                    job.getLocation(),
                    job.getSalaryMin(),
                    job.getSalaryMax(),
                    job.getJobType(),
                    job.getStatus() != null ? job.getStatus().name() : "OPEN",
                    job.getCategory(),
                    job.getCreatedAt() != null ? Timestamp.valueOf(job.getCreatedAt()) : null,
                    job.getExpiredAt() != null ? Timestamp.valueOf(job.getExpiredAt()) : null,
                    job.getId());
            System.out.println("Updated job: id=" + job.getId());
        } catch (Exception e) {
            System.err.println("Error updating job: " + e.getMessage());
            throw e;
        }
    }

    public boolean deleteByID(int id) {
        String sql = "DELETE FROM jobs WHERE id = ?";
        try {
            int rows = jdbcTemplate.update(sql, id);
            System.out.println("Deleted job ID: " + id + ", rows affected: " + rows);
            return rows > 0;
        } catch (Exception e) {
            System.err.println("Error deleting job by ID: " + id + ", " + e.getMessage());
            return false;
        }
    }

    public List<Job> search(String keyword) {
        try {
            if (keyword == null || keyword.isBlank()) {
                return findAll();
            }
            String sql = "SELECT * FROM jobs WHERE LOWER(title) LIKE ? OR LOWER(category) LIKE ?";
            String like = "%" + keyword.trim().toLowerCase() + "%";
            List<Job> jobs = jdbcTemplate.query(sql, jobRowMapper, like, like);
            System.out.println("search: Keyword='" + keyword + "', Found " + jobs.size() + " jobs");
            return jobs;
        } catch (Exception e) {
            System.err.println("Error searching jobs with keyword: " + keyword + ", " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Job> getPage(List<Job> list, int page, int size) {
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
        List<Job> pagedJobs = list.subList(from, to);
        System.out.println("getPage: Page=" + page + ", Size=" + size + ", Returned " + pagedJobs.size() + " jobs");
        return pagedJobs;
    }

    public int countPages(List<Job> list, int size) {
        int pages = (int) Math.ceil((double) list.size() / Math.max(1, size));
        System.out.println("countPages: List size=" + list.size() + ", Size=" + size + ", Pages=" + pages);
        return pages;
    }
}
