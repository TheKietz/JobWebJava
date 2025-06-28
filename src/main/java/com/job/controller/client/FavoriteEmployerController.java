package com.job.controller.client;

import com.job.enums.CommonEnums;
import com.job.model.Candidate;
import com.job.model.Employer;
import com.job.model.User;
import com.job.service.client.CandidateService;
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
    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public ModelAndView list(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return new ModelAndView("redirect:/login");
        }

        Candidate candidate = candidateService.findByUserID(loggedInUser.getId());
        if (candidate == null) {
            return new ModelAndView("redirect:/login");
        }

        List<Employer> employers = favoriteEmployerService.getFavorites(candidate.getId());
        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/favorite-employer/favorite-employers.jsp");
        mav.addObject("employers", employers);
        return mav;
    }

    @PostMapping("/add/{employerId}")
    public String save(@PathVariable("employerId") int employerId, HttpSession session,
            @RequestParam(value = "redirect", required = false) String redirect) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return "redirect:/login";
        }

        Candidate candidate = candidateService.findByUserID(loggedInUser.getId());
        if (candidate == null) {
            return "redirect:/login";
        }

        favoriteEmployerService.save(candidate.getId(), employerId);
        return "redirect:/employers/detail/" + employerId;
    }

    @PostMapping("/remove/{employerId}")
    public String remove(@PathVariable("employerId") int employerId, HttpSession session,
            @RequestParam(value = "redirect", required = false) String redirect) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return "redirect:/login";
        }

        Candidate candidate = candidateService.findByUserID(loggedInUser.getId());
        if (candidate == null) {
            return "redirect:/login";
        }

        favoriteEmployerService.remove(candidate.getId(), employerId);
        return "redirect:" + (redirect != null ? redirect : "/favorite-employers");
    }
}
