package com.job.service.client;

import com.job.model.User;
import com.job.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

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

    public void deleteByID(Integer id) {
        userRepository.deleteById(id);
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
}