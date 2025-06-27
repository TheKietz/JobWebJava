/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.app;

import com.job.dto.EmployerProfileDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.job.enums.CommonEnums;
import com.job.model.Employer;
import com.job.model.User;
import com.job.service.client.EmployerService;
import com.job.service.client.JobService;
import com.job.service.client.UserService;
import com.job.service.storage.FileStorageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author 11090
 */
@Controller
@RequestMapping("/app/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private JobService jobService;

    @Autowired
    private FileStorageService fileStorageService;
    @Value("${file.upload-dir}")
    private String uploadDirLocation;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView profilePage(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            return new ModelAndView("redirect:/app/login");
        }
        User user = userService.findByID(loggedInUser.getId());

        Employer employer = employerService.findByUserID(loggedInUser.getId());
        int countJob = jobService.countJobByEmpID(employer.getId());
        ModelAndView mav = new ModelAndView("app/layout/main");
        mav.addObject("user", user);
        mav.addObject("employer", employer);
        mav.addObject("countJob", countJob);
        mav.addObject("body", "/WEB-INF/views/app/user/profile.jsp");
        return mav;
    }

    @PostMapping("/update")
    public String updateProfile(
            @Valid @ModelAttribute("profile") EmployerProfileDTO profile,
            BindingResult result,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> System.out.println("Validation error: " + err.getDefaultMessage()));
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi nhập thông tin. Vui lòng kiểm tra lại.");
            return "redirect:/app/profile";
        }

        try {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
                return "redirect:/app/login";
            }

            User formUser = profile.getUser();
            Employer formEmployer = profile.getEmployer();

            User existingUser = userService.findByID(loggedInUser.getId());
            Employer existingEmployer = employerService.findByUserID(loggedInUser.getId());

            if (existingUser == null || existingEmployer == null) {
                throw new RuntimeException("Không tìm thấy dữ liệu trong CSDL");
            }

            existingUser.setFullName(formUser.getFullName());
            existingUser.setEmail(formUser.getEmail());
            existingUser.setPhone(formUser.getPhone());

            existingEmployer.setCompanyName(formEmployer.getCompanyName());
            existingEmployer.setCompanySize(formEmployer.getCompanySize());
            existingEmployer.setAddress(formEmployer.getAddress());
            existingEmployer.setWebsite(formEmployer.getWebsite());
            existingEmployer.setDescription(formEmployer.getDescription());

            if (imageFile != null && !imageFile.isEmpty()) {
                String logoUrl = fileStorageService.storeFile(imageFile);
                existingEmployer.setLogoUrl(logoUrl);
            }

            userService.update(existingUser);
            employerService.update(existingEmployer);

            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }

        return "redirect:/app/profile";
    }
}
