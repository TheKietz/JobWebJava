/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.app;

import com.job.dto.EmployerLoginDTO;
import com.job.dto.EmployerRegisterDTO;
import com.job.enums.CommonEnums.Role;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/app")
public class LoginEmployerController {

    private static final Logger logger = LoggerFactory.getLogger(LoginEmployerController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        logger.info("Handling GET /employers/login");
        if (!model.containsAttribute("loginForm")) {
            logger.debug("Adding new LoginDTO to model");
            model.addAttribute("loginForm", new EmployerLoginDTO());
        }
        return "app/auth/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin(@RequestParam("email") String email,
            @RequestParam("passwordHash") String password,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        logger.debug("Processing employer login: email={}", email);
        User user = userService.findByEmail(email);
        if (user == null || !userService.verifyPassword(password, user.getPassword())) {
            logger.warn("Employer login failed: email={}", email);
            model.addAttribute("error", "Email hoặc mật khẩu không đúng");
            return "app/auth/login";
        }

        // Kiểm tra role
        if (user.getRole() == Role.EMPLOYER) {
            session.setAttribute("loggedInUser", user);
            logger.info("Employer logged in: id={}, email={}", user.getId(), user.getEmail());
            return "redirect:/app/dashboard";
        } else if (user.getRole() == Role.CANDIDATE) {
            logger.info("Candidate login attempt on employer page: email={}, redirecting to /login", email);
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập tại trang ứng viên");
            return "redirect:/login";
        } else if (user.getRole() == Role.ADMIN) {
            logger.info("Admin login attempt on employer page: email={}, redirecting to /admin/login", email);
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập tại trang quản trị viên");
            return "redirect:/admin/login";
        } else {
            logger.warn("Invalid role for employer login: email={}, role={}", email, user.getRole());
            model.addAttribute("error", "Vai trò không hợp lệ để đăng nhập");
            return "app/auth/login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        logger.debug("Logging out employer: {}", session.getAttribute("loggedInUser"));
        session.invalidate();
        return "redirect:/app/login";
    }
}
