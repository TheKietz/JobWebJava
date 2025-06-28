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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/edit")
    public String editProfileForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return "redirect:/login";
        }

        CandidateProfileDTO profile = profileService.getProfileByUserId(user.getId());
        model.addAttribute("profile", profile);
        model.addAttribute("body", "/WEB-INF/views/client/profile/edit.jsp");
        return "client/layout/main";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute CandidateProfileDTO profileDTO,
            HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !user.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return "redirect:/login";
        }

        profileDTO.setUserId(user.getId());
        profileService.updateProfile(profileDTO);
        redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/profile";
    }

    @PostMapping("/delete")
    public String deleteProfile(HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        boolean success = profileService.deactivateUser(loggedInUser.getId());
        if (success) {
            session.invalidate(); // đăng xuất
            redirectAttributes.addFlashAttribute("message", "Tài khoản đã bị vô hiệu hoá.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Xoá tài khoản thất bại.");
        }

        return "redirect:/";
    }
}