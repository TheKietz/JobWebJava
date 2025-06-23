package com.job.controller.admin;

import com.job.enums.CommonEnums.Role;
import com.job.model.Application;
import com.job.model.Job;
import com.job.model.User;
import com.job.service.ApplicationAdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ApplicationAdminController.class);
    private static final String ROLE_ADMIN = "ADMIN";

    @GetMapping
    public String list(Model model, HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/applications, redirecting to login");
            return "redirect:/admin/login";
        }

        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        size = Math.max(1, size);      
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
        Object loggedInUser = session.getAttribute("loggedInUser");
        Object userRoleObj = session.getAttribute("userRole");

        if (loggedInUser == null) {
            logger.warn("Unauthorized access to /admin/applications/add: No logged-in user, redirecting to login");
            return "redirect:/admin/login";
        }

        String userRole = null;
        if (userRoleObj instanceof String) {
            userRole = (String) userRoleObj;
        } else if (userRoleObj instanceof Role) {
            userRole = ((Role) userRoleObj).name();
        }

        if (userRole == null || !ROLE_ADMIN.equalsIgnoreCase(userRole)) {
            logger.warn("Unauthorized access to /admin/applications/add: Role={} is not ADMIN, redirecting to login", userRole);
            return "redirect:/admin/login";
        }

        model.addAttribute("application", new Application());
        model.addAttribute("appliedAt", LocalDate.now());
        model.addAttribute("body", "/WEB-INF/views/admin/application/form.jsp");
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
        Application application = applicationService.findByID(id);
        if (application == null) {
            model.addAttribute("error", "application not found");
            return "redirect:/admin/applications?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        }
        model.addAttribute("application", application);
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("body", "/WEB-INF/views/admin/application/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        Object userRoleObj = session.getAttribute("userRole");

        if (loggedInUser == null) {
            logger.warn("Unauthorized access to /admin/applications/delete/{}: No logged-in user, redirecting to login", id);
            return "redirect:/admin/login";
        }

        String userRole = null;
        if (userRoleObj instanceof String) {
            userRole = (String) userRoleObj;
        } else if (userRoleObj instanceof Role) {
            userRole = ((Role) userRoleObj).name();
        }

        if (userRole == null || !ROLE_ADMIN.equalsIgnoreCase(userRole)) {
            logger.warn("Unauthorized access to /admin/applications/delete/{}: Role={} is not ADMIN, redirecting to login", id, userRole);
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
        Object loggedInUser = session.getAttribute("loggedInUser");
        Object userRoleObj = session.getAttribute("userRole");

        if (loggedInUser == null) {
            logger.warn("Unauthorized access to /admin/applications/save: No logged-in user, redirecting to login");
            return "redirect:/admin/login";
        }

        String userRole = null;
        if (userRoleObj instanceof String) {
            userRole = (String) userRoleObj;
        } else if (userRoleObj instanceof Role) {
            userRole = ((Role) userRoleObj).name();
        }

        if (userRole == null || !ROLE_ADMIN.equalsIgnoreCase(userRole)) {
            logger.warn("Unauthorized access to /admin/applications/save: Role={} is not ADMIN, redirecting to login", userRole);
            return "redirect:/admin/login";
        }

        if (result.hasErrors()) {
            logger.error("Validation errors in application save: {}", result.getAllErrors());
            model.addAttribute("body", "/WEB-INF/views/admin/application/form.jsp");
            return "admin/layout/main";
        }

        try {
            if (application.getId() == null) {
                application.setAppliedAt(LocalDateTime.now());
                applicationService.add(application);
                model.addAttribute("success", "Application added successfully");
            } else {
                applicationService.update(application);
                model.addAttribute("success", "Application updated successfully");
            }
        } catch (Exception e) {
            logger.error("Error saving application: {}", e.getMessage());
            model.addAttribute("error", "Failed to save application: " + e.getMessage());
            model.addAttribute("body", "/WEB-INF/views/admin/application/form.jsp");
            return "admin/layout/main";
        }

        return "redirect:/admin/applications?page=1&size=" + size;
    }
}
