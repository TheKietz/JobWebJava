/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.client;

import com.job.model.Employer;
import com.job.service.client.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployerController {
    @Autowired
    private EmployerService employerService;

    // Danh sách công ty
    @RequestMapping(value = "/employers", method = RequestMethod.GET)
    public ModelAndView listEmployers(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page) {

        List<Employer> allEmployers = (keyword != null && !keyword.isBlank())
                ? employerService.search(keyword)
                : employerService.findAll();

        int pageSize = 6;
        int totalPages = employerService.countPages(allEmployers, pageSize);
        List<Employer> paginated = employerService.getPage(allEmployers, page, pageSize);

        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/employer/employers.jsp");
        mav.addObject("employers", paginated);
        mav.addObject("page", page);
        mav.addObject("totalPages", totalPages);
        mav.addObject("keyword", keyword);
        return mav;
    }

    // Chi tiết công ty
    @RequestMapping(value = "/employers/detail/{id}", method = RequestMethod.GET)
    public ModelAndView employerDetail(@PathVariable("id") int id) {
        Employer employer = employerService.findByID(id);

        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/employer/employer-detail.jsp");
        mav.addObject("employer", employer);
        return mav;
    }
}
