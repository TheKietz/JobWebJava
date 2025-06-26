/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service;

import com.job.service.client.*;
import com.job.model.Feedback;
import com.job.repository.FeedbackRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 11090
 */
@Service
public class FeedbackAdminService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> findAll(){
        
        return feedbackRepository.findAll();
    }
    
    public Feedback findByID(Integer jobID) {
        return feedbackRepository.findById(jobID);
    }
    
    public void add(Feedback job) {
        feedbackRepository.add(job);
    }
    public List<Feedback> getPage(List<Feedback> list, int page, int size) {
        return feedbackRepository.getPage(list, page, size);
    }
    public List<Feedback> search(String keyword) {
        return feedbackRepository.search(keyword);
    }
    public int countPages(List<Feedback> list, int size) {
        return feedbackRepository.countPages(list, size);
    }
}
