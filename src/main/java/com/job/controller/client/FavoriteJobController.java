package com.job.controller.client;

import com.job.model.Job;
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

    @GetMapping
    public ModelAndView list(HttpSession session) {
        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        if (candidateId == null) {
            return new ModelAndView("redirect:/login");
        }

        List<Job> jobs = favoriteJobService.getFavorites(candidateId);
        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/favorite-job/favorite-jobs.jsp");
        mav.addObject("jobs", jobs);
        return mav;
    }

    @PostMapping("/add/{jobId}")
    public String save(@PathVariable("jobId") int jobId, HttpSession session) {
        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        if (candidateId == null) {
            return "redirect:/login";
        }
        
        favoriteJobService.save(candidateId, jobId);
        return "redirect:/jobs/detail/" + jobId;
    }

    @PostMapping("/remove/{jobId}")
    public String remove(@PathVariable("jobId") int jobId, HttpSession session,
            @RequestParam(name = "redirect", required = false) String redirect) {
        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        if (candidateId == null) {
            return "redirect:/login";
        }
        favoriteJobService.remove(candidateId, jobId);
        return "redirect:" + (redirect != null ? redirect : "/favorite-jobs");
    }
}
