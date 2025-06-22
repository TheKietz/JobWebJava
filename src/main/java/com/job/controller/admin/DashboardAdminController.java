package com.job.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.job.enums.CommonEnums.Role;
import com.job.model.User;

@Controller
public class DashboardAdminController {

    @RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboardPage(HttpSession session) {
        // Kiểm tra xem đã đăng nhập và đúng role chưa
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/dashboard, redirecting to login");
            return new ModelAndView("redirect:/admin/login");
        }
       ModelAndView mav = new ModelAndView("admin/layout/main");
        mav.addObject("body", "/WEB-INF/views/admin/dashboard.jsp");
        return mav;
    }
}
