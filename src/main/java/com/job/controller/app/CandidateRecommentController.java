/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.app;

import com.job.dto.CandidateApplicationDTO;
import com.job.dto.RecommentCandidateDTO;
import com.job.enums.CommonEnums;
import com.job.model.Employer;
import com.job.model.User;
import com.job.service.client.ApplicationService;
import com.job.service.client.CandidateService;
import com.job.service.client.EmployerService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author 11090
 */
@Controller
public class CandidateRecommentController {
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private EmployerService employerService;
    @GetMapping("/app/suggest-cv")
    public String listCandidatesApplied(Model model, HttpSession session,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            return ("redirect:/app/login");
        }
        Employer employerId = employerService.findByUserID(loggedInUser.getId());
        List<RecommentCandidateDTO> allApplications = candidateService.findRecommentCandidateForEmp(employerId.getId());

        final String trimmedKeyword = (keyword != null && !keyword.isBlank()) ? keyword.trim().toLowerCase() : null;
        if (trimmedKeyword != null) {
            allApplications = allApplications.stream()
                    .filter(dto -> dto.getFullName().toLowerCase().contains(trimmedKeyword)
                    || dto.getSkills().toLowerCase().contains(trimmedKeyword))
                    .toList();
        }

        // Tính toán phân trang
        size = Math.max(1, size);
        int totalPages = (int) Math.ceil((double) allApplications.size() / size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allApplications.size());
        List<RecommentCandidateDTO> paginated = allApplications.subList(fromIndex, toIndex);

        model.addAttribute("candidates", paginated);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("body", "/WEB-INF/views/app/candidate/suggest-cv.jsp");
        return "app/layout/main";
    }
}
