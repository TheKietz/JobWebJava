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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private static final Logger logger = LoggerFactory.getLogger(UserAdminController.class);

    @Autowired
    private UserAdminService userService;
    @Autowired
    private EmployerAdminService employerService;
    @Autowired
    private CandidateAdminService candidateService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String list(Model model,
                       HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            logger.warn("Unauthorized access to /admin/users by user: {}", loggedInUser != null ? loggedInUser.getEmail() : "null");
            return "redirect:/admin/login";
        }

        logger.debug("Listing users: keyword={}, page={}, size={}", keyword, page, size);
        final String trimmedKeyword = keyword != null ? keyword.trim() : null;
        List<User> users = userService.search(trimmedKeyword);
        logger.info("Found {} users for keyword: {}", users.size(), trimmedKeyword);

        int totalPages = userService.countPages(users, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        List<User> pagedUsers = userService.getPage(users, page, size);
        logger.debug("Paged users: page={}, size={}, count={}", page, size, pagedUsers.size());

        Map<Integer, String> userEntityMap = new HashMap<>();
        for (User user : pagedUsers) {
            if (user.getRole() == Role.EMPLOYER) {
                Employer employer = employerService.findByUserID(user.getId());
                if (employer != null) {
                    userEntityMap.put(user.getId(), "employer:" + employer.getId());
                    logger.debug("Mapped userID {} to employer:{}", user.getId(), employer.getId());
                }
            } else if (user.getRole() == Role.CANDIDATE) {
                Candidate candidate = candidateService.findByUserID(user.getId());
                if (candidate != null) {
                    userEntityMap.put(user.getId(), "candidate:" + candidate.getId());
                    logger.debug("Mapped userID {} to candidate:{}", user.getId(), candidate.getId());
                }
            } else if (user.getRole() == Role.ADMIN) {
                userEntityMap.put(user.getId(), "id:" + user.getId());
                logger.debug("Mapped userID {} to admin:{}", user.getId(), user.getId());
            }
        }

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
                logger.warn("Unauthorized access to /admin/users/add by user: {}", loggedInUser != null ? loggedInUser.getEmail() : "null");
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
            logger.error("Error in add user: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể tải form thêm người dùng: " + e.getMessage());
            return "redirect:/admin/users?page=1&size=" + size + "&keyword=" + (keyword == null ? "" : keyword);
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, HttpSession session, @RequestParam(value = "size", defaultValue = "5") int size, @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
                logger.warn("Unauthorized access to /admin/users/edit/{} by user: {}", id, loggedInUser != null ? loggedInUser.getEmail() : "null");
                return "redirect:/admin/login";
            }

            User user = userService.findByID(id);
            if (user == null) {
                logger.error("User not found: id={}", id);
                return "redirect:/admin/users?page=1&size=" + size + "&keyword=" + (keyword == null ? "" : keyword);
            }
            logger.debug("Editing user: id={}", id);
            model.addAttribute("user", user);
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
            return "admin/layout/main";
        } catch (Exception e) {
            logger.error("Error editing user: id={}, error={}", id, e.getMessage(), e);
            model.addAttribute("error", "Không thể tải người dùng: " + e.getMessage());
            return "redirect:/admin/users?page=1&size=" + size + "&keyword=" + (keyword == null ? "" : keyword);
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") User user,
                       BindingResult result,
                       @RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
                       Model model,
                       HttpSession session,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            logger.warn("Unauthorized access to /admin/users/save by user: {}", loggedInUser != null ? loggedInUser.getEmail() : "null");
            return "redirect:/admin/login";
        }

        logger.debug("Saving user: id={}, email={}, role={}", user.getId(), user.getEmail(), user.getRole());

        // Validate password
        if (user.getId() == null || (user.getPassword() != null && !user.getPassword().isBlank())) {
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                result.addError(new FieldError("user", "password", "Mật khẩu không được để trống"));
            } else if (user.getPassword().length() < 8) {
                result.addError(new FieldError("user", "password", "Mật khẩu phải có ít nhất 8 ký tự"));
            } else if (!user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
                result.addError(new FieldError("user", "password", "Mật khẩu phải chứa chữ hoa, chữ thường và số"));
            } else if (!user.getPassword().equals(passwordConfirm)) {
                result.addError(new FieldError("user", "password", "Mật khẩu và xác nhận mật khẩu không khớp"));
            }
        }

        // Validate role
        if (user.getRole() == null) {
            result.addError(new FieldError("user", "role", "Vai trò không được để trống"));
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.warn("Validation error: {}", error));
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
            return "admin/layout/main";
        }

        try {
            if (user.getId() == null) {
                // Thêm mới
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setCreatedAt(LocalDateTime.now());
                user.setRole(Role.ADMIN);
                user.setStatus(Status.ACTIVE);
                userService.add(user);
                logger.info("Added new user: email={}", user.getEmail());
            } else {
                // Cập nhật
                User existingUser = userService.findByID(user.getId());
                if (existingUser == null) {
                    logger.error("User not found for update: id={}", user.getId());
                    model.addAttribute("error", "Không tìm thấy người dùng");
                    return "admin/layout/main";
                }
                if (user.getPassword() == null || user.getPassword().isBlank()) {
                    user.setPassword(existingUser.getPassword());
                } else {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
                user.setCreatedAt(existingUser.getCreatedAt());
                user.setStatus(existingUser.getStatus());
                user.setRole(existingUser.getRole());
                userService.update(user);
                logger.info("Updated user: email={}", user.getEmail());
            }
            return "redirect:/admin/users?page=1&size=" + size + "&keyword=" + (keyword == null ? "" : keyword);
        } catch (Exception e) {
            logger.error("Error saving user: email={}, error={}", user.getEmail(), e.getMessage(), e);
            model.addAttribute("error", "Lỗi khi lưu người dùng: " + e.getMessage());
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
            logger.warn("Unauthorized access to /admin/users/delete/{} by user: {}", id, loggedInUser != null ? loggedInUser.getEmail() : "null");
            return "redirect:/admin/login";
        }

        logger.debug("Attempting to delete user: id={}", id);
        if (userService.deleteByID(id)) {
            logger.info("Deleted user: id={}", id);
            redirectAttributes.addFlashAttribute("success", "Xóa người dùng thành công.");
        } else {
            logger.warn("Failed to delete user: id={} due to dependencies", id);
            redirectAttributes.addFlashAttribute("error", "Không thể xóa người dùng do liên kết với dữ liệu khác.");
        }
        return "redirect:/admin/users?page=1&size=" + size + "&keyword=" + (keyword == null ? "" : keyword);
    }
}
