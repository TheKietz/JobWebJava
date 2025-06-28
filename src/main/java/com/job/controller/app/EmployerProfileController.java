package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.model.Employer;
import com.job.model.Job;
import com.job.model.User;
import com.job.service.client.EmployerService;
import com.job.service.client.JobService;
import com.job.service.client.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/profile")
public class EmployerProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private JobService jobService;
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView profilePage(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            return new ModelAndView("redirect:/app/login");
        }
        User user = userService.findByID(loggedInUser.getId());
        
        Employer employer = employerService.findByUserID(loggedInUser.getId());
        int countJob = jobService.countJobByEmpID(employer.getId());
        ModelAndView mav = new ModelAndView("app/layout/main"); 
        mav.addObject("user", user);
        mav.addObject("employer", employer);
        mav.addObject("countJob", countJob);
        mav.addObject("body","/WEB-INF/views/app/user/profile.jsp");
        return mav;
    }
}
