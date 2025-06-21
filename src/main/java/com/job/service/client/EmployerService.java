/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service.client;

import com.job.model.Employer;
import com.job.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    public List<Employer> findAll() {
        return employerRepository.findAll();
    }

    public Employer findByID(Integer id) {
        return employerRepository.findByID(id);
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


