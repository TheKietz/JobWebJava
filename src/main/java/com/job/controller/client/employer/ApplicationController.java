package com.job.controller.client.employer;

import com.job.service.client.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client/application")
public class ApplicationController {
    String path = "/WEB-INF/views/client/";
    @Autowired private ApplicationService applicationService;
    
    public String getHomePage(Model model) {
        model.addAttribute("body", "/WEB-INF/views/client/client/home.jsp");
        return "client/layout/main.jsp";
    }
}
