/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller;

import com.job.model.Candidate;
import com.job.model.Employer;
import com.job.model.User;
import com.job.service.EmployerAdminService;
import com.job.service.UserAdminService;
import com.job.service.CandidateAdminService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/users")
public class UserAdminController {

    @Autowired
    private UserAdminService userService;
    @Autowired
    private EmployerAdminService employerService;
    @Autowired
    private CandidateAdminService candidateService;

    @GetMapping
    public String list(Model model,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        System.out.println("Request URL: /admin/users?page=" + page + "&size=" + size + "&keyword=" + trimmedKeyword);

        // Validate size
        size = Math.max(1, size);
        List<User> users = userService.search(trimmedKeyword);
        System.out.println("Search keyword: '" + trimmedKeyword + "', Filtered users: " + users.size());

        // Validate page number
        int totalPages = userService.countPages(users, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        List<User> pagedUsers = userService.getPage(users, page, size);
        System.out.println("Page: " + page + ", Size: " + size + ", Paged users: " + pagedUsers.size());
        System.out.println("Total pages: " + totalPages);
        System.out.println("Paged users content: " + pagedUsers);

        // Create mapping of userID to associated entity ID
        Map<Integer, String> userEntityMap = new HashMap<>();
        for (User user : pagedUsers) {
            System.out.println("Processing user: " + user.getUserID() + ", Role: " + user.getRole());
            if ("EMPLOYER".equalsIgnoreCase(user.getRole())) {
                Employer employer = employerService.findByUserID(user.getUserID());
                if (employer != null) {
                    userEntityMap.put(user.getUserID(), "employer:" + employer.getEmployerID());
                    System.out.println("Mapped userID " + user.getUserID() + " to employer:" + employer.getEmployerID());
                } else {
                    System.out.println("No employer found for userID " + user.getUserID());
                }
            } else if ("CANDIDATE".equalsIgnoreCase(user.getRole())) {
                Candidate candidate = candidateService.findByUserID(user.getUserID());
                if (candidate != null) {
                    userEntityMap.put(user.getUserID(), "candidate:" + candidate.getCandidateID());
                    System.out.println("Mapped userID " + user.getUserID() + " to candidate:" + candidate.getCandidateID());
                } else {
                    System.out.println("No candidate found for userID " + user.getUserID());
                }
            } else if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                userEntityMap.put(user.getUserID(), "admin:" + user.getUserID());
                System.out.println("Mapped userID " + user.getUserID() + " to admin:" + user.getUserID());
            } else {
                System.out.println("Unknown role for userID " + user.getUserID() + ": " + user.getRole());
            }
        }
        System.out.println("userEntityMap: " + userEntityMap);

        model.addAttribute("users", pagedUsers);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("userEntityMap", userEntityMap);
        model.addAttribute("body", "/WEB-INF/views/admin/user/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("createAt", LocalDate.now());
        model.addAttribute("admin","ADMIN");
        model.addAttribute("user", new User());
        model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
        return "admin/layout/main";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
            model.addAttribute("user", user);
            model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
            return "admin/layout/main";
        }
        if (user.getUserID() == null) {
            user.setCreatedAt(LocalDate.now());
            user.setRole("ADMIN");
            user.setPasswordHash("defaultPassword123");
            userService.add(user);
        } else {
            User existingUser = userService.findByID(user.getUserID());
            user.setPasswordHash(existingUser.getPasswordHash());
            userService.update(user);
        }
        return "redirect:/admin/users?page=1&size=" + size;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findByID(id));
        model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        userService.deleteByID(id);
        return "redirect:/admin/users";
    }
}
