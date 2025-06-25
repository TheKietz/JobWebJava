package com.job.service;

import com.job.enums.CommonEnums.Role;
import com.job.model.User;
import java.util.stream.Collectors;
import com.job.repository.UserRepository;
import com.job.service.client.UserService;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAdminService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CandidateAdminService candidateService;
    @Autowired
    private EmployerAdminService employerAdminService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> getPage(List<User> list, int page, int size) {
        return userRepository.getPage(list, page, size);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByID(Integer id) {
        return userRepository.findById(id);
    }

    public void add(User user) {
        userRepository.add(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public boolean deleteByID(Integer id) {
        return userRepository.deleteById(id);
    }

    public Map<String, Long> getRegistrationStatsByRoleAndDate(int days) {
        return userRepository.getRegistrationStatsByRoleAndDate(days);
    }

    public List<User> search(String keyword) {
        return userRepository.search(keyword);
    }

    public int countPages(List<User> list, int size) {
        return userRepository.countPages(list, size);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Transactional
    public void save(User user) {
        logger.debug("Saving user: email={}", user.getEmail());
        userRepository.save(user);
        logger.info("User saved: id={}, email={}", user.getId(), user.getEmail());
    }

    public List<User> findAvailableEmployers() {
        List<User> employers = userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.EMPLOYER)
                .collect(Collectors.toList());
        return employers.stream()
                .filter(user -> employerAdminService.findByUserID(user.getId()) == null)
                .collect(Collectors.toList());
    }

    public List<User> findAvailableCandidates() {
        List<User> candidates = userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.CANDIDATE)
                .collect(Collectors.toList());
        return candidates.stream()
                .filter(user -> candidateService.findByUserID(user.getId()) == null)
                .collect(Collectors.toList());
    }
}
