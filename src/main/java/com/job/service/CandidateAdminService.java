
package com.job.service;

import com.job.model.Candidate;
import com.job.repository.CandidateRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for managing Candidate entities in the admin panel.
 */
@Service
public class CandidateAdminService {

    @Autowired
    private CandidateRepository candidateRepository;
    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Candidate findByID(Integer id) {
        return candidateRepository.findByID(id);
    }

    public Candidate findByUserID(Integer userID) {
        return candidateRepository.findByUserID(userID);
    }

    public void add(Candidate candidate) {
        candidateRepository.add(candidate);
    }

    public void update(Candidate candidate) {
        candidateRepository.update(candidate);
    }

    public boolean deleteByID(Integer id) {
        return candidateRepository.deleteByID(id);
    }

    public int countCandidate() {
        return candidateRepository.countCandidates();
    }

    public int countCandidateByDateRange(LocalDate from, LocalDate to) {
        return candidateRepository.countCandidatesByDateRange(from, to);
    }
    
    public List<Candidate> search(String keyword) {
        return candidateRepository.search(keyword);
    }

    public List<Candidate> getPage(List<Candidate> list, int page, int size) {
        return candidateRepository.getPage(list, page, size);
    }

    public int countPages(List<Candidate> list, int size) {
        return candidateRepository.countPages(list, size);
    }

}
