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
import org.springframework.stereotype.Service;

/**
 *
 * @author 11090
 */
@Service
public class UserAdminService {
    private final List<User> UserList = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    LocalDate date = LocalDate.parse("2024-11-03");
    @PostConstruct
    public void init() {
        add(new User(
            1, "Nguyen Van A", "a.nguyen@example.com", "hash123A", "CANDIDATE",date));
        add(new User(
            2, "Tran Thi B", "b.tran@example.com", "hash456B", "EMPLOYER", LocalDate.now()));
        add(new User(
            3, "Le Van C", "c.le@example.com", "hash789C", "CANDIDATE", LocalDate.now()));
        add(new User(
            4, "Pham Thi D", "d.pham@example.com", "hashABC1", "ADMIN", LocalDate.now()));
        add(new User(
            5, "Hoang Van E", "e.hoang@example.com", "hashXYZ9", "EMPLOYER", LocalDate.now()));
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
            return UserList;
        }
        return UserList.stream()
                .filter(c -> c.getFullName().toLowerCase().contains(keyword.toLowerCase())
                || c.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<User> getPage(List<User> list, int page, int size) {
        int from = (page - 1) * size;
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) {
            return new ArrayList<>();
        }
        return list.subList(from, to);
    }

    public int countPages(List<User> list, int size) {
        return (int) Math.ceil((double) list.size() / size);
    }
}
