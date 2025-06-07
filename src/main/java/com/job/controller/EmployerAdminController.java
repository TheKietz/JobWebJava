/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller;

import com.job.model.Employer;
import com.job.service.EmployerAdminService;
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

@Controller
@RequestMapping("/admin/employers")
public class EmployerAdminController {

        @Autowired
    private EmployerAdminService employerService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        List<Employer> employers = employerService.search(trimmedKeyword);

        // Phân trang
        int totalPages = employerService.countPages(employers, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        List<Employer> pagedEmployers = employerService.getPage(employers, page, size);

        model.addAttribute("employers", pagedEmployers);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("body", "/WEB-INF/views/admin/employer/list.jsp");
        return "admin/layout/main";
    }

    // Chỉnh sửa
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Employer employer = employerService.findByID(id);
        if (employer == null) {
            return "redirect:/admin/employers";
        }
        model.addAttribute("employer", employer);
        model.addAttribute("body", "/WEB-INF/views/admin/employer/form.jsp");
        return "admin/layout/main";
    }

    // Xoá
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        employerService.deleteByID(id);
        return "redirect:/admin/employers";
    }

    // Lưu
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("employer") Employer employer,
                       BindingResult result,
                       Model model,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        if (result.hasErrors()) {
            model.addAttribute("body", "/WEB-INF/views/admin/employer/form.jsp");
            return "admin/layout/main";
        }
        if (employer.getEmployerID() == null) {
            employerService.add(employer);
        } else {
            employerService.update(employer);
        }
        return "redirect:/admin/employers?page=1&size=" + size;
    }
}
    

