/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.enums.CommonEnums.JobStatus;
import com.job.model.Employer;
import com.job.model.Job;
import com.job.model.User;
import com.job.service.client.EmployerService;
import com.job.service.client.JobService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 11090
 */
@Controller
@RequestMapping("/app/jobs")
public class JobPostController {

    @Autowired
    private JobService jobService;
    @Autowired
    private EmployerService employerService;

    @GetMapping("")
    public ModelAndView jobPostPage(Model model,
            HttpSession session,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        // Kiểm tra đăng nhập
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            System.out.println("Unauthorized access to /app/jobs, redirecting to login");
            return new ModelAndView("redirect:/app/login");
        }

        // Lấy employerId từ user
        int employerId = employerService.findEmployerIdByUserId(loggedInUser.getId());
        final String trimmedKeyword = (keyword != null && !keyword.isBlank()) ? keyword.trim() : null;

        // Tìm kiếm jobs
        List<Job> allJobs = (trimmedKeyword == null)
                ? jobService.findByEmployerID(employerId)
                : jobService.employerSearch(trimmedKeyword, employerId);

        // Tính toán phân trang
        size = Math.max(1, size);
        int totalPages = jobService.countPages(allJobs, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allJobs.size());
        List<Job> paginatedJobs = allJobs.subList(fromIndex, toIndex);

        // Log
        System.out.println("Search keyword: '" + trimmedKeyword + "', Found " + allJobs.size() + " jobs");
        System.out.println("Page " + page + "/" + totalPages + ", showing jobs " + fromIndex + " to " + (toIndex - 1));

        // Trả về view
        ModelAndView mav = new ModelAndView("app/layout/main");
        mav.addObject("jobs", paginatedJobs);
        mav.addObject("title", "Danh sách tin đã đăng");
        mav.addObject("keyword", trimmedKeyword);
        mav.addObject("page", page);
        mav.addObject("totalPages", totalPages);
        mav.addObject("body", "/WEB-INF/views/app/job/posted-job.jsp");
        return mav;
    }

    @ModelAttribute("job")
    public Job jobPrePopulate(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRole() == CommonEnums.Role.EMPLOYER) {
            int employerId = employerService.findEmployerIdByUserId(loggedInUser.getId());
            Job job = new Job();
            job.setEmployerId(employerId); // Pre-set employerId
            return job;
        }
        return new Job(); // Default if not logged in (will fail auth check later)
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            System.out.println("Unauthorized access to /app/jobs/add, redirecting to login");
            return "redirect:/admin/login";
        }

        model.addAttribute("job", new Job());
        model.addAttribute("body", "/WEB-INF/views/app/job/post-job.jsp");
        return "app/layout/main";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("job") Job job,
            BindingResult result,
            Model model,
            HttpSession session,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "keyword", required = false) String keyword) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            return "redirect:/app/login";
        }

        int employerId = employerService.findEmployerIdByUserId(loggedInUser.getId());
        System.out.println("Employer ID from session: " + employerId);
        Employer emp = employerService.findByID(employerId);
        if (emp == null) {
            result.rejectValue("employerId", "error.employerId", "Invalid Employer ID: must exist.");
        }
        if (result.hasErrors()) {
            model.addAttribute("body", "/WEB-INF/views/app/job/post-job.jsp");
            return "app/layout/main";
        }

        try {
            if (job.getId() == null) {
                job.setStatus(JobStatus.PENDING);
                job.setCreatedAt(LocalDateTime.now());
                job.setEmployerId(employerId); // Ensure it's set
                jobService.add(job);
            } else {
                jobService.update(job);
            }
            model.addAttribute("success", "Job saved successfully!");
            return "redirect:/app/jobs?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Failed to save job: " + e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
        }
        model.addAttribute("body", "/WEB-INF/views/app/job/post-job.jsp");
        return "app/layout/main";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(BigDecimal.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text != null && !text.isBlank()) {
                    // Loại bỏ dấu chấm ngăn cách
                    String cleaned = text.replaceAll("\\.", "");
                    setValue(new BigDecimal(cleaned));
                } else {
                    setValue(null);
                }
            }
        });

        // Formatter cho LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text != null && !text.isBlank()) {
                    setValue(LocalDateTime.parse(text, formatter));
                } else {
                    setValue(null);
                }
            }

            @Override
            public String getAsText() {
                LocalDateTime value = (LocalDateTime) getValue();
                return value != null ? value.format(formatter) : "";
            }
        });
    }
}
