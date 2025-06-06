package com.job.service;

import com.job.model.User;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserAdminService {

    private final List<User> UserList = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @PostConstruct
    public void init() {
        add(new User(0, "Nguyen Van A", "a.nguyen@example.com", "hash123A", "CANDIDATE", LocalDate.parse("2024-11-03")));
        add(new User(0, "Tran Thi B", "b.tran@example.com", "hash456B", "EMPLOYER", LocalDate.now()));
        add(new User(0, "Le Van C", "c.le@example.com", "hash789C", "CANDIDATE", LocalDate.now()));
        add(new User(0, "Pham Thi D", "d.pham@example.com", "adminpass1", "ADMIN", LocalDate.now()));
        add(new User(0, "Hoang Van E", "e.hoang@example.com", "hashXYZ9", "EMPLOYER", LocalDate.now()));
        add(new User(0, "Nguyen Thi F", "f.nguyen@example.com", "fpass1", "CANDIDATE", LocalDate.parse("2024-10-22")));
        add(new User(0, "Tran Van G", "g.tran@example.com", "gpass2", "EMPLOYER", LocalDate.now()));
        add(new User(0, "Le Thi H", "h.le@example.com", "hpass3", "CANDIDATE", LocalDate.now()));
        add(new User(0, "Do Van I", "i.do@example.com", "ipass4", "CANDIDATE", LocalDate.now()));
        add(new User(0, "Vo Thi J", "j.vo@example.com", "jpass5", "EMPLOYER", LocalDate.now()));
        add(new User(0, "Nguyen The Kiet", "thekiet1109@gmail.com", "admin", "ADMIN", LocalDate.now()));
        add(new User(0, "Dang Thi L", "l.dang@example.com", "lpass7", "EMPLOYER", LocalDate.now()));
        add(new User(0, "Ngo Van M", "m.ngo@example.com", "mpass8", "CANDIDATE", LocalDate.parse("2024-12-01")));
        add(new User(0, "Ha Thi N", "n.ha@example.com", "npass9", "EMPLOYER", LocalDate.now()));
        add(new User(0, "Phan Van O", "o.phan@example.com", "opass0", "CANDIDATE", LocalDate.now()));
        System.out.println("UserList initialized: ");
        for (User user : UserList) {
            System.out.println("User: ID=" + user.getUserID() + ", Email=" + user.getEmail() + ", Role=" + user.getRole() + ", Password=" + user.getPasswordHash());
        }
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
        return new ArrayList<>(UserList);
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
                user.setCreatedAt(existingUser.getCreatedAt());
                UserList.set(i, user);
                return;
            }
        }
    }

    public void deleteByID(int id) {
        UserList.removeIf(c -> c.getUserID() == id);
    }

    public List<User> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            System.out.println("No keyword provided, returning full list: " + UserList.size() + " users");
            return new ArrayList<>(UserList);
        }
        final String trimmedKeyword = keyword.trim();
        System.out.println("Search keyword: '" + trimmedKeyword + "'");
        List<User> results = UserList.stream()
                .filter(c -> c.getFullName().toLowerCase().contains(trimmedKeyword.toLowerCase())
                        || c.getEmail().toLowerCase().contains(trimmedKeyword.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("Search results for '" + trimmedKeyword + "': " + results.size() + " users");
        return results;
    }

    public int countPages(List<User> list, int size) {
        return (int) Math.ceil((double) list.size() / Math.max(1, size));
    }

    public User findByEmail(String email) {
        if (email == null) return null;
        User user = UserList.stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst()
                .orElse(null);
        System.out.println("findByEmail: email=" + email + ", found=" + (user != null ? user.getEmail() : "null"));
        return user;
    }

    public boolean verifyPassword(String rawPassword, String storedPassword) {
        boolean matches = rawPassword != null && rawPassword.equals(storedPassword);
        System.out.println("verifyPassword: rawPassword=" + rawPassword + ", storedPassword=" + storedPassword + ", matches=" + matches);
        return matches;
    }
}