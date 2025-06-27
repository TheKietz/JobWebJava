package com.job.controller.client;

import com.job.model.Employer;
import com.job.service.client.FavoriteEmployerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/favorite-employers")
public class FavoriteEmployerController {

    @Autowired
    private FavoriteEmployerService favoriteEmployerService;

    @GetMapping
    public ModelAndView list(HttpSession session) {
        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        if (candidateId == null) {
            return new ModelAndView("redirect:/login");
        }

        List<Employer> employers = favoriteEmployerService.getFavorites(candidateId);
        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/favorite-employer/favorite-employers.jsp");
        mav.addObject("employers", employers);
        return mav;
    }

    @PostMapping("/add/{employerId}")
    public String save(@PathVariable("employerId") int employerId, HttpSession session,
            @RequestParam(value = "redirect", required = false) String redirect) {
        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        if (candidateId == null) {
            return "redirect:/login";
        }

        favoriteEmployerService.save(candidateId, employerId);
        return "redirect:/employers/detail/" + employerId;
    }

    @PostMapping("/remove/{employerId}")
    public String remove(@PathVariable("employerId") int employerId, HttpSession session,
            @RequestParam(value = "redirect", required = false) String redirect) {
        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        if (candidateId == null) {
            return "redirect:/login";
        }

        favoriteEmployerService.remove(candidateId, employerId);
        return "redirect:" + (redirect != null ? redirect : "/favorite-employers");
    }
}
