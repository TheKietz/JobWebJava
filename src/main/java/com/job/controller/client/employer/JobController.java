package com.job.controller.client.employer;

import com.job.model.Employer;
import com.job.model.Job;
import com.job.model.User;
import com.job.service.client.EmployerService;
import com.job.service.client.JobService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/jobs")
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private EmployerService employerService;

    @GetMapping
    public ModelAndView jobPage() {
        List<Job> jobs = jobService.findAll();

        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/job/jobs.jsp");
        mav.addObject("jobs", jobs);
        return mav;
    }

    // Chi tiết thông tin công việc 
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView jobDetail(@PathVariable("id") int id) {
        Job job = jobService.findByID(id);

        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/job/job-detail.jsp");
        mav.addObject("job", job);
        return mav;
    }

    @PostMapping("/employer/jobs/save")
    public String saveJob(@Valid @ModelAttribute("job") Job job, BindingResult result, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        Employer employer = employerService.findByUserID(user.getId());
        job.setEmployerId(employer.getId());
        jobService.add(job);
        return "redirect:/employer/jobs";
    }
}
