package com.job.controller.client;

import com.job.enums.CommonEnums;
import com.job.model.Candidate;
import com.job.model.Employer;
import com.job.model.Job;
import com.job.model.User;
import com.job.service.client.CandidateService;
import com.job.service.client.EmployerService;
import com.job.service.client.FavoriteJobService;
import com.job.service.client.JobService;
import com.job.service.client.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private FavoriteJobService favoriteJobService;
    @Autowired
    private UserService userService;
    @Autowired
    private CandidateService candidateService;

    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public ModelAndView jobPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        List<Job> jobs = jobService.search(keyword);

        List<Job> pageJobs = jobService.getPage(jobs, page, 6);
        int totalPages = jobService.countPages(jobs, 6);

        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/job/jobs.jsp");
        mav.addObject("jobs", pageJobs);
        mav.addObject("page", page);
        mav.addObject("totalPages", totalPages);
        mav.addObject("keyword", keyword);
        return mav;
    }

    // Chi tiết thông tin công việc 
    @RequestMapping(value = "/jobs/detail/{id}", method = RequestMethod.GET)
    public ModelAndView jobDetail(@PathVariable("id") int id, HttpSession session) {
        Job job = jobService.findByID(id);
        if (job == null) {
            return new ModelAndView("redirect:/jobs");
        }

        Employer employer = employerService.findByID(job.getEmployerId());
        User employerUser = userService.findByID(employer.getUserId());

        boolean isFavorite = false;
        Integer userId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (userId != null && "CANDIDATE".equalsIgnoreCase(role)) {
            Candidate candidate = candidateService.findByUserID(userId);
            if (candidate != null) {
                isFavorite = favoriteJobService.isFavorited(candidate.getId(), id);
            }
        }

        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/job/job-detail.jsp");
        mav.addObject("job", job);
        mav.addObject("employer", employer);
        mav.addObject("employerEmail", employerUser != null ? employerUser.getEmail() : "");
        mav.addObject("isFavorite", isFavorite);
        return mav;
    }
}