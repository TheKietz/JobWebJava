package com.job.controller.admin;

import com.job.enums.CommonEnums.Role;
import com.job.model.Employer;
import com.job.model.Job;
import com.job.model.User;
import com.job.repository.EmployerRepository;
import com.job.service.EmployerAdminService;
import com.job.service.JobAdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/jobs")
public class JobAdminController {

    @Autowired
    private JobAdminService jobService;

    @Autowired
    private EmployerAdminService employerService;
    @Autowired
    private EmployerRepository employerRepository;

    @GetMapping
    public String list(Model model,
            HttpSession session,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/jobs, redirecting to login");
            return "redirect:/admin/login";
        }

        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        System.out.println("Request URL: /admin/jobs?page=" + page + "&size=" + size + "&keyword=" + trimmedKeyword);

        size = Math.max(1, size);
        List<Job> jobs = jobService.search(trimmedKeyword);
        System.out.println("Search keyword: '" + trimmedKeyword + "', Found " + jobs.size() + " jobs");

        int totalPages = jobService.countPages(jobs, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));

        List<Job> pagedJobs = jobService.getPage(jobs, page, size);

        Set<Integer> employerIds = pagedJobs.stream()
                .map(Job::getEmployerId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Integer, String> companyNames = new HashMap<>();
        for (Integer id : employerIds) {
            Employer employer = employerRepository.findByID(id);
            if (employer != null) {
                companyNames.put(id, employer.getCompanyName());
            }
        }
        model.addAttribute("jobs", pagedJobs);
        model.addAttribute("companyName", companyNames);
        model.addAttribute("keyword", trimmedKeyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        if (pagedJobs.isEmpty()) {
            model.addAttribute("message", "No jobs found.");
        }
        model.addAttribute("body", "/WEB-INF/views/admin/job/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/jobs/add, redirecting to login");
            return "redirect:/admin/login";
        }

        model.addAttribute("job", new Job());
        model.addAttribute("employers", employerService.findAll());
        model.addAttribute("body", "/WEB-INF/views/admin/job/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, HttpSession session,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "keyword", required = false) String keyword) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/jobs/edit/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }

        Job job = jobService.findByID(id);
        if (job == null) {
            System.out.println("Job not found for ID: " + id);
            model.addAttribute("error", "Job not found");
            return "redirect:/admin/jobs?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        }
        model.addAttribute("job", job);
        model.addAttribute("employers", employerService.findAll());
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("body", "/WEB-INF/views/admin/job/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, HttpSession session,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "keyword", required = false) String keyword,
            RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/jobs/delete/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }
        try {
            if (jobService.deleteByID(id)) {
                redirectAttributes.addFlashAttribute("success", "Job deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Cannot delete job due to linked records.");
            }
        } catch (Exception e) {
            System.err.println("Error deleting job ID=" + id + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Failed to delete job: " + e.getMessage());
        }
        return "redirect:/admin/jobs?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("job") Job job,
            BindingResult result,
            Model model,
            HttpSession session,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "keyword", required = false) String keyword) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/jobs/save, redirecting to login");
            return "redirect:/admin/login";
        }

        // Validate employerId exists
        if (employerService.findByID(job.getEmployerId()) == null) {
            result.rejectValue("employerId", "error.employerId", "Invalid Employer ID: must exist.");
        }
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error));
            model.addAttribute("employers", employerService.findAll());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/job/form.jsp");
            return "admin/layout/main";
        }
        try {
            if (job.getId() == null) {
                job.setCreatedAt(LocalDateTime.now());
                jobService.add(job);
                System.out.println("Added job: " + job.getTitle());
            } else {
                jobService.update(job);
                System.out.println("Updated job: " + job.getTitle());
            }
            return "redirect:/admin/jobs?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        } catch (DataIntegrityViolationException e) {
            System.err.println("Data integrity violation: " + e.getMessage());
            model.addAttribute("error", "Failed to save job due to database constraint: " + e.getMessage());
            model.addAttribute("employers", employerService.findAll());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/job/form.jsp");
            return "admin/layout/main";
        } catch (Exception e) {
            System.err.println("Error saving job: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Failed to save job: " + e.getMessage());
            model.addAttribute("employers", employerService.findAll());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/job/form.jsp");
            return "admin/layout/main";
        }

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
