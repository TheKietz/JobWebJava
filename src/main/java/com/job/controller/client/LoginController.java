package com.job.controller.client;

import static com.job.enums.CommonEnums.Role.ADMIN;
import static com.job.enums.CommonEnums.Role.CANDIDATE;
import static com.job.enums.CommonEnums.Role.EMPLOYER;
import com.job.model.LoginForm;
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

@Controller
public class LoginController {

    @Autowired
    private UserAdminService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "client/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("loginForm") LoginForm loginForm,
                              BindingResult result,
                              Model model,
                              HttpSession session) {
        if (result.hasErrors()) {
            return "client/login";
        }

        User user = userService.findByEmail(loginForm.getEmail());
        if (user == null || !userService.verifyPassword(loginForm.getPasswordHash(), user.getPassword())) {
            model.addAttribute("error", "Invalid email or password");
            return "client/login";
        }

        // Store user info in session
        session.setAttribute("loggedInUser", user);
        session.setAttribute("userRole", user.getRole());
        System.out.println("User logged in: userID=" + user.getId()+ ", email=" + user.getEmail() + ", role=" + user.getRole());

        // Redirect based on role
        switch (user.getRole()) { // user.getRole() trả về Role enum
    case CANDIDATE -> { 
        return "redirect:/home";
    }
    case EMPLOYER -> { 
        return "redirect:/home";
    }
    case ADMIN -> { 
        return "redirect:/admin/login";
    }
    default -> {
        model.addAttribute("error", "Unknown role");
        session.invalidate();
        return "client/login";
    }
}
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("User logged out.");
        return "redirect:/client/login";
    }
}