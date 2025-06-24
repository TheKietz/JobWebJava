/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.model.Employer;
import com.job.model.User;
import com.job.repository.EmployerRepository;
import com.job.service.client.EmployerService;
import com.job.service.client.UserService;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/app/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmployerService employerService;
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView profilePage(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            return new ModelAndView("redirect:/app/login");
        }
        User user = userService.findByID(loggedInUser.getId());
        
        Employer employer = employerService.findByUserID(loggedInUser.getId());
        ModelAndView mav = new ModelAndView("app/user/profile"); 
        mav.addObject("user", user);
        mav.addObject("employer", employer);
        return mav;
    }
}
