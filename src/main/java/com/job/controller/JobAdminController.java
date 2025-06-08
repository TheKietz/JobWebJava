package com.job.controller;

import com.job.model.Job;
import com.job.service.JobAdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/jobs")
public class JobAdminController {
    @Autowired
    private JobAdminService jobService;

    @GetMapping
    public String list(Model model,
                       HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        if (session.getAttribute("loggedInUser") == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            System.out.println("Unauthorized access to /admin/jobs, redirecting to login");
            return "redirect:/admin/login";
        }

        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        List<Job> jobs = jobService.search(trimmedKeyword);

        int totalPages = jobService.countPages(jobs, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        List<Job> pagedJobs = jobService.getPage(jobs, page, size);

        model.addAttribute("jobs", pagedJobs);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("body", "/WEB-INF/views/admin/job/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            System.out.println("Unauthorized access to /admin/jobs/add, redirecting to login");
            return "redirect:/admin/login";
        }

        model.addAttribute("job", new Job());
        model.addAttribute("postedAt", LocalDate.now());
        model.addAttribute("body", "/WEB-INF/views/admin/job/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            System.out.println("Unauthorized access to /admin/jobs/edit/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }

        Job job = jobService.findByID(id);
        if (job == null) {
            return "redirect:/admin/jobs";
        }
        model.addAttribute("job", job);
        model.addAttribute("body", "/WEB-INF/views/admin/job/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            System.out.println("Unauthorized access to /admin/jobs/delete/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }

        jobService.deleteByID(id);
        return "redirect:/admin/jobs";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("job") Job job,
                       BindingResult result,
                       Model model,
                       HttpSession session,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        if (session.getAttribute("loggedInUser") == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            System.out.println("Unauthorized access to /admin/jobs/save, redirecting to login");
            return "redirect:/admin/login";
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
            model.addAttribute("body", "/WEB-INF/views/admin/job/form.jsp");
            return "admin/layout/main";
        }
        if (job.getJobID() == null) {
            job.setPostedAt(LocalDate.now());
            jobService.add(job);
        } else {
            jobService.update(job);
        }
        return "redirect:/admin/jobs?page=1&size=" + size;
    }
}