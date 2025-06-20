package com.job.controller.admin;

import com.job.enums.CommonEnums.Role;
import com.job.model.Employer;
import com.job.model.User;
import com.job.service.EmployerAdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/employers")
public class EmployerAdminController {

    @Autowired
    private EmployerAdminService employerService;

    @GetMapping
    public String list(Model model,
                       HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/employers, redirecting to login");
            return "redirect:/admin/login";
        }

        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        System.out.println("Request URL: /admin/employers?page=" + page + "&size=" + size + "&keyword=" + trimmedKeyword);

        size = Math.max(1, size);
        List<Employer> employers = employerService.search(trimmedKeyword);
        System.out.println("Search keyword: '" + trimmedKeyword + "', Found " + employers.size() + " employers");

        int totalPages = employerService.countPages(employers, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        List<Employer> pagedEmployers = employerService.getPage(employers, page, size);
        System.out.println("Page: " + page + ", Size: " + size + ", Paged Employers=" + pagedEmployers.size());

        model.addAttribute("employers", pagedEmployers);
        model.addAttribute("keyword", trimmedKeyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        if (pagedEmployers.isEmpty()) {
            model.addAttribute("message", "No employers found.");
        }
        model.addAttribute("body", "/WEB-INF/views/admin/employer/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session,
                      @RequestParam(value = "size", defaultValue = "5") int size,
                      @RequestParam(value = "keyword", required = false) String keyword) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/employers/add, redirecting to login");
            return "redirect:/admin/login";
        }

        model.addAttribute("employer", new Employer());
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("body", "/WEB-INF/views/admin/employer/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, HttpSession session,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/employers/edit/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }

        Employer employer = employerService.findByID(id);
        if (employer == null) {
            System.out.println("Employer not found for ID: " + id);
            model.addAttribute("error", "Employer not found");
            return "redirect:/admin/employers?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        }
        model.addAttribute("employer", employer);
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("body", "/WEB-INF/views/admin/employer/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, HttpSession session,
                         @RequestParam(value = "size", defaultValue = "5") int size,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/employers/delete/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }

        try {
            if (employerService.deleteByID(id)) {
                redirectAttributes.addFlashAttribute("success", "Employer deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Cannot delete employer due to linked records (e.g., jobs).");
            }
        } catch (Exception e) {
            System.err.println("Error deleting employer ID=" + id + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Failed to delete employer: " + e.getMessage());
        }
        return "redirect:/admin/employers?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("employer") Employer employer,
                       BindingResult result,
                       Model model,
                       HttpSession session,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/employers/save, redirecting to login");
            return "redirect:/admin/login";
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error));
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/employer/form.jsp");
            return "admin/layout/main";
        }

        try {
            if (employer.getId() == null) {
                employerService.add(employer);
                System.out.println("Added new employer: " + employer.getCompanyName());
            } else {
                employerService.update(employer);
                System.out.println("Updated employer: " + employer.getCompanyName());
            }
            return "redirect:/admin/employers?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        } catch (Exception e) {
            System.err.println("Error saving employer: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Failed to save employer: " + e.getMessage());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/employer/form.jsp");
            return "admin/layout/main";
        }
    }
}
