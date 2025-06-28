/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.model.ServicePackage;
import com.job.model.User;
import com.job.service.ServicePackageService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author 11090
 */
@Controller
@RequestMapping("/app/service_packages")
public class PackageServiceController {
    @Autowired
    private ServicePackageService packageService;

    @GetMapping
    public String listPackages(Model model,
                               HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
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
        model.addAttribute("body", "/WEB-INF/views/app/package/list.jsp");
        return "app/layout/main";
    }
}
