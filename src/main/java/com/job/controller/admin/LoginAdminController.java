package com.job.controller.admin;

import com.job.enums.CommonEnums.Role;
import com.job.model.User;
import com.job.service.UserAdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginAdminController {

    @Autowired
    private UserAdminService userService;

    @RequestMapping(value = {"/admin","/admin/login"})
    public String showLoginForm(Model model) {
        return "admin/login"; // Assumes admin/login.jsp exists
    }

    @PostMapping("/admin/login")
    public String processLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              Model model,
                              HttpSession session) {
        // 1. Ngoại lệ đăng nhập thủ công
    if ("thekiet@gmail.com".equals(email) && "admin".equals(password)) {
        User specialAdmin = new User();
        specialAdmin.setId(0); 
        specialAdmin.setEmail(email);
        specialAdmin.setFullName("Super Admin");
        specialAdmin.setRole(Role.ADMIN);

        session.setAttribute("loggedInUser", specialAdmin);
        session.setAttribute("userRole", Role.ADMIN);

        System.out.println("Super admin login bypass: " + email);
        return "redirect:/admin/dashboard";
    }
        User user = userService.findByEmail(email);
        if (user == null || !userService.verifyPassword(password, user.getPassword())) {
            model.addAttribute("error", "Invalid email or password");
            System.out.println("Admin login failed: email=" + email);
            return "admin/login";
        }

        if (user.getRole() != Role.ADMIN) {
            model.addAttribute("error", "Access restricted to admins");
            System.out.println("Non-admin attempted admin login: email=" + email + ", role=" + user.getRole());
            return "admin/login";
        }

        session.setAttribute("loggedInUser", user);
        session.setAttribute("userRole", user.getRole());
        System.out.println("Admin logged in: userID=" + user.getId()+ ", email=" + email);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("Admin logged out.");
        return "redirect:/admin/login";
    }
}