
package com.job.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardAdminController {
    @RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboardPage() {
        ModelAndView mav = new ModelAndView("admin/layout/main");
        mav.addObject("body", "/WEB-INF/views/admin/dashboard.jsp");
        return mav;
    }
}
