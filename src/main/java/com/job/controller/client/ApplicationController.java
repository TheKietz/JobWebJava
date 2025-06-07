package com.job.controller.client;

import com.job.service.client.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
    String path = "/WEB-INF/views/client/";
    @Autowired private ApplicationService applicationService;
    
    // Lấy ra trang chủ 
    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("body", path + "client/home.jsp");
        
        return "client/layout/main.jsp";
    }
}
