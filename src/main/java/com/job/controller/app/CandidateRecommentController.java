/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.app;

import com.job.dto.RecommentCandidateDTO;
import com.job.enums.CommonEnums;
import com.job.model.Employer;
import com.job.model.InvitedCandidates;
import com.job.model.User;
import com.job.service.client.CandidateService;
import com.job.service.client.EmployerService;
import com.job.service.client.InvitedCandidatesService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author 11090
 */
@Controller
public class CandidateRecommentController {
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private InvitedCandidatesService invitedCandidatesService;
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
        List<InvitedCandidates> inviteCandidate = invitedCandidatesService.getAllInvitations();
        
        
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
    @GetMapping("/app/suggest-cv/add/{candidateId}")
    public String inviteCandidate(@PathVariable("candidateId") Integer candidateId,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                  @RequestParam(value = "keyword", required = false) String keyword) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập với tài khoản nhà tuyển dụng.");
            return "redirect:/app/login";
        }

        Employer employer = employerService.findByUserID(loggedInUser.getId());
        if (employer == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thông tin nhà tuyển dụng của bạn.");
            return "redirect:/app/suggest-cv"; // Quay lại trang gợi ý
        }

        try {
            // Kiểm tra xem ứng viên đã được mời bởi nhà tuyển dụng này hay chưa
            InvitedCandidates existingInvitation = invitedCandidatesService.getInvitationByEmployerAndCandidateId(employer.getId(), candidateId);

            if (existingInvitation != null) {
                redirectAttributes.addFlashAttribute("error", "Ứng viên này đã được bạn mời phỏng vấn rồi.");
            } else {
                // Tạo đối tượng InvitedCandidates
                InvitedCandidates newInvitation = new InvitedCandidates();
                newInvitation.setEmployerId(employer.getId());
                newInvitation.setCandidateId(candidateId);
                newInvitation.setInvitedAt(LocalDate.now()); // Ngày mời là ngày hiện tại

                // Lưu vào cơ sở dữ liệu
                Integer invitationId = invitedCandidatesService.addInvitation(newInvitation);

                if (invitationId != null) {
                    redirectAttributes.addFlashAttribute("success", "Đã gửi lời mời phỏng vấn thành công cho ứng viên!");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi gửi lời mời phỏng vấn.");
                }
            }
        } catch (Exception e) {
            // Log lỗi chi tiết để debug
            System.err.println("Error inviting candidate: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi xử lý yêu cầu của bạn: " + e.getMessage());
        }

        // Quay lại trang danh sách ứng viên đề xuất với các tham số phân trang/tìm kiếm hiện tại
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);
        redirectAttributes.addAttribute("keyword", keyword);
        return "redirect:/app/suggest-cv";
    }
}
