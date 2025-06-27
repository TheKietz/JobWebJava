package com.job.controller.client;

import com.job.enums.CommonEnums.ApplicationStatus;
import com.job.model.Application;
import com.job.service.client.ApplicationService;
import com.job.service.storage.FileStorageService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/client/application")
public class ApplicationController {

    private final String path = "/WEB-INF/views/client/application/";

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/applications")
    public String viewMyApplications(Model model, HttpSession session) {
        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        if (candidateId == null) {
            return "redirect:/login"; // Hoặc chuyển hướng về trang chủ tuỳ logic của bạn
        }

        List<Application> applications = applicationService.findByCandidateId(candidateId);
        model.addAttribute("applications", applications);
        model.addAttribute("body", path + "applications.jsp");
        return "client/layout/main";
    }

    // GET: Hiển thị form ứng tuyển
    @GetMapping("/apply")
    public String showApplyForm(@RequestParam("jobId") Integer jobId, Model model) {
        model.addAttribute("jobId", jobId);
        model.addAttribute("body", path + "form.jsp");
        return "client/layout/main";
    }

    // POST: Xử lý khi ứng tuyển
    @PostMapping("/apply")
    public String handleApply(
            @RequestParam("jobId") Integer jobId,
            @RequestParam("resumeFile") MultipartFile resumeFile,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        if (candidateId == null) {
            return "redirect:/login";
        }

        if (applicationService.hasApplied(candidateId, jobId)) {
            redirectAttributes.addFlashAttribute("error", "Bạn đã ứng tuyển công việc này rồi.");
            redirectAttributes.addAttribute("jobId", jobId);
            return "redirect:/client/application/apply";
        }

        try {
            String resumeUrl = fileStorageService.storeFile(resumeFile); // Đã kiểm tra định dạng và kích thước ở đây

            Application app = new Application();
            app.setCandidateId(candidateId);
            app.setJobId(jobId);
            app.setResumeUrl(resumeUrl);
            app.setStatus(ApplicationStatus.PENDING);
            app.setAppliedAt(LocalDateTime.now());

            applicationService.add(app);

            redirectAttributes.addFlashAttribute("success", "Ứng tuyển thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi upload file: " + e.getMessage());
        }

        redirectAttributes.addAttribute("jobId", jobId);
        return "redirect:/client/application/apply";
    }
}
