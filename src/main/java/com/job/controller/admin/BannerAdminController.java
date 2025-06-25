/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.controller.admin;

import com.job.enums.CommonEnums;
import com.job.enums.CommonEnums.Role;
import com.job.model.Banner;
import com.job.model.User;
import com.job.service.client.BannerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.MediaType;

@Controller
@RequestMapping("/admin/banners")
public class BannerAdminController {

    @Autowired
    private BannerService bannerService;
    private static final String UPLOAD_DIR = "D:/Web_Java/jobwebjava/target/JobWebJava/upload/";

    // LIST
    @GetMapping
    public String list(Model model,
            HttpSession session,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/candidates, redirecting to login");
            return "redirect:/admin/login";
        }

        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        System.out.println("Request URL: /admin/candidates?page=" + page + "&size=" + size + "&keyword=" + trimmedKeyword);
        size = Math.max(1, size);
        List<Banner> banners = bannerService.search(trimmedKeyword);
        System.out.println("Search keyword: '" + trimmedKeyword + "', Found " + banners.size() + " candidates");

        int totalPages = bannerService.countPages(banners, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        List<Banner> pagedBanners = bannerService.getPage(banners, page, size);

        model.addAttribute("banners", pagedBanners);
        model.addAttribute("keyword", trimmedKeyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        if (pagedBanners.isEmpty()) {
            model.addAttribute("message", "No candidates found.");
        }
        model.addAttribute("body", "/WEB-INF/views/admin/banner/list.jsp");
        return "admin/layout/main";
    }

    // SHOW FORM CREATE
    @GetMapping("/add")
    public ModelAndView showCreateForm(Model model) {
        ModelAndView mav = new ModelAndView("admin/layout/main");
        mav.addObject("body", "/WEB-INF/views/admin/banner/form.jsp");
        mav.addObject("banner", new Banner());
        mav.addObject("positions", CommonEnums.BannerPosition.values());
        mav.addObject("statuses", CommonEnums.Status.values());
        mav.addObject("isEdit", false);
        return mav;
    }

    // CREATE
    @RequestMapping(value = "/save", method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String saveBanner(@Valid @ModelAttribute("banner") Banner banner,
            @RequestParam("imageFile") MultipartFile imageFile,
            HttpServletRequest request,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("positions", CommonEnums.BannerPosition.values());
            model.addAttribute("statuses", CommonEnums.Status.values());
            model.addAttribute("isEdit", false);
            model.addAttribute("body", "/WEB-INF/views/admin/banner/form.jsp");
            return "admin/layout/main";
        }

        try {
            if (!imageFile.isEmpty()) {
                String uploadPath = request.getServletContext().getRealPath("/uploads/");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String filename = imageFile.getOriginalFilename();
                imageFile.transferTo(new File(uploadDir, filename));
                banner.setImageUrl(filename); // lưu tên file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (banner.getId() == null) {
            bannerService.saveBanner(banner);
        } else {
            bannerService.updateBanner(banner);
        }

        return "redirect:/admin/banners";
    }

    // SHOW FORM EDIT
    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") int id) {
        Banner banner = bannerService.getBannerById(id);

        ModelAndView mav = new ModelAndView("admin/layout/main");
        mav.addObject("body", "/WEB-INF/views/admin/banner/form.jsp");
        mav.addObject("banner", banner);
        mav.addObject("positions", CommonEnums.BannerPosition.values());
        mav.addObject("statuses", CommonEnums.Status.values());
        mav.addObject("isEdit", true);
        return mav;
    }

    // UPDATE
    @PostMapping("/edit")
    public String updateBanner(@ModelAttribute("banner") Banner banner,
            @RequestParam("imageFile") MultipartFile imageFile,
            BindingResult result) {
        try {
            if (!imageFile.isEmpty()) {
                String filename = imageFile.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + filename);
                Files.write(path, imageFile.getBytes());

                banner.setImageUrl(filename);
            }
            bannerService.updateBanner(banner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/banners";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteBanner(@PathVariable("id") int id) {
        bannerService.deleteBanner(id);
        return "redirect:/admin/banners";
    }
}
