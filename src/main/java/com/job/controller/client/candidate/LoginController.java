package com.job.controller.client.candidate;

import com.job.enums.CommonEnums.Role;
import com.job.model.LoginForm;
import com.job.model.User;
import com.job.service.client.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        logger.debug("Showing login form");
        model.addAttribute("loginForm", new LoginForm());
        return "client/candidate/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        logger.debug("Processing login: email={}", email);
        User user = userService.findByEmail(email);
        if (user == null || !userService.verifyPassword(password, user.getPassword())) {
            logger.warn("Login failed: email={}", email);
            model.addAttribute("error", "Email hoặc mật khẩu không đúng");
            return "client/candidate/login";
        }

        if (user.getRole() == Role.CANDIDATE) {
            session.setAttribute("loggedInUser", user);
            logger.info("Candidate logged in: id={}, email={}", user.getId(), user.getEmail());
            return "redirect:/";
        } else if (user.getRole() == Role.ADMIN) {
            logger.info("Admin login attempt: email={}, redirecting to /admin/login", email);
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập tại trang quản trị viên");
            return "redirect:/admin/login";
        } else if (user.getRole() == Role.EMPLOYER) {
            logger.info("Employer login attempt: email={}, redirecting to /employer/login", email);
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập tại trang nhà tuyển dụng");
            return "redirect:/employer/login";
        } else {
            logger.warn("Invalid role for login: email={}, role={}", email, user.getRole());
            model.addAttribute("error", "Vai trò không hợp lệ để đăng nhập");
            return "client/candidate/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.debug("Logging out user: {}", session.getAttribute("loggedInUser"));
        session.invalidate();
        return "redirect:/candidate/login";
    }
}
