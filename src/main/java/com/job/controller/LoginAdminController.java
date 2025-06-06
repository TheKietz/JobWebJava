package com.job.controller;

import com.job.model.User;
import com.job.service.UserAdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class LoginAdminController {

    @Autowired
    private UserAdminService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "admin/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("loginForm") LoginForm loginForm,
                               BindingResult result,
                               HttpSession session,
                               Model model) {
        if (result.hasErrors()) {
            return "admin/login";
        }

        User user = userService.findByEmail(loginForm.getEmail());
        if (user == null || !userService.verifyPassword(loginForm.getPassword(), user.getPasswordHash())) {
            model.addAttribute("error", "Email hoặc mật khẩu không đúng.");
            return "admin/login";
        }

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            model.addAttribute("error", "Bạn không có quyền truy cập khu vực quản trị.");
            return "admin/login";
        }

        session.setAttribute("loggedInUser", user);
        session.setAttribute("userRole", user.getRole());
        System.out.println("Admin logged in: userID=" + user.getUserID() + ", email=" + user.getEmail());

        return "redirect:/admin/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("Admin logged out.");
        return "redirect:/admin/login";
    }

    public static class LoginForm {
        @jakarta.validation.constraints.NotBlank(message = "Email không được để trống")
        @jakarta.validation.constraints.Email(message = "Email không hợp lệ")
        private String email;

        @jakarta.validation.constraints.NotBlank(message = "Mật khẩu không được để trống")
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}