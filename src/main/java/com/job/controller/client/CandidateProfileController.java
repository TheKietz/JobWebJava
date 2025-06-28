package com.job.controller.client;

import com.job.dto.CandidateProfileDTO;
import com.job.enums.CommonEnums;
import com.job.model.User;
import com.job.service.client.ProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class CandidateProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public String viewProfile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return "redirect:/login";
        }

        CandidateProfileDTO profile = profileService.getProfileByUserId(loggedInUser.getId());
        model.addAttribute("profile", profile);
        model.addAttribute("body", "/WEB-INF/views/client/profile/profile.jsp");
        return "client/layout/main";
    }
}

