package com.job.repository;

import com.job.model.ServicePackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicePackageRepository {
    private static final Logger logger = LoggerFactory.getLogger(ServicePackageRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ServicePackage> rowMapper = (rs, rowNum) -> {
        ServicePackage sp = new ServicePackage();
        sp.setId(rs.getInt("id"));
        sp.setName(rs.getString("name"));
        sp.setPrice(rs.getBigDecimal("price"));
        sp.setDurationDays(rs.getInt("duration_days"));
        sp.setJobPostLimit(rs.getInt("job_post_limit"));
        sp.setResumeAccess(rs.getBoolean("resume_access"));
        sp.setDescription(rs.getString("description"));
        return sp;
    };

    public List<ServicePackage> findAll() {
        String sql = "SELECT * FROM service_packages";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public ServicePackage findById(Integer id) {
        String sql = "SELECT * FROM service_packages WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (Exception e) {
            logger.error("Error finding service package by ID {}: {}", id, e.getMessage());
            return null;
        }
    }
//    public void add(ServicePackage servicePackage){
//        String sql = "Inser into service_packages(name, price, duration_date,job_post_limit, resume_access, description) VALUES()";
//        jdbcTemplate.update(sql,
//                    job.getEmployerId(),
//                    job.getTitle(),
//                    job.getDescription(),
//                    job.getLocation(),
//                    job.getSalaryMin(),
//                    job.getSalaryMax(),
//                    job.getJobType(),
//                    job.getStatus() != null ? job.getStatus().name() : "OPEN",
//                    job.getCategory(),
//                    job.getCreatedAt() != null ? Timestamp.valueOf(job.getCreatedAt()) : null,
//                    job.getExpiredAt() != null ? Timestamp.valueOf(job.getExpiredAt()) : null);
//    }
    public void save(ServicePackage sp) {
        String sql = "INSERT INTO service_packages (name, price, duration_days, job_post_limit, resume_access, description) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, sp.getName());
            ps.setBigDecimal(2, sp.getPrice());
            ps.setInt(3, sp.getDurationDays());
            ps.setInt(4, sp.getJobPostLimit());
            ps.setBoolean(5, sp.getResumeAccess() != null && sp.getResumeAccess());
            ps.setString(6, sp.getDescription());
            return ps;
        }, keyHolder);
        sp.setId(keyHolder.getKey().intValue());
        logger.info("Saved service package with ID {}", sp.getId());
    }

    public void update(ServicePackage sp) {
        String sql = "UPDATE service_packages SET name = ?, price = ?, duration_days = ?, job_post_limit = ?, resume_access = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                sp.getName(),
                sp.getPrice(),
                sp.getDurationDays(),
                sp.getJobPostLimit(),
                sp.getResumeAccess(),
                sp.getDescription(),
                sp.getId());
        logger.info("Updated service package with ID {}", sp.getId());
    }

    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM service_packages WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        logger.info("Deleted service package ID {}, rows affected: {}", id, rows);
        return rows > 0;
    }

    public List<ServicePackage> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String sql = "SELECT * FROM service_packages WHERE LOWER(name) LIKE ? OR LOWER(description) LIKE ?";
        String like = "%" + keyword.toLowerCase() + "%";
        return jdbcTemplate.query(sql, new Object[]{like, like}, rowMapper);
    }

    public List<ServicePackage> getPage(List<ServicePackage> list, int page, int size) {
        if (list == null || list.isEmpty()) return new ArrayList<>();
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) return new ArrayList<>();
        return list.subList(from, to);
    }

    public int countPages(List<ServicePackage> list, int size) {
        if (list == null || list.isEmpty()) return 0;
        return (int) Math.ceil((double) list.size() / size);
    }
} 
