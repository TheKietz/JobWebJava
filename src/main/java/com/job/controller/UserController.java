/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller;

import com.job.model.User;
import com.job.service.UserAdminService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author 11090
 */
@Controller
@RequestMapping("/admin/users")
public class UserController {
    @Autowired
    private UserAdminService userService;
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("body", "/WEB-INF/views/admin/user/list.jsp");
        return "admin/layout/main";
    }
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("createAt", LocalDate.now());
        model.addAttribute("user", new User());        
        model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
        return "admin/layout/main";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") User user) {
        if (user.getUserID() == null) {
            user.setCreatedAt(LocalDate.now());
            userService.add(user);
        } else {
            userService.update(user);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findByID(id));
        model.addAttribute("body", "/WEB-INF/views/admin/user/form.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        userService.deleteByID(id);
        return "redirect:/admin/users";
    }
}
