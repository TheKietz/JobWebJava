
package com.job.controller.client.candidate;

import com.job.enums.CommonEnums.Role;
import com.job.enums.CommonEnums.Status;
import com.job.model.RegisterForm;
import com.job.model.User;
import com.job.service.client.UserService; 
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService; 

    @GetMapping("/signup")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "client/candidate/signup";
    }

    @PostMapping("/signup")
    @Transactional
    public String processRegister(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                                 BindingResult result,
                                 Model model) {
        logger.debug("Processing registration: email={}", registerForm.getEmail());
        if (result.hasErrors()) {
            logger.warn("Validation errors: {}", result.getAllErrors());
            return "client/candidate/signup";
        }

        if (!registerForm.getPassword().equals(registerForm.getPasswordConfirm())) {
            logger.warn("Password mismatch: email={}", registerForm.getEmail());
            model.addAttribute("error", "Mật khẩu và xác nhận mật khẩu không khớp");
            return "client/candidate/signup";
        }

        if (userService.findByEmail(registerForm.getEmail()) != null) {
            logger.warn("Email already exists: {}", registerForm.getEmail());
            model.addAttribute("error", "Email đã được sử dụng");
            return "index";
        }

        User user = new User();
        user.setFullName(registerForm.getFullName());
        user.setEmail(registerForm.getEmail());
        user.setPassword(userService.encodePassword(registerForm.getPassword()));
        user.setPhone(registerForm.getPhone());
        user.setRole(Role.CANDIDATE);
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        try {
            userService.save(user);
            logger.info("User registered: id={}, email={}, role={}", user.getId(), user.getEmail(), user.getRole());
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            logger.error("Error registering user: email={}, error={}", registerForm.getEmail(), e.getMessage(), e);
            model.addAttribute("error", "Đã xảy ra lỗi khi đăng ký: " + e.getMessage());
            return "client/candidate/signup";
        }
    }
}
