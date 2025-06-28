package com.job.controller.admin;

import com.job.enums.CommonEnums;
import com.job.model.ServicePackage;
import com.job.model.User;
import com.job.service.ServicePackageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // BẠN ĐANG THIẾU DÒNG NÀY
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/service_packages")
public class AdminPackageController {

    @Autowired
    private ServicePackageService packageService;

    @GetMapping
    public String listPackages(Model model,
                               HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/applications, redirecting to login");
            return "redirect:/admin/login";
        }
        
        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        size = Math.max(1, size); 
        List<ServicePackage> packages = packageService.search(trimmedKeyword);
        
        int totalPages = packageService.countPages(packages, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        //
        List<ServicePackage> pagedPackages = packageService.getPage(packages, page, size);
        model.addAttribute("packages", pagedPackages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("body", "/WEB-INF/views/admin/package/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("package", new ServicePackage());
        return "admin/package/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("package") ServicePackage pack,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        packageService.add(pack);
        return "redirect:/admin/packages" + (keyword != null && !keyword.isBlank() ? "?keyword=" + keyword.trim() : "");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        ServicePackage servicePackage = packageService.findByID(id);
        if (servicePackage == null) {
            return "redirect:/admin/packages?error=notfound";
        }
        model.addAttribute("package", servicePackage);
        return "admin/package/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id,
                         @RequestParam(value = "keyword", required = false) String keyword) {
        packageService.deleteByID(id);
        return "redirect:/admin/packages" + (keyword != null && !keyword.isBlank() ? "?keyword=" + keyword.trim() : "");
    }
}
