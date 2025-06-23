package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.model.User;
import com.job.service.client.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
        logger.debug("showSettings: loggedInUser={}", loggedInUser);
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.EMPLOYER)) {
            logger.warn("Unauthorized access to /app/settings: No logged-in user or not an EMPLOYER");
            return "redirect:/app/login";
        }
        User user = userService.findByID(loggedInUser.getId());
        logger.debug("showSettings: user from service={}", user);
        if (user == null) {
            logger.error("User not found: id={}", loggedInUser.getId());
            return "redirect:/app/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("body", "/WEB-INF/views/app/account/settings.jsp");
        model.addAttribute("csrfToken", session.getAttribute("csrfToken"));
        return "app/layout/main";
    }

    @RequestMapping(value = "settings", method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String updateSettings(@Valid @ModelAttribute("user") User user, BindingResult result,
            @RequestParam("avatarFile") MultipartFile avatarFile,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            HttpServletRequest request,
            HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.EMPLOYER)) {
            logger.warn("Unauthorized post to /app/settings: No logged-in user or not an EMPLOYER");
            return "redirect:/app/login";
        }
        if (result.hasErrors()) {
            logger.warn("Validation errors in account settings form: {}", result.getAllErrors());
            model.addAttribute("user", userService.findByID(loggedInUser.getId()));
            return "app/account/settings";
        }
        try {
            user.setId(loggedInUser.getId());

            // Handle avatar file upload
            if (!avatarFile.isEmpty()) {
                String uploadPath = request.getServletContext().getRealPath("/upload/users/");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String fileName = "user_" + loggedInUser.getId() + "_" + avatarFile.getOriginalFilename();
                File destFile = new File(uploadDir, fileName);
                avatarFile.transferTo(destFile);
                user.setAvatarUrl("/upload/users/" + fileName);
            }

            // Handle additional image file (optional)
            if (imageFile != null && !imageFile.isEmpty()) {
                String uploadPath = request.getServletContext().getRealPath("/upload/users/");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String fileName = "user_" + loggedInUser.getId() + "_image_" + imageFile.getOriginalFilename();
                File destFile = new File(uploadDir, fileName);
                imageFile.transferTo(destFile);
                // Optionally update avatarUrl or another field if imageFile is intended as new avatar
                user.setAvatarUrl("/upload/users/" + fileName); // Overwrite avatar with imageFile if intended
            }

            // Update user in repository
            userService.update(user);
            // Update session with the latest user data
            User updatedUser = userService.findByID(loggedInUser.getId());
            session.setAttribute("loggedInUser", updatedUser);
            model.addAttribute("success", "Profile updated successfully");
        } catch (IOException e) {
            logger.error("Error uploading image file: {}", e.getMessage(), e);
            model.addAttribute("error", "Failed to upload image: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating profile: {}", e.getMessage(), e);
            model.addAttribute("error", "Failed to update profile: " + e.getMessage());
        }
        model.addAttribute("user", userService.findByID(loggedInUser.getId()));
        return "app/account/settings";
    }
}
