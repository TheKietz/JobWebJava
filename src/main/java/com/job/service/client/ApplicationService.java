package com.job.service.client;

import com.job.model.Application;
import com.job.repository.ApplicationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author truonghonhatminh
 */
@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Application findByID(Integer applicationID) {
        return applicationRepository.findByID(applicationID);
    }

    public void add(Application application) {
        applicationRepository.add(application);
    }

    public void update(Application application) {
        applicationRepository.update(application);
    }

    public void deleteByID(Integer id) {
        applicationRepository.deleteByID(id);
    }

    public List<Application> search(String keyword) {
        return applicationRepository.search(keyword);
    }

    public List<Application> getPage(List<Application> list, int page, int size) {
        return applicationRepository.getPage(list, page, size);
    }

    public int countPages(List<Application> list, int size) {
        return applicationRepository.countPages(list, size);
    }
}
