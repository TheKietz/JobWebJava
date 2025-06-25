package com.job.repository;

import com.job.model.Job;
import com.job.enums.CommonEnums.JobStatus;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JobRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private final RowMapper<Job> jobRowMapper = (rs, rowNum) -> {
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
    };

    public List<Job> findAll() {
        String sql = "SELECT * FROM jobs";
        List<Job> jobs = jdbcTemplate.query(sql, jobRowMapper);

        return jobs;
    }

    public Job findByID(int id) {
        String sql = "SELECT * FROM jobs WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, jobRowMapper, id);
        } catch (Exception e) {
            logger.error("Error finding job by ID: {}, {}", id, e.getMessage());
            return null;
        }
    }

    public List<Job> findByEmployerID(int userId) {
        String sql = "SELECT title, job_type, status FROM jobs WHERE employer_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Job job = new Job();
            job.setTitle(rs.getString("title"));
            job.setJobType(rs.getString("job_type"));
            job.setStatus(JobStatus.valueOf(rs.getString("status")));
            return job;
        }, userId);
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

    public List<Job> topTenJob() {
        String sql = """
                   SELECT j.title, j.category, COUNT(a.id) AS total_applications
                   FROM jobs j
                   LEFT JOIN applications a ON j.id = a.job_id
                   GROUP BY j.id, j.title, j.category
                   ORDER BY total_applications DESC
                   LIMIT 10;
                   """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Job job = new Job();
            job.setTitle(rs.getString("title"));
            job.setCategory(rs.getString("category"));
            job.setTotalApplications(rs.getInt("total_applications")); // Bạn cần có field này trong Job
            return job;
        });
    }

    public int countJobByEmpID(Integer id) {
        String sql = "SELECT count(*) FROM jobs WHERE employer_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null ? count : 0;
    }

    public List<Job> search(String keyword) {
        try {
            if (keyword == null || keyword.isBlank()) {
                return findAll();
            }
            String sql = "SELECT * FROM jobs WHERE LOWER(title) LIKE ? OR LOWER(category) LIKE ?";
            String likeKeyword = "%" + keyword.trim().toLowerCase() + "%";
            return jdbcTemplate.query(sql, new Object[]{likeKeyword, likeKeyword}, jobRowMapper);
        } catch (Exception e) {
            logger.error("Error searching jobs with keyword: {}", keyword, e);
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

//    public List<Job> searchByFilters(List<String> categories, List<String> jobTypes, List<String> salaryRanges) {
//        StringBuilder sql = new StringBuilder("SELECT * FROM jobs WHERE 1=1 ");
//        List<Object> params = new ArrayList<>();
//
//        if (categories != null && !categories.isEmpty()) {
//            sql.append("AND category IN (")
//                    .append(String.join(",", Collections.nCopies(categories.size(), "?")))
//                    .append(") ");
//            params.addAll(categories);
//        }
//
//        if (jobTypes != null && !jobTypes.isEmpty()) {
//            sql.append("AND job_type IN (")
//                    .append(String.join(",", Collections.nCopies(jobTypes.size(), "?")))
//                    .append(") ");
//            params.addAll(jobTypes);
//        }
//
//        if (salaryRanges != null && !salaryRanges.isEmpty()) {
//            for (String range : salaryRanges) {
//                String[] parts = range.split("-");
//                if (parts.length == 2) {
//                    try {
//                        BigDecimal min = new BigDecimal(parts[0]);
//                        BigDecimal max = new BigDecimal(parts[1]);
//                        sql.append("AND salary_min >= ? AND salary_max <= ? ");
//                        params.add(min);
//                        params.add(max);
//                    } catch (NumberFormatException ignored) {
//                    }
//                }
//            }
//        }
//
//        System.out.println("Final SQL: " + sql.toString());
//        return jdbcTemplate.query(sql.toString(), params.toArray(), jobRowMapper);
//    }

    public RowMapper<Job> getRowMapper() {
        return jobRowMapper;
    }
}
