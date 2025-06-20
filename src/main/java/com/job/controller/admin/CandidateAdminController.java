
package com.job.controller.admin;

import com.job.enums.CommonEnums.Role;
import com.job.model.Candidate;
import com.job.model.User;
import com.job.service.CandidateAdminService;
import com.job.service.UserAdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("/admin/candidates")
public class CandidateAdminController {

    @Autowired
    private CandidateAdminService candidateService;

    @Autowired
    private UserAdminService userService;

    @GetMapping
    public String list(Model model,
                       HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/candidates, redirecting to login");
            return "redirect:/admin/login";
        }

        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        System.out.println("Request URL: /admin/candidates?page=" + page + "&size=" + size + "&keyword=" + trimmedKeyword);

        size = Math.max(1, size);
        List<Candidate> candidates = candidateService.search(trimmedKeyword);
        System.out.println("Search keyword: '" + trimmedKeyword + "', Found " + candidates.size() + " candidates");

        int totalPages = candidateService.countPages(candidates, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        List<Candidate> pagedCandidates = candidateService.getPage(candidates, page, size);
        System.out.println("Page: " + page + ", Size: " + size + ", Paged Candidates=" + pagedCandidates.size());

        model.addAttribute("candidates", pagedCandidates);
        model.addAttribute("keyword", trimmedKeyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        if (pagedCandidates.isEmpty()) {
            model.addAttribute("message", "No candidates found.");
        }
        model.addAttribute("body", "/WEB-INF/views/admin/candidate/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session,
                      @RequestParam(value = "size", defaultValue = "5") int size,
                      @RequestParam(value = "keyword", required = false) String keyword) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/candidates/add, redirecting to login");
            return "redirect:/admin/login";
        }

        model.addAttribute("candidate", new Candidate());
        model.addAttribute("users", userService.findAvailableCandidates());
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("body", "/WEB-INF/views/admin/candidate/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, HttpSession session,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/candidates/edit/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }

        Candidate candidate = candidateService.findByID(id);
        if (candidate == null) {
            System.out.println("Candidate not found for ID: " + id);
            model.addAttribute("error", "Candidate not found");
            return "redirect:/admin/candidates?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        }
        model.addAttribute("candidate", candidate);
        model.addAttribute("users", userService.findAvailableCandidates());
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("body", "/WEB-INF/views/admin/candidate/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, HttpSession session,
                         @RequestParam(value = "size", defaultValue = "5") int size,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/candidates/delete/" + id + ", redirecting to login");
            return "redirect:/admin/login";
        }

        try {
            if (candidateService.deleteByID(id)) {
                redirectAttributes.addFlashAttribute("success", "Candidate deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Cannot delete candidate due to linked records.");
            }
        } catch (Exception e) {
            System.err.println("Error deleting candidate ID=" + id + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Failed to delete candidate: " + e.getMessage());
        }
        return "redirect:/admin/candidates?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("candidate") Candidate candidate,
                       BindingResult result,
                       Model model,
                       HttpSession session,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/candidates/save, redirecting to login");
            return "redirect:/admin/login";
        }

        // Validate userId exists and is CANDIDATE
        User user = userService.findByID(candidate.getUserId());
        if (user == null || user.getRole() != Role.CANDIDATE) {
            result.rejectValue("userId", "error.userId", "Invalid User ID: Must be an existing CANDIDATE user.");
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error));
            model.addAttribute("users", userService.findAvailableCandidates());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/candidate/form.jsp");
            return "admin/layout/main";
        }

        try {
            if (candidate.getId() == null) {
                candidateService.add(candidate);
                System.out.println("Added new candidate: user_id=" + candidate.getUserId());
            } else {
                candidateService.update(candidate);
                System.out.println("Updated candidate: id=" + candidate.getId());
            }
            return "redirect:/admin/candidates?page=1&size=" + size + "&keyword=" + (keyword != null ? keyword : "");
        } catch (DataIntegrityViolationException e) {
            System.err.println("Data integrity violation: " + e.getMessage());
            String errorMsg = e.getMessage().contains("Duplicate entry")
                    ? "User ID is already associated with another candidate."
                    : "Failed to save candidate due to database constraint.";
            model.addAttribute("error", errorMsg);
            model.addAttribute("users", userService.findAvailableCandidates());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/candidate/form.jsp");
            return "admin/layout/main";
        } catch (Exception e) {
            System.err.println("Error saving candidate: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Failed to save candidate: " + e.getMessage());
            model.addAttribute("users", userService.findAvailableCandidates());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("body", "/WEB-INF/views/admin/candidate/form.jsp");
            return "admin/layout/main";
        }
    }
}
