package com.job.service.client;

import com.job.model.Job;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Job> getPage(List<Job> list, int page, int size) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) {
            return new ArrayList<>();
        }
        return list.subList(from, to);
    }

    public List<Job> findAll() {
        String sql = "SELECT * FROM Jobs";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Job job = new Job();
            job.setJobID(rs.getInt("jobID"));
            job.setEmployerID(rs.getInt("employerID"));
            job.setTitle(rs.getString("title"));
            job.setDescription(rs.getString("description"));
            job.setLocation(rs.getString("location"));
            job.setJobType(rs.getString("jobType"));
            job.setSalaryRange(rs.getString("salaryRange"));
            job.setPostedAt(rs.getDate("postedAt"));
            job.setExpiryDate(rs.getDate("expiryDate"));
            return job;
        });
    }

    public Job findByID(int id) {
        String sql = "SELECT * FROM Jobs WHERE jobID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Job job = new Job();
                job.setJobID(rs.getInt("jobID"));
                job.setEmployerID(rs.getInt("employerID"));
                job.setTitle(rs.getString("title"));
                job.setDescription(rs.getString("description"));
                job.setLocation(rs.getString("location"));
                job.setJobType(rs.getString("jobType"));
                job.setSalaryRange(rs.getString("salaryRange"));
                job.setPostedAt(rs.getDate("postedAt"));
                job.setExpiryDate(rs.getDate("expiryDate"));
                return job;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void add(Job job) {
        String sql = "INSERT INTO Jobs (EmployerID, Title, Description, Location, JobType, SalaryRange, PostedAt, ExpiryDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                job.getEmployerID(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getJobType(),
                job.getSalaryRange(),
                new java.sql.Date(job.getPostedAt().getTime()),
                new java.sql.Date(job.getExpiryDate().getTime()));
    }

    public void update(Job job) {
        String sql = "UPDATE Jobs SET EmployerID = ?, Title = ?, Description = ?, Location = ?, JobType = ?, SalaryRange = ?, PostedAt = ?, ExpiryDate = ? WHERE JobID = ?";
        jdbcTemplate.update(sql,
                job.getEmployerID(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getJobType(),
                job.getSalaryRange(),
                new java.sql.Date(job.getPostedAt().getTime()),
                new java.sql.Date(job.getExpiryDate().getTime()),
                job.getJobID());
    }

    public void deleteByID(int id) {
        String sql = "DELETE FROM Jobs WHERE JobID = ?";
        jdbcTemplate.update(sql, id);
    }

    public int countPages(List<Job> list, int size) {
        return (int) Math.ceil((double) list.size() / Math.max(1, size));
    }
}

//   public List<Job> search(String keyword) {
//        if (keyword == null || keyword.isBlank()) {
//            return findAll();
//        }
//        String sql = "SELECT * FROM users WHERE LOWER(fullname) LIKE ? OR LOWER(email) LIKE ?";
//        String likeKeyword = "%" + keyword.toLowerCase() + "%";
//        return jdbcTemplate.query(sql,
//                new Object[]{likeKeyword, likeKeyword},
//                (rs, rowNum) -> new Job(
//                    rs.getInt("userid"),
//                    rs.getString("fullname"),
//                    rs.getString("email"),
//                    rs.getString("passwordhash"),
//                    rs.getString("role"),
//                    rs.getDate("createdat").toLocalDate()
//                ));
//    }   
