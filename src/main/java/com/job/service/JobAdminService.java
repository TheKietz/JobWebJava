package com.job.service;

import com.job.model.Job;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobAdminService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Job> findAll() {
        String sql = "SELECT * FROM Jobs";
        List<Job> jobs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Job.class));
        System.out.println("findAll: Retrieved " + jobs.size() + " jobs");
        return jobs;
    }

    public Job findByID(int jobID) {
        String sql = "SELECT * FROM Jobs WHERE JobID = ?";
        List<Job> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Job.class), jobID);
        System.out.println("findByID: JobID=" + jobID + ", Found=" + (list.isEmpty() ? "null" : list.get(0).getTitle()));
        return list.isEmpty() ? null : list.get(0);
    }

    public void add(Job job) {
        String sql = "INSERT INTO Jobs (employerID, title, description, location, jobType, salaryRange, postedAt, expiryDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                job.getEmployerID(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getJobType(),
                job.getSalaryRange(),
                java.sql.Date.valueOf(job.getPostedAt()),
                java.sql.Date.valueOf(job.getExpiryDate()));
        System.out.println("add: Added job with title=" + job.getTitle());
    }

    public void update(Job job) {
        String sql = "UPDATE Jobs SET employerID = ?, title = ?, description = ?, location = ?, jobType = ?, salaryRange = ?, postedAt = ?, expiryDate = ? WHERE JobID = ?";
        jdbcTemplate.update(sql,
                job.getEmployerID(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getJobType(),
                job.getSalaryRange(),
                java.sql.Date.valueOf(job.getPostedAt()),
                java.sql.Date.valueOf(job.getExpiryDate()),
                job.getJobID());
        System.out.println("update: Updated job with JobID=" + job.getJobID());
    }

    public void deleteByID(int id) {
        String sql = "DELETE FROM Jobs WHERE JobID = ?";
        jdbcTemplate.update(sql, id);
        System.out.println("deleteByID: Deleted job with JobID=" + id);
    }

    public List<Job> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String sql = "SELECT * FROM Jobs WHERE LOWER(title) LIKE ?";
        String like = "%" + keyword.trim().toLowerCase() + "%";
        List<Job> jobs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Job.class), like);
        System.out.println("search: Keyword='" + keyword + "', Found " + jobs.size() + " jobs");
        return jobs;
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