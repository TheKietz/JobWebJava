/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller;

import com.job.model.Candidate;
import com.job.service.CandidateAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/candidates")
public class CandidateAdminController {
    @Autowired
    private CandidateAdminService candidateService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("body", "/WEB-INF/views/admin/candidate/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("candidate", candidateService.findByID(id));
        model.addAttribute("body", "/WEB-INF/views/admin/candidate/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        candidateService.deleteByID(id);
        return "redirect:/admin/candidates";
    }
        @PostMapping("/save")
    public String save(@Valid @ModelAttribute("candidate") Candidate candidate, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("body", "/WEB-INF/views/admin/candidate/form.jsp");
            return "admin/layout/main";
        }
        if (candidate.getCandidateID() == null) {
            candidateService.add(candidate);
        } else {
            candidateService.update(candidate);
        }
        return "redirect:/admin/candidates";
    }
}
