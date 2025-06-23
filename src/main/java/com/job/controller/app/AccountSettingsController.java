package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.model.User;
import com.job.service.client.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/app/settings")
public class AccountSettingsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountSettingsController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public String showSettings(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || 
            !(loggedInUser.getRole().equals(CommonEnums.Role.EMPLOYER))) {
            logger.warn("Unauthorized access to /app/settings");
            return "redirect:/app/login";
        }
        User user = userService.findByID(loggedInUser.getId());
        if (user == null) {
            logger.error("User not found: id={}", loggedInUser.getId());
            return "redirect:/app/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("body", "/WEB-INF/views/app/account/settings.jsp");
        model.addAttribute("csrfToken", session.getAttribute("csrfToken"));
        return "app/layout/main";
    }

    @PostMapping
    public String updateSettings(@Valid @ModelAttribute("user") User user, BindingResult result,
                                 @RequestParam("avatarFile") MultipartFile avatarFile,
                                 HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || 
            !(loggedInUser.getRole().equals(CommonEnums.Role.EMPLOYER.name()))) {
            logger.warn("Unauthorized post to /account/settings");
            return "redirect:/app/login";
        }
        if (result.hasErrors()) {
            logger.warn("Validation errors in account settings form");
            model.addAttribute("user", userService.findByID(loggedInUser.getId()));
            return "app/account/settings";
        }
        try {
            user.setId(loggedInUser.getId());
            userService.updateUserProfile(user, avatarFile);
            session.setAttribute("loggedInUser", userService.findByID(loggedInUser.getId()));
            model.addAttribute("success", "Profile updated successfully");
        } catch (Exception e) {
            logger.error("Error updating profile: {}", e.getMessage(), e);
            model.addAttribute("error", "Failed to update profile");
        }
        model.addAttribute("user", userService.findByID(loggedInUser.getId()));
        return "app/account/settings";
    }
}
