
package com.job.controller.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardProfileController {
    @RequestMapping(value = "/profiles/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboardPage() {
        ModelAndView mav = new ModelAndView("profile/layout/main");
        mav.addObject("body", "/WEB-INF/views/profile/dashboard.jsp");
        return mav;
    }
}
