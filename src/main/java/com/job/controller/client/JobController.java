package com.job.controller.client;

import com.job.model.Job;
import com.job.service.client.JobService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JobController {
    @Autowired private JobService jobService;
    
    // Danh sách các công việc
    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public ModelAndView jobPage() {
        List<Job> jobs = jobService.findAll();
        
        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/jobs.jsp");
        mav.addObject("jobs", jobs);
        return mav;
    }
    
    // Chi tiết thông tin công việc 
    @RequestMapping(value = "/jobs/detail/{id}", method = RequestMethod.GET)
    public ModelAndView jobDetail(@PathVariable("id") int id) {
        Job job = jobService.findByID(id);
        
        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/job-detail.jsp");
        mav.addObject("job", job); 
        return mav;
    }
}
