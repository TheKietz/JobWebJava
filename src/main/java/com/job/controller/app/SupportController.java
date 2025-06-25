/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 11090
 */
@Controller
@RequestMapping("/app")
public class SupportController {

    @GetMapping("/supports")
    public String supportPage(@RequestParam(defaultValue = "report") String tab, 
                                Model model,
                                HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            return "redirect:/app/login";
        }
        String bodyView;

        switch (tab) {
            case "suggest":
                bodyView = "/WEB-INF/views/app/support/suggest.jsp";
                break;
            case "consult":
                bodyView = "/WEB-INF/views/app/support/consult.jsp";
                break;
            case "hotline":
                bodyView = "/WEB-INF/views/app/support/hotline.jsp";
                break;
            case "docs":
                bodyView = "/WEB-INF/views/app/support/docs.jsp";
                break;
            default:
                bodyView = "/WEB-INF/views/app/support/report.jsp";
                break;
        }

        model.addAttribute("body", bodyView);
        model.addAttribute("activeTab", tab);
        return "app/layout/main";
    }
}
