/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service;

import com.job.model.ServicePackage;
import com.job.repository.ServicePackageRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 11090
 */
@Service 
public class ServicePackageService {
    @Autowired
    private ServicePackageRepository servicePackageRepository;
    
    public List<ServicePackage> findAll() {
        return servicePackageRepository.findAll();
    }

    public ServicePackage findByID(Integer servicePackageID) {
        return servicePackageRepository.findById(servicePackageID);
    }

    public void add(ServicePackage servicePackage) {
       servicePackageRepository.save(servicePackage);
    }

    public void update(ServicePackage servicePackage) {
        servicePackageRepository.update(servicePackage);
    }

    public boolean deleteByID(Integer id) {
        return servicePackageRepository.deleteById(id);
    }

    public List<ServicePackage> search(String keyword) {
        return servicePackageRepository.search(keyword);
    }

    public List<ServicePackage> getPage(List<ServicePackage> list, int page, int size) {
        return servicePackageRepository.getPage(list, page, size);
    }

    public int countPages(List<ServicePackage> list, int size) {
       return servicePackageRepository.countPages(list, size);
    }
}
