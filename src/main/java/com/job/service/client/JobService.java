package com.job.service.client;

import com.job.model.Job;
import com.job.repository.JobRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getPage(List<Job> list, int page, int size) {
        return jobRepository.getPage(list, page, size);
    }

    public List<Job> findAll() {       
        return jobRepository.findAll();
    }

    public Job findByID(Integer jobID) {
        return jobRepository.findByID(jobID);
    }

    public void add(Job job) {
        jobRepository.add(job);
    }

    public void update(Job job) {
        jobRepository.update(job);
    }

    public void deleteByID(int id) {
        jobRepository.deleteByID(id);
    }

    public int countPages(List<Job> list, int size) {
        return jobRepository.countPages(list, size);
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