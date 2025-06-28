package com.job.service.client;

import com.job.model.User;
import com.job.repository.UserRepository;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private ServletContext servletContext;
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

    public void updateUserProfile(User user, MultipartFile avatarFile) throws IOException {
        if (!avatarFile.isEmpty()) {
            String uploadDir = servletContext.getRealPath("/images/avatars/");
            String fileName = System.currentTimeMillis() + "_" + avatarFile.getOriginalFilename();
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            File file = new File(uploadDirFile, fileName);
            avatarFile.transferTo(file);
            user.setAvatarUrl("/images/avatars/" + fileName);
        }
        userRepository.updateUserProfile(user);
        logger.info("Updated profile for user: id={}", user.getId());
    }

    @Transactional
    public void save(User user) {
        logger.debug("Saving user: email={}", user.getEmail());
        userRepository.save(user);
        logger.info("User saved: id={}, email={}", user.getId(), user.getEmail());
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
}
