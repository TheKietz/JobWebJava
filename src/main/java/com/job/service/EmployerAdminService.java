/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service;

import com.job.model.Employer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class EmployerAdminService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Employer> findAll() {
        String sql = "SELECT * FROM Employers";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employer.class));
    }

    public Employer findByID(int id) {
        String sql = "SELECT * FROM Employers WHERE EmployerID = ?";
        List<Employer> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employer.class), id);
        return list.isEmpty() ? null : list.get(0);
    }

    public Employer findByUserID(int userID) {
        String sql = "SELECT * FROM Employers WHERE UserID = ?";
        List<Employer> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employer.class), userID);
        return list.isEmpty() ? null : list.get(0);
    }

    public void add(Employer employer) {
        String sql = "INSERT INTO Employers (UserID, CompanyName, Website, Description, LogoUrl) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                employer.getUserID(),
                employer.getCompanyName(),
                employer.getWebsite(),
                employer.getDescription(),
                employer.getLogoUrl());
    }

    public void update(Employer employer) {
        String sql = "UPDATE Employers SET UserID = ?, CompanyName = ?, Website = ?, Description = ?, LogoUrl = ? WHERE EmployerID = ?";
        jdbcTemplate.update(sql,
                employer.getUserID(),
                employer.getCompanyName(),
                employer.getWebsite(),
                employer.getDescription(),
                employer.getLogoUrl(),
                employer.getEmployerID());
    }

    public void deleteByID(int id) {
        String sql = "DELETE FROM Employers WHERE EmployerID = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Employer> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String sql = "SELECT * FROM Employers WHERE CompanyName LIKE ? OR Website LIKE ?";
        String like = "%" + keyword.trim() + "%";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employer.class), like, like);
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