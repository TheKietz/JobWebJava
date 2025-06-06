/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service;

import com.job.model.User;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author 11090
 */
@Service
public class UserAdminService {

    private final List<User> UserList = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
    add(new User(1, "Nguyen Van A", "a.nguyen@example.com", "hash123A", "CANDIDATE", LocalDate.parse("2024-11-03")));
    add(new User(2, "Tran Thi B", "b.tran@example.com", "hash456B", "EMPLOYER", LocalDate.now()));
    add(new User(3, "Le Van C", "c.le@example.com", "hash789C", "CANDIDATE", LocalDate.now()));
    add(new User(4, "Pham Thi D", "d.pham@example.com", "hashABC1", "ADMIN", LocalDate.now()));
    add(new User(5, "Hoang Van E", "e.hoang@example.com", "hashXYZ9", "EMPLOYER", LocalDate.now()));
    add(new User(6, "Nguyen Thi F", "f.nguyen@example.com", "fpass1", "CANDIDATE", LocalDate.parse("2024-10-22")));
    add(new User(7, "Tran Van G", "g.tran@example.com", "gpass2", "EMPLOYER", LocalDate.now()));
    add(new User(8, "Le Thi H", "h.le@example.com", "hpass3", "CANDIDATE", LocalDate.now()));
    add(new User(9, "Do Van I", "i.do@example.com", "ipass4", "CANDIDATE", LocalDate.now()));
    add(new User(10, "Vo Thi J", "j.vo@example.com", "jpass5", "EMPLOYER", LocalDate.now()));
    add(new User(11, "Bui Van K", "k.bui@example.com", "kpass6", "ADMIN", LocalDate.now()));
    add(new User(12, "Dang Thi L", "l.dang@example.com", "lpass7", "EMPLOYER", LocalDate.now()));
    add(new User(13, "Ngo Van M", "m.ngo@example.com", "mpass8", "CANDIDATE", LocalDate.parse("2024-12-01")));
    add(new User(14, "Ha Thi N", "n.ha@example.com", "npass9", "EMPLOYER", LocalDate.now()));
    add(new User(15, "Phan Van O", "o.phan@example.com", "opass0", "CANDIDATE", LocalDate.now()));
}


    public List<User> getPage(List<User> list, int page, int size) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) {
            return new ArrayList<>();
        }
        return list.subList(from, to);
    }

    public List<User> findAll() {
        return UserList;
    }

    public User findByID(int id) {
        return UserList.stream()
                .filter(c -> c.getUserID() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(User user) {
        user.setUserID(idGenerator.getAndIncrement());
        UserList.add(user);
    }

    public void update(User user) {
        for (int i = 0; i < UserList.size(); i++) {
            if (UserList.get(i).getUserID() == user.getUserID()) {
                User existingUser = UserList.get(i);
                user.setCreatedAt(existingUser.getCreatedAt()); // Giữ nguyên createdAt
                UserList.set(i, user);
                return;
            }
        }
    }
    // Xoá theo ID

    public void deleteByID(int id) {
        UserList.removeIf(c -> c.getUserID() == id);
    }

    public List<User> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            System.out.println("No keyword provided, returning full list: " + UserList.size() + " users");
            return UserList;
        }
        final String trimmedKeyword = keyword.trim(); // Use a new final variable
        System.out.println("Search keyword: '" + trimmedKeyword + "'");
        List<User> results = UserList.stream()
                .filter(c -> c.getFullName().toLowerCase().contains(trimmedKeyword.toLowerCase())
                || c.getEmail().toLowerCase().contains(trimmedKeyword.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("Search results for '" + trimmedKeyword + "': " + results.size() + " users");
        return results;
    }

    public int countPages(List<User> list, int size) {
        return (int) Math.ceil((double) list.size() / size);
    }
        public User findByEmail(String email) {
        return UserList.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
