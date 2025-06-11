package com.job.controller;

import com.job.enums.CommonEnums.Role;
import com.job.enums.CommonEnums.Status;
import com.job.model.Candidate;
import com.job.model.Employer;
import com.job.model.User;
import com.job.service.EmployerAdminService;
import com.job.service.CandidateAdminService;
import com.job.service.UserAdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                       HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/users, redirecting to login");
            return "redirect:/admin/login";
        }

        System.out.println("list: loggedInUser=" + loggedInUser.getEmail() + ", role=" + loggedInUser.getRole());
        final String trimmedKeyword = keyword != null ? keyword.trim() : null;
        System.out.println("Request URL: /admin/users?page=" + page + "&size=" + size + "&keyword=" + trimmedKeyword);

        size = Math.max(1, size);
        List<User> users = userService.search(trimmedKeyword);
        System.out.println("Search keyword: '" + trimmedKeyword + "', Found " + users.size() + " users");

        int totalPages = userService.countPages(users, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        List<User> pagedUsers = userService.getPage(users, page, size);
        System.out.println("Page: " + page + ", Size: " + size + ", Paged Users=" + pagedUsers.size());
        System.out.println("Total pages: " + totalPages);
        System.out.println("Paged users content: " + pagedUsers);

        Map<Integer, String> userEntityMap = new HashMap<>();
        for (User user : pagedUsers) {
            System.out.println("Processing user: id=" + user.getId() + ", role=" + user.getRole());
            if (user.getRole() == Role.EMPLOYER) {
                Employer employer = employerService.findByUserID(user.getId());
                if (employer != null) {
                    userEntityMap.put(user.getId(), "employer:" + employer.getId());
                    System.out.println("Mapped userID " + user.getId() + " to employer:" + employer.getId());
                } else {
                    System.out.println("No employer found for userID " + user.getId());
                }
            } else if (user.getRole() == Role.CANDIDATE) {
                Candidate candidate = candidateService.findByUserID(user.getId());
                if (candidate != null) {
                    userEntityMap.put(user.getId(), "candidate:" + candidate.getId());
                    System.out.println("Mapped userID " + user.getId() + " to candidate:" + candidate.getId());
                } else {
                    System.out.println("No candidate found for userID " + user.getId());
                }
            } else if (user.getRole() == Role.ADMIN) {
                userEntityMap.put(user.getId(), "id:" + user.getId());
                System.out.println("Mapped userID " + user.getId() + " to admin:" + user.getId());
            }
        }
        System.out.println("userEntityMap: " + userEntityMap);

        model.addAttribute("users", pagedUsers);
        model.addAttribute("keyword", trimmedKeyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("userMap", userEntityMap);
        model.addAttribute("body", "/WEB-INF/views/admin/user/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session, @RequestParam(value = "size", defaultValue = "5") int size, @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
                System.out.println("Unauthorized access to /admin/users/add, redirecting to login");
                return "redirect:/admin/login";
            }

            User user = new User();
            user.setRole(Role.ADMIN);
            model.addAttribute("user", user);
            model.addAttribute("createdAt", LocalDateTime.now());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
            return "admin/layout/main";
        } catch (Exception e) {
            System.err.println("Error in add user: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Failed to load add form: " + e.getMessage());
            return "redirect:/admin/users?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, HttpSession session, @RequestParam(value = "size", defaultValue = "5") int size, @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
                System.out.println("Unauthorized access to /admin/users/edit/" + id + ", redirecting to login");
                return "redirect:/admin/login";
            }

            User user = userService.findByID(id);
            if (user == null) {
                System.out.println("User not found for ID: " + id);
                return "redirect:/admin/users?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
            }
            System.out.println("Editing user: id=" + id + ", role=" + user.getRole());
            model.addAttribute("user", user);
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
            return "admin/layout/main";
        } catch (Exception e) {
            System.err.println("Error in edit user ID=" + id + ": " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Failed to load user: " + e.getMessage());
            return "redirect:/admin/users?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") User user,
                       BindingResult result,
                       Model model,
                       HttpSession session,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/users/save, redirecting to login");
            return "redirect:/admin/login";
        }

        // Debug input
        System.out.println("Saving user: id=" + user.getId() + ", email=" + user.getEmail() + ", role=" + user.getRole() + ", status=" + user.getStatus());

        // Validation thủ công cho password khi thêm mới
        if (user.getId() == null) {
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                result.addError(new FieldError("user", "password", "Password cannot be blank"));
            } else if (user.getPassword().length() < 8) {
                result.addError(new FieldError("user", "password", "Password must be at least 8 characters long"));
            }
        }

        // Kiểm tra role
        if (user.getRole() == null) {
            result.addError(new FieldError("user", "role", "Role cannot be null"));
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error));
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
            return "admin/layout/main";
        }

        try {
            if (user.getId() == null) {
                // Thêm mới
                user.setCreatedAt(LocalDateTime.now());
                user.setRole(Role.ADMIN);
                user.setStatus(Status.ACTIVE);
                userService.add(user);
                System.out.println("Added new user: " + user.getEmail());
            } else {
                // Cập nhật
                User existingUser = userService.findByID(user.getId());
                if (existingUser == null) {
                    System.out.println("User not found for update: " + user.getId());
                    model.addAttribute("error", "User not found");
                    return "admin/layout/main";
                }
                if (user.getPassword() == null || user.getPassword().isBlank()) {
                    user.setPassword(existingUser.getPassword());
                }
                user.setCreatedAt(existingUser.getCreatedAt());
                user.setStatus(existingUser.getStatus());
                user.setRole(existingUser.getRole()); // Giữ role hiện tại
                userService.update(user);
                System.out.println("Updated user: " + user.getEmail());
            }
            return "redirect:/admin/users?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Failed to save user: " + e.getMessage());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
            return "admin/layout/main";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id,
                         HttpSession session,
                         @RequestParam(value = "size", defaultValue = "5") int size,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/users/delete/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }
        System.out.println("delete: Attempting to delete UserID=" + id);
        if (userService.deleteByID(id)) {
            System.out.println("delete: Deleted user with ID: " + id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully.");
        } else {
            System.out.println("delete: Failed to delete user with ID: " + id + " due to dependencies");
            redirectAttributes.addFlashAttribute("error", "Cannot delete user due to linked records (e.g., notifications, messages, feedbacks).");
        }
        return "redirect:/admin/users?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
    }
}