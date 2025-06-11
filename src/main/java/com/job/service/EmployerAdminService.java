package com.job.service;

import com.job.model.Employer;
import com.job.repository.EmployerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for managing Employer entities in the admin panel.
 */
@Service
public class EmployerAdminService {
    @Autowired
    private EmployerRepository employerRepository;

    public List<Employer> findAll() {
        return employerRepository.findAll();
    }

    public Employer findByID(Integer id) {
        return employerRepository.findByID(id);
    }

    public Employer findByUserID(Integer userID) {
        return employerRepository.findByUserID(userID);
    }

    public void add(Employer employer) {
        employerRepository.add(employer);
    }

    public void update(Employer employer) {
        employerRepository.update(employer);
    }

    public boolean deleteByID(Integer id) {
        return employerRepository.deleteByID(id);
    }

    public List<Employer> search(String keyword) {
        return employerRepository.search(keyword);
    }

    public List<Employer> getPage(List<Employer> list, int page, int size) {
        return employerRepository.getPage(list, page, size);
    }

    public int countPages(List<Employer> list, int size) {
        return employerRepository.countPages(list, size);
    }
}
