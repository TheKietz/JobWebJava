package com.job.service;

import com.job.model.CV;
import com.job.repository.CVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CVService {

    @Autowired
    private CVRepository cvRepository;

    public List<CV> getByCandidateId(int candidateId) {
        return cvRepository.findByCandidateId(candidateId);
    }

    public void create(CV cv) {
        cvRepository.save(cv);
    }

    public void delete(int id) {
        cvRepository.delete(id);
    }
}