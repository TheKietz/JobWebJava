package com.job.controller.client;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        return "client/auth/login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("loginForm") LoginForm loginForm,
            @RequestParam("email") String email,
            @RequestParam("passwordHash") String password,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        logger.debug("Processing login: email={}", email);
        User user = userService.findByEmail(email);
        boolean pass1=userService.verifyPassword(password, user.getPassword());
        boolean pass2=userService.verifyRawPassword(password, user.getPassword());
        if(!pass1 && !pass2)
        {
           if (user == null) {
            model.addAttribute("error", "Invalid email or password");
            System.out.println("Admin login failed: email=" + email);
            return "admin/login";
        }
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
            return "redirect:/app/login";
        } else {
            logger.warn("Invalid role for login: email={}, role={}", email, user.getRole());
            model.addAttribute("error", "Vai trò không hợp lệ để đăng nhập");
            return "client/auth/login";
        }
    }

    @PostMapping(value = "/logout")
    public String logout(HttpSession session) {
        logger.debug("Logging out user: {}", session.getAttribute("loggedInUser"));
        session.invalidate();
        return "redirect:/login";
    }
}
