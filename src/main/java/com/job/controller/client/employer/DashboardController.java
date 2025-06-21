/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.client.employer;

import com.job.enums.CommonEnums;
import com.job.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 11090
 */
@Controller

public class DashboardController {
   @RequestMapping(value = "/employers/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboardPage(HttpSession session) {
        // Kiểm tra xem đã đăng nhập và đúng role chưa
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            System.out.println("Unauthorized access to /admin/dashboard, redirecting to login");
            return new ModelAndView("redirect:/employers/login");
        }
        ModelAndView mav = new ModelAndView("client/employer/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/employer/dashboard.jsp");
        return mav;
    }
}
