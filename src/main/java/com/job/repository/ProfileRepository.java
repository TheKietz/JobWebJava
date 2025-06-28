package com.job.repository;

import com.job.dto.CandidateProfileDTO;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CandidateProfileDTO findByUserId(int userId) {
        String sql = "SELECT u.id as user_id, u.full_name, u.email, u.phone, u.status, u.created_at, "
                + "c.resume_url, c.skills, c.location, c.experience_level "
                + "FROM users u LEFT JOIN candidates c ON u.id = c.user_id "
                + "WHERE u.id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rowNum) -> {
            CandidateProfileDTO profile = new CandidateProfileDTO();
            profile.setUserId(rs.getInt("user_id"));
            profile.setFullName(rs.getString("full_name"));
            profile.setEmail(rs.getString("email"));
            profile.setPhone(rs.getString("phone"));
            profile.setStatus(rs.getString("status"));
            profile.setCreatedAt(rs.getTimestamp("created_at"));
            Timestamp ts = rs.getTimestamp("created_at");
            profile.setCreatedAt(ts);

            if (ts != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formatted = ts.toLocalDateTime().format(formatter);
                profile.setCreatedAtFormatted(formatted);
            } else {
                profile.setCreatedAtFormatted("");
            }

            profile.setResumeUrl(rs.getString("resume_url"));
            profile.setSkills(rs.getString("skills"));
            profile.setLocation(rs.getString("location"));
            profile.setExperienceLevel(rs.getString("experience_level"));
            return profile;
        });
    }

    public void updateCandidate(CandidateProfileDTO profile) {
        String sqlUser = "UPDATE users SET full_name = ?, phone = ? WHERE id = ?";
        jdbcTemplate.update(sqlUser, profile.getFullName(), profile.getPhone(), profile.getUserId());

        String sqlCandidate = "UPDATE candidates SET skills = ?, location = ?, experience_level = ? WHERE user_id = ?";
        jdbcTemplate.update(sqlCandidate, profile.getSkills(), profile.getLocation(), profile.getExperienceLevel(), profile.getUserId());
    }

    public int deactivateUserById(int userId) {
        String sql = "UPDATE users SET status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, "UNACTIVE", userId);
    }
}