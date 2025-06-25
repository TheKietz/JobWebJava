package com.job.service;

import com.job.model.Job;
import com.job.repository.JobRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobAdminService {
    @Autowired
    private JobRepository jobRepository;

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

    public boolean deleteByID(Integer id) {
        return jobRepository.deleteByID(id);
    }

    public List<Job> topTenJob(){
        return jobRepository.topTenJob();
    }
    
    public List<Job> search(String keyword) {
        return jobRepository.search(keyword);
    }

    public List<Job> getPage(List<Job> list, int page, int size) {
        return jobRepository.getPage(list, page, size);
    }

    public int countPages(List<Job> list, int size) {
       return jobRepository.countPages(list, size);
    }
}