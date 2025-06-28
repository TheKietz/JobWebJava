package com.job.controller.client;

import com.job.enums.CommonEnums;
import com.job.enums.CommonEnums.ApplicationStatus;
import com.job.model.Application;
import com.job.model.Candidate;
import com.job.model.Job;
import com.job.model.User;
import com.job.service.client.ApplicationService;
import com.job.service.client.CandidateService;
import com.job.service.client.JobService;
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
@RequestMapping("/applications")
public class ApplicationController {

    private final String path = "/WEB-INF/views/client/application/";

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private JobService jobService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public String viewMyApplications(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return "redirect:/login";
        }

        Candidate candidate = candidateService.findByUserID(loggedInUser.getId());
        if (candidate == null) {
            return "redirect:/login";
        }

        List<Application> applications = applicationService.findByCandidateId(candidate.getId());
        model.addAttribute("applications", applications);
        model.addAttribute("body", path + "applications.jsp");
        return "client/layout/main";
    }

    // GET: Hiển thị form ứng tuyển
    @GetMapping("/apply")
     public String showApplyForm(@RequestParam("jobId") Integer jobId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return "redirect:/login";
        }

        Candidate candidate = candidateService.findByUserID(loggedInUser.getId());
        if (candidate == null) {
            return "redirect:/login";
        }

        Job job = jobService.findByID(jobId);
        if (job == null) {
            model.addAttribute("error", "Không tìm thấy công việc.");
            return "redirect:/client/job/jobs";
        }

        model.addAttribute("job", job);
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

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return "redirect:/login";
        }

        Candidate candidate = candidateService.findByUserID(loggedInUser.getId());
        if (candidate == null) {
            return "redirect:/login";
        }

        Integer candidateId = candidate.getId();

        // Kiểm tra nếu đã ứng tuyển rồi
        if (applicationService.hasApplied(candidateId, jobId)) {
            redirectAttributes.addFlashAttribute("error", "Bạn đã ứng tuyển công việc này rồi.");
            redirectAttributes.addAttribute("jobId", jobId);
            return "redirect:/applications/apply";
        }

        if (resumeFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn tệp CV để tải lên.");
            redirectAttributes.addAttribute("jobId", jobId);
            return "redirect:/applications/apply";
        }

        try {
            String resumeUrl = fileStorageService.storeFile(resumeFile);

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
        return "redirect:/applications/apply";
    }
}