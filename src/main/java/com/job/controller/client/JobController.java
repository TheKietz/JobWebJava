package com.job.controller.client;

import com.job.model.Employer;
import com.job.model.Job;
import com.job.model.User;
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

    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public ModelAndView jobPage(
            @RequestParam(value = "category", required = false) List<String> categories,
            @RequestParam(value = "jobType", required = false) List<String> jobTypes,
            @RequestParam(value = "salaryRange", required = false) List<String> salaryRanges,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        List<Job> filteredJobs = jobService.searchByFilters(categories, jobTypes, salaryRanges);

        List<Job> pageJobs = jobService.getPage(filteredJobs, page, 6);
        int totalPages = jobService.countPages(filteredJobs, 6);

        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/job/jobs.jsp");
        mav.addObject("jobs", pageJobs);
        mav.addObject("page", page);
        mav.addObject("totalPages", totalPages);
        return mav;
    }

    // Chi tiết thông tin công việc 
    @RequestMapping(value = "/jobs/detail/{id}", method = RequestMethod.GET)
    public ModelAndView jobDetail(@PathVariable("id") int id, HttpSession session) {
        Job job = jobService.findByID(id);

        Integer candidateId = (Integer) session.getAttribute("currentCandidateId");
        boolean isFavorite = false;
        if (candidateId != null) {
            isFavorite = favoriteJobService.isFavorited(candidateId, id);
        }

        Employer employer = employerService.findByID(job.getEmployerId());
        User user = userService.findByID(employer.getUserId()); // ✅ sửa chỗ này

        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/job/job-detail.jsp");
        mav.addObject("job", job);
        mav.addObject("isFavorite", isFavorite);
        mav.addObject("employer", employer);
        mav.addObject("employerEmail", user != null ? user.getEmail() : "");
        return mav;
    }

}
