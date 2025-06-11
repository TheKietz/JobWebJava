package com.job.service.client;

import com.job.model.User;
import com.job.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public boolean verifyPassword(String rawPassword, String storedPassword) {
        boolean matches = rawPassword != null && rawPassword.equals(storedPassword);
        System.out.println("verifyPassword: rawPassword=" + rawPassword + ", storedPassword=" + storedPassword + ", matches=" + matches);
        return matches;
    }
}
