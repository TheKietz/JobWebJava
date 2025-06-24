/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.model.Banner;
import com.job.model.User;
import com.job.service.client.BannerService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 11090
 */
@Controller
public class DashboardController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping("/app/dashboard")
    public ModelAndView dashboardPage(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            return new ModelAndView("redirect:/app/login");
        }

        List<Banner> banners = bannerService.getActiveBannersByPosition(CommonEnums.BannerPosition.HOMEPAGE_TOP);

        ModelAndView mav = new ModelAndView("app/layout/main");
        mav.addObject("banners", banners);
        mav.addObject("user", loggedInUser);
        mav.addObject("body", "/WEB-INF/views/app/dashboard.jsp");

        return mav;
    }
}
