package com.job.controller.admin;

import com.job.model.Application;
import com.job.service.ApplicationAdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@RequestMapping("/admin/applications")
public class ApplicationAdminController {
    @Autowired
    private ApplicationAdminService applicationService;

    @GetMapping
    public String list(Model model,
                       HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        if (session.getAttribute("loggedInUser") == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            System.out.println("Unauthorized access to /admin/applications, redirecting to login");
            return "redirect:/admin/login";
        }

        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        List<Application> applications = applicationService.search(trimmedKeyword);

        int totalPages = applicationService.countPages(applications, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        List<Application> pagedApplications = applicationService.getPage(applications, page, size);

        model.addAttribute("applications", pagedApplications);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("body", "/WEB-INF/views/admin/application/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            System.out.println("Unauthorized access to /admin/applications/add, redirecting to login");
            return "redirect:/admin/login";
        }

        model.addAttribute("application", new Application());
        model.addAttribute("appliedAt", LocalDate.now());
        model.addAttribute("body", "/WEB-INF/views/admin/application/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            System.out.println("Unauthorized access to /admin/applications/edit/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }

        Application application = applicationService.findByID(id);
        if (application == null) {
            return "redirect:/admin/applications";
        }
        model.addAttribute("application", application);
        model.addAttribute("body", "/WEB-INF/views/admin/application/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            System.out.println("Unauthorized access to /admin/applications/delete/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }

        applicationService.deleteByID(id);
        return "redirect:/admin/applications";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("application") Application application,
                       BindingResult result,
                       Model model,
                       HttpSession session,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        if (session.getAttribute("loggedInUser") == null || !"ADMIN".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            System.out.println("Unauthorized access to /admin/applications/save, redirecting to login");
            return "redirect:/admin/login";
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
            model.addAttribute("body", "/WEB-INF/views/admin/application/form.jsp");
            return "admin/layout/main";
        }
        if (application.getId() == null) {
            application.setAppliedAt(LocalDateTime.now());
            applicationService.add(application);
        } else {
            applicationService.update(application);
        }
        return "redirect:/admin/applications?page=1&size=" + size;
    }
}