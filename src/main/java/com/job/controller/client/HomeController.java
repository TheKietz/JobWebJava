package com.job.controller.client;
import com.job.model.Job;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.job.service.client.JobService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {
    @Autowired
    private JobService jobService;
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView homePage() {
        List<Job> jobs = jobService.findAll();

        if (jobs.size() > 6) {
            jobs = jobs.subList(0, 6); // lấy 6 job đầu
        }

        ModelAndView mav = new ModelAndView("client/layout/main");
        mav.addObject("body", "/WEB-INF/views/client/home.jsp");
        mav.addObject("jobs", jobs);
        return mav;
    }
}

