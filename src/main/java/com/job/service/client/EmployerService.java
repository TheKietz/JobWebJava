/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service.client;

import com.job.model.Employer;
import com.job.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 11090
 */
@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;
    public Employer findByUserID(Integer userID) {
        return employerRepository.findByUserID(userID);
    }
}
