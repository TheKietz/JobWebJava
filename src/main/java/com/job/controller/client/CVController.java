package com.job.controller.client;

import com.job.model.CV;
import com.job.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cv")
public class CVController {

    @Autowired
    private CVService cvService;

    @GetMapping
    public ModelAndView list(HttpSession session) {
        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        if (candidateId == null) return new ModelAndView("redirect:/login");

        List<CV> cvs = cvService.getByCandidateId(candidateId);
        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/cv/cv-list.jsp");
        mav.addObject("cvs", cvs);
        return mav;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CV cv, HttpSession session) {
        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        if (candidateId != null) {
            cv.setCandidateId(candidateId);
            cvService.create(cv);
        }
        return "redirect:/cv";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        cvService.delete(id);
        return "redirect:/cv";
    }
}