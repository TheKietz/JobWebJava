package com.job.controller.client;

import com.job.enums.CommonEnums;
import com.job.model.Candidate;
import com.job.model.Job;
import com.job.model.User;
import com.job.service.client.CandidateService;
import com.job.service.client.FavoriteJobService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/favorite-jobs")
public class FavoriteJobController {

    @Autowired
    private FavoriteJobService favoriteJobService;
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

        List<Job> jobs = favoriteJobService.getFavorites(candidate.getId());
        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/favorite-job/favorite-jobs.jsp");
        mav.addObject("jobs", jobs);
        return mav;
    }

    @PostMapping("/add/{jobId}")
    public String save(@PathVariable("jobId") int jobId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return "redirect:/login";
        }

        Candidate candidate = candidateService.findByUserID(loggedInUser.getId());
        if (candidate == null) {
            return "redirect:/login";
        }

        favoriteJobService.save(candidate.getId(), jobId);
        return "redirect:/jobs/detail/" + jobId;
    }

    @PostMapping("/remove/{jobId}")
    public String remove(@PathVariable("jobId") int jobId, HttpSession session,
                         @RequestParam(name = "redirect", required = false) String redirect) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals(CommonEnums.Role.CANDIDATE)) {
            return "redirect:/login";
        }

        Candidate candidate = candidateService.findByUserID(loggedInUser.getId());
        if (candidate == null) {
            return "redirect:/login";
        }

        favoriteJobService.remove(candidate.getId(), jobId);
        return "redirect:" + (redirect != null ? redirect : "/favorite-jobs");
    }
}
