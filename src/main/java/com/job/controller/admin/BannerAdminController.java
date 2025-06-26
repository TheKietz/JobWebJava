package com.job.controller.admin;

import com.job.enums.CommonEnums;
import com.job.enums.CommonEnums.Role;
import com.job.model.Banner;
import com.job.model.User;
import com.job.service.client.BannerService;
// import com.job.service.storage.FileStorageService; // Bỏ import này
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/banners")
public class BannerAdminController {

    private final BannerService bannerService; // Sử dụng final và inject qua constructor

    // Bỏ FileStorageService ra khỏi đây
    // private final FileStorageService fileStorageService;

    @Autowired
    public BannerAdminController(BannerService bannerService) { // Bỏ FileStorageService khỏi constructor
        this.bannerService = bannerService;
        // this.fileStorageService = fileStorageService; // Bỏ dòng này
    }

    @GetMapping
    public String list(Model model,
                       HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/banners, redirecting to login");
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
            model.addAttribute("message", "No banners found.");
        }
        model.addAttribute("body", "/WEB-INF/views/admin/banner/list.jsp"); // SỬA ĐỔI TẠI ĐÂY: Dùng list.jsp
        return "admin/layout/main";
    }

    @GetMapping("/add")
    public ModelAndView showAddForm(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            return new ModelAndView("redirect:/admin/login");
        }
        ModelAndView mav = new ModelAndView("admin/layout/main");
        mav.addObject("body", "/WEB-INF/views/admin/banner/form.jsp");
        mav.addObject("banner", new Banner());
        mav.addObject("positions", CommonEnums.BannerPosition.values());
        mav.addObject("statuses", CommonEnums.Status.values());
        mav.addObject("isEdit", false);
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") int id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            return new ModelAndView("redirect:/admin/login");
        }

        Banner banner = bannerService.getBannerById(id);
        if (banner == null) {
            return new ModelAndView("redirect:/admin/banners");
        }        

        ModelAndView mav = new ModelAndView("admin/layout/main");
        mav.addObject("body", "/WEB-INF/views/admin/banner/form.jsp");
        mav.addObject("banner", banner);
        mav.addObject("positions", CommonEnums.BannerPosition.values());
        mav.addObject("statuses", CommonEnums.Status.values());
        mav.addObject("isEdit", true);
        return mav;
    }

    @PostMapping("/save")
    public String saveBanner(@Valid @ModelAttribute("banner") Banner banner,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             HttpSession session,
                             Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            return "redirect:/admin/login";
        }

        if (result.hasErrors()) {
            // Khi có lỗi validation, cần trả về lại form với dữ liệu đã nhập và thông báo lỗi
            model.addAttribute("positions", CommonEnums.BannerPosition.values());
            model.addAttribute("statuses", CommonEnums.Status.values());
            model.addAttribute("isEdit", banner.getId() != null); // Đặt lại isEdit
            model.addAttribute("body", "/WEB-INF/views/admin/banner/form.jsp");
            return "admin/layout/main";
        }

        try {
            // Loại bỏ logic xử lý imageUrl ở đây, vì nó đã được chuyển vào BannerService
            // Nếu bạn có một hidden field cho imageUrl trong form, Spring sẽ tự động binding.
            // Service sẽ đảm bảo imageUrl được giữ lại hoặc cập nhật.

            // Gọi phương thức saveBanner/updateBanner từ service, truyền cả file
            if (banner.getId() == null) {
                bannerService.saveBanner(banner, imageFile);
                redirectAttributes.addFlashAttribute("success", "Banner added successfully!");
            } else {
                bannerService.updateBanner(banner, imageFile);
                redirectAttributes.addFlashAttribute("success", "Banner updated successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to save banner: " + e.getMessage());
            // Điều hướng trở lại form tương ứng (thêm hoặc sửa) nếu có lỗi
            return "redirect:" + (banner.getId() != null ? "/admin/banners/edit/" + banner.getId() : "/admin/banners/add");
        }

        return "redirect:/admin/banners";
    }

    @GetMapping("/delete/{id}")
    public String deleteBanner(@PathVariable("id") int id, HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            return "redirect:/admin/login";
        }
        try {
            bannerService.deleteBanner(id);
            redirectAttributes.addFlashAttribute("success", "Banner deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to delete banner: " + e.getMessage());
        }
        return "redirect:/admin/banners";
    }
}