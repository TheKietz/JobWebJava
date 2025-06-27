package com.job.service.client;

import com.job.model.Employer;
import com.job.model.User;
import com.job.repository.EmployerRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {

    private static final Logger logger = LoggerFactory.getLogger(EmployerService.class);

    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private UserService userService;

    public Employer findByUserID(Integer userID) {
        logger.debug("Finding employer by userID={}", userID);
        return employerRepository.findByUserID(userID);
    }

    public void add(Employer employer) {
        if (employer.getUserId() == null) {
            logger.error("Cannot add employer: userId is null");
            throw new IllegalArgumentException("userId cannot be null");
        }
        if (employer.getCompanyName() == null || employer.getCompanyName().isBlank()) {
            logger.error("Cannot add employer: companyName is null or empty");
            throw new IllegalArgumentException("companyName cannot be null or empty");
        }
        if (employer.getAddress() == null || employer.getAddress().isBlank()) {
            logger.error("Cannot add employer: address is null or empty");
            throw new IllegalArgumentException("address cannot be null or empty");
        }
        logger.debug("Adding employer: companyName={}, userId={}", employer.getCompanyName(), employer.getUserId());
        employerRepository.add(employer);
        logger.info("Added employer: companyName={}, userId={}", employer.getCompanyName(), employer.getUserId());
    }

    public void update(Employer employer) {
        if (employer.getId() == null) {
            logger.error("Cannot update employer: id is null");
            throw new IllegalArgumentException("id cannot be null");
        }
        logger.debug("Updating employer: id={}, companyName={}", employer.getId(), employer.getCompanyName());
        employerRepository.update(employer);
        logger.info("Updated employer: id={}, companyName={}", employer.getId(), employer.getCompanyName());
    }
     public List<Employer> findAll() {

        return employerRepository.findAll();
    }

    public Employer findByID(Integer id) {
        return employerRepository.findByID(id);
    }

    public Integer findEmployerIdByUserId(int id){
        return employerRepository.findEmployerIdByUserId(id);
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

    public String getEmployerEmail(Employer employer) {
        User user = userService.findByID(employer.getId());
        return user != null ? user.getEmail() : null;
    }

}
