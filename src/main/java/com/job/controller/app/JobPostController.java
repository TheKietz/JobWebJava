/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.model.Job;
import com.job.model.User;
import com.job.repository.JobRepository;
import com.job.service.client.EmployerService;
import com.job.service.client.JobService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 11090
 */
@Controller
@RequestMapping("/app/job-post")
public class JobPostController {
    @Autowired 
    private JobService jobService;
    @Autowired
    private EmployerService employerService;
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView jobPostPage(HttpSession session) {
        // Kiểm tra xem đã đăng nhập và đúng role chưa
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            System.out.println("Unauthorized access to /admin/dashboard, redirecting to login");
            return new ModelAndView("redirect:/app/login");
        }
        String title = "Danh sách tin đã đăng";
        int employerId = employerService.findEmployerIdByUserId(loggedInUser.getId());
        List<Job> jobs = jobService.findByEmployerID(employerId);
        ModelAndView mav = new ModelAndView("app/layout/main");
        mav.addObject("jobs",jobs);
        mav.addObject("title",title);
        mav.addObject("body", "/WEB-INF/views/app/job/post-job.jsp");
        return mav;
    }
}
