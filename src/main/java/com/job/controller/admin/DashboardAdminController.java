package com.job.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.job.enums.CommonEnums.Role;
import com.job.model.Job;
import com.job.model.User;
import com.job.service.CandidateAdminService;
import com.job.service.EmployerAdminService;
import com.job.service.JobAdminService;
import com.job.service.TransactionService;
import com.job.service.UserAdminService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/admin/dashboard")
public class DashboardAdminController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private EmployerAdminService employerService;
    @Autowired
    private CandidateAdminService candidateService;
    @Autowired
    private JobAdminService jobService;
    @Autowired
    private UserAdminService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView dashboardPage(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/dashboard, redirecting to login");
            return new ModelAndView("redirect:/admin/login");
        }

        BigDecimal totalRevenueToday = transactionService.getTotalRevenueToday();
        List<Job> jobs = jobService.topTenJob();
        int countEmployers = employerService.countEmployer();
        int countCandidates = candidateService.countCandidate();

        // Thống kê đăng ký người dùng (7 ngày gần nhất)
        Map<String, Long> registrationStats = userService.getRegistrationStatsByRoleAndDate(7);
        List<String> registrationDates = registrationStats.keySet().stream()
                .map(key -> key.split("_")[1])
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        List<Integer> newCandidates = registrationDates.stream()
                .map(date -> registrationStats.getOrDefault("CANDIDATE_" + date, 0L).intValue())
                .collect(Collectors.toList());

        List<Integer> newEmployers = registrationDates.stream()
                .map(date -> registrationStats.getOrDefault("EMPLOYER_" + date, 0L).intValue())
                .collect(Collectors.toList());

        // Thống kê doanh thu theo gói dịch vụ
        Map<String, BigDecimal> revenueByPackage = transactionService.getRevenueByPackage();
        List<String> packageNames = revenueByPackage.keySet().stream().collect(Collectors.toList());
        List<BigDecimal> packageRevenues = packageNames.stream()
                .map(revenueByPackage::get)
                .collect(Collectors.toList());
        BigDecimal totalRevenue = packageRevenues.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Serialize dữ liệu thành JSON
        try {
            String registrationDatesJson = objectMapper.writeValueAsString(registrationDates);
            String newCandidatesJson = objectMapper.writeValueAsString(newCandidates);
            String newEmployersJson = objectMapper.writeValueAsString(newEmployers);
            String packageNamesJson = objectMapper.writeValueAsString(packageNames);
            String packageRevenuesJson = objectMapper.writeValueAsString(packageRevenues);

            ModelAndView mav = new ModelAndView("admin/layout/main");
            mav.addObject("revenueToday", totalRevenueToday);
            mav.addObject("countEmployers", countEmployers);
            mav.addObject("countCandidates", countCandidates);
            mav.addObject("top10Jobs", jobs);
            mav.addObject("registrationDatesJson", registrationDatesJson);
            mav.addObject("newCandidatesJson", newCandidatesJson);
            mav.addObject("newEmployersJson", newEmployersJson);
            mav.addObject("packageNamesJson", packageNamesJson);
            mav.addObject("packageRevenuesJson", packageRevenuesJson);
            mav.addObject("totalRevenue", totalRevenue);
            mav.addObject("body", "/WEB-INF/views/admin/dashboard.jsp");
            return mav;
        } catch (Exception e) {
            System.out.println("Error serializing JSON: " + e.getMessage());
            return new ModelAndView("redirect:/admin/error");
        }
    }
}
