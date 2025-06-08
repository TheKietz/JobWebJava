package com.job.service;

import com.job.model.Application;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ApplicationAdminService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Application> findAll() {
        String sql = "SELECT * FROM Applications";
        List<Application> applications = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Application.class));
        System.out.println("findAll: Retrieved " + applications.size() + " applications");
        return applications;
    }

    public Application findByID(int applicationID) {
        String sql = "SELECT * FROM Applications WHERE ApplicationID = ?";
        List<Application> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Application.class), applicationID);
        System.out.println("findByID: ApplicationID=" + applicationID + ", Found=" + (list.isEmpty() ? "null" : list.get(0).getStatus()));
        return list.isEmpty() ? null : list.get(0);
    }

    public void add(Application application) {
        String sql = "INSERT INTO Applications (CandidateID, JobID, CoverLetter, ResumeUrl, AppliedAt, Status) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                application.getCandidateID(),
                application.getJobID(),
                application.getCoverLetter(),
                application.getResumeUrl(),
                java.sql.Date.valueOf(application.getAppliedAt()),
                application.getStatus());
        System.out.println("add: Added application with Status=" + application.getStatus());
    }

    public void update(Application application) {
        String sql = "UPDATE Applications SET CandidateID = ?, JobID = ?, CoverLetter = ?, ResumeUrl = ?, AppliedAt = ?, Status = ? WHERE ApplicationID = ?";
        jdbcTemplate.update(sql,
                application.getCandidateID(),
                application.getJobID(),
                application.getCoverLetter(),
                application.getResumeUrl(),
                java.sql.Date.valueOf(application.getAppliedAt()),
                application.getStatus(),
                application.getApplicationID());
        System.out.println("update: Updated application with ApplicationID=" + application.getApplicationID());
    }

    public void deleteByID(int id) {
        String sql = "DELETE FROM Applications WHERE ApplicationID = ?";
        jdbcTemplate.update(sql, id);
        System.out.println("deleteByID: Deleted application with ApplicationID=" + id);
    }

    public List<Application> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String sql = "SELECT * FROM Applications WHERE LOWER(Status) LIKE ?";
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
        List<Application> pagedApplications = list.subList(from, to);
        System.out.println("getPage: Page=" + page + ", Size=" + size + ", Returned " + pagedApplications.size() + " applications");
        return pagedApplications;
    }

    public int countPages(List<Application> list, int size) {
        int pages = (int) Math.ceil((double) list.size() / Math.max(1, size));
        System.out.println("countPages: List size=" + list.size() + ", Size=" + size + ", Pages=" + pages);
        return pages;
    }
}