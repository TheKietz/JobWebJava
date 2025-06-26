/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.repository;

import com.job.enums.CommonEnums;
import com.job.enums.CommonEnums.BannerPosition;
import com.job.enums.CommonEnums.Status;
import com.job.model.Banner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 11090
 */
@Repository
public class BannerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
// Row mapper

    private Banner mapRow(ResultSet rs, int rowNum) throws SQLException {
        Banner banner = new Banner();
        banner.setId(rs.getInt("id"));
        banner.setTitle(rs.getString("title"));
        banner.setImageUrl(rs.getString("image_url"));
        banner.setLinkUrl(rs.getString("link_url"));
        String position = rs.getString("position");
        banner.setPosition(position != null ? BannerPosition.valueOf(position) : BannerPosition.HOMEPAGE_TOP);    
        String status = rs.getString("status");
        banner.setStatus(status != null ? Status.valueOf(status) : Status.ACTIVE);
        banner.setStartDate(rs.getDate("start_date").toLocalDate());
        banner.setEndDate(rs.getDate("end_date").toLocalDate());
        return banner;
    }
// READ ALL

    public List<Banner> findAll() {
        String sql = "SELECT * FROM banners";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    // READ BY ID
    public Banner findById(int id) {
        String sql = "SELECT * FROM banners WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRow);
    }

    // CREATE
    public void save(Banner banner) {
        String sql = "INSERT INTO banners (title, image_url, link_url, position, status, start_date, end_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                banner.getTitle(),
                banner.getImageUrl(),
                banner.getLinkUrl(),
                banner.getPosition().name(),
                banner.getStatus().name(),
                banner.getStartDate(),
                banner.getEndDate());
    }

    // UPDATE
    public void update(Banner banner) {
        String sql = "UPDATE banners SET title = ?, image_url = ?, link_url = ?, position = ?, status = ?, start_date = ?, end_date = ? WHERE id = ?";
         jdbcTemplate.update(sql,
                banner.getTitle(),
                banner.getImageUrl(),
                banner.getLinkUrl(),
                banner.getPosition().name(),
                banner.getStatus().name(),
                banner.getStartDate(),
                banner.getEndDate(),
                banner.getId());
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM banners WHERE id = ?";
         jdbcTemplate.update(sql, id);
    }

    // FIND ACTIVE BANNERS BY POSITION
    public List<Banner> findActiveByPosition(CommonEnums.BannerPosition position) {
        String sql = "SELECT * FROM banners "
                + "WHERE position = ? AND status = 'ACTIVE' "
                + "AND start_date <= CURDATE() AND end_date >= CURDATE()";
        return jdbcTemplate.query(sql, new Object[]{position.name()}, this::mapRow);
    }

    public List<Banner> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String sql = "SELECT * FROM applications WHERE LOWER(Status) LIKE ?";
        String like = "%" + keyword.trim().toLowerCase() + "%";
        List<Banner> applications = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Banner.class), like);
        System.out.println("search: Keyword='" + keyword + "', Found " + applications.size() + " applications");
        return applications;
    }

    public List<Banner> getPage(List<Banner> list, int page, int size) {
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
        List<Banner> pagedapplications = list.subList(from, to);
        System.out.println("getPage: Page=" + page + ", Size=" + size + ", Returned " + pagedapplications.size() + " applications");
        return pagedapplications;
    }

    public int countPages(List<Banner> list, int size) {
        int pages = (int) Math.ceil((double) list.size() / Math.max(1, size));
        System.out.println("countPages: List size=" + list.size() + ", Size=" + size + ", Pages=" + pages);
        return pages;
    }
}
