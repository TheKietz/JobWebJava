package com.job.repository;

import com.job.model.Employer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EmployerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

     private final RowMapper<Employer> employerRowMapper = (rs, rowNum) -> {
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
    };
    public List<Employer> findAll() {
        String sql = "SELECT * FROM employers";
        return jdbcTemplate.query(sql, employerRowMapper);
    }
    public Employer findByID(Integer id) {
        String sql = "SELECT * FROM employers WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, employerRowMapper);
        } catch (Exception e) {
            System.err.println("Error finding employer by ID: " + id + ", " + e.getMessage());
            return null;
        }
    }

    public Employer findByUserID(Integer userID) {
        String sql = "SELECT * FROM employers WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userID}, employerRowMapper);
        } catch (Exception e) {
            System.err.println("Error finding employer by userID: " + userID + ", " + e.getMessage());
            return null;
        }
    }

    public void add(Employer employer) {
        String sql = "INSERT INTO employers (user_id, company_name, company_size, address, website, description, logo_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                employer.getUserId(),
                employer.getCompanyName(),
                employer.getCompanySize(),
                employer.getAddress(),
                employer.getWebsite(),
                employer.getDescription(),
                employer.getLogoUrl());
    }

    public void update(Employer employer) {
        String sql = "UPDATE employers SET user_id = ?, company_name = ?, company_size = ?, address = ?, website = ?, description = ?, logo_url = ? WHERE id = ?";
        try {
            System.out.println("Updating employer: id=" + employer.getId() + ", user_id=" + employer.getUserId());
            jdbcTemplate.update(sql,
                    employer.getUserId(),
                    employer.getCompanyName(),
                    employer.getCompanySize(),
                    employer.getAddress(),
                    employer.getWebsite(),
                    employer.getDescription(),
                    employer.getLogoUrl(),
                    employer.getId());
        } catch (Exception e) {
            System.err.println("Error updating employer: " + e.getMessage());
            throw e;
        }
    }

    public boolean deleteByID(Integer id) {
        String sql = "DELETE FROM employers WHERE id = ?";
        try {
            int rows = jdbcTemplate.update(sql, id);
            System.out.println("Deleted employer ID: " + id + ", rows affected: " + rows);
            return rows > 0;
        } catch (Exception e) {
            System.err.println("Error deleting employer by ID: " + id + ", " + e.getMessage());
            return false;
        }
    }

    public List<Employer> search(String keyword) {
         try {
            if (keyword == null || keyword.isBlank()) {
                return findAll();
            }
            String sql = "SELECT * FROM employers WHERE company_name LIKE ? OR website LIKE ?";
            String like = "%" + keyword.trim() + "%";
            return jdbcTemplate.query(sql, new Object[]{like, like}, employerRowMapper);
        } catch (Exception e) {
            System.err.println("Error searching employers with keyword: " + keyword + ", " + e.getMessage());
            return List.of();
        }
    }

    public List<Employer> getPage(List<Employer> list, int page, int size) {
        if (list.isEmpty()) return List.of();
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) return List.of();
        return list.subList(from, to);
    }

    public int countPages(List<Employer> list, int size) {
        return (int) Math.ceil((double) list.size() / size);
    }
}
