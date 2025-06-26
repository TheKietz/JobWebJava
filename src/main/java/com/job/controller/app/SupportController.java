package com.job.controller.app;

import com.job.enums.CommonEnums;
import com.job.model.User;
import com.job.model.Feedback; // Import model Feedback
import com.job.service.client.FeedbackService; // Import FeedbackService
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Import PostMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile; // Import MultipartFile
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Import RedirectAttributes

import java.util.List;

/**
 *
 * @author 11090
 */
@Controller
@RequestMapping("/app")
public class SupportController {

    private final FeedbackService feedbackService; // Khai báo FeedbackService

    // Inject FeedbackService qua constructor
    public SupportController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/supports")
    public String supportPage(@RequestParam(defaultValue = "report") String tab, 
                                Model model,
                                HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Đảm bảo người dùng đã đăng nhập và là EMPLOYER mới có thể truy cập trang hỗ trợ
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.EMPLOYER) {
            return "redirect:/app/login"; 
        }
        
        String bodyView;

        switch (tab) {
            case "suggest":
                bodyView = "/WEB-INF/views/app/support/suggest.jsp";
                break;
            case "consult":
                bodyView = "/WEB-INF/views/app/support/consult.jsp";
                break;
            case "hotline":
                bodyView = "/WEB-INF/views/app/support/hotline.jsp";
                break;
            case "docs":
                bodyView = "/WEB-INF/views/app/support/docs.jsp";
                break;
            default: // Mặc định là tab 'report'
                bodyView = "/WEB-INF/views/app/support/report.jsp";
                break;
        }

        model.addAttribute("body", bodyView);
        model.addAttribute("activeTab", tab);
        model.addAttribute("feedback", new Feedback()); 
        return "app/layout/main";
    }

    /**
     * Xử lý yêu cầu gửi báo cáo vi phạm từ form report.jsp
     */
    @PostMapping("/supports/submit-report")
    public String submitReport(Feedback feedback, 
                               @RequestParam("attachments") List<MultipartFile> attachments,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để gửi báo cáo.");
            return "redirect:/app/login";
        }
        feedback.setUserId(loggedInUser.getId());
        try {
            feedbackService.createFeedback(feedback, attachments);
            redirectAttributes.addFlashAttribute("successMessage", "Báo cáo của bạn đã được gửi thành công!");
        } catch (Exception e) {
            System.err.println("Lỗi khi gửi báo cáo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi gửi báo cáo. Vui lòng thử lại!");
        }
        return "redirect:/app/supports?tab=report";
    }

    /**
     * Xử lý yêu cầu gửi góp ý sản phẩm từ form suggest.jsp
     */
    @PostMapping("/supports/submit-suggest")
    public String submitSuggest(Feedback feedback, 
                               @RequestParam("attachments") List<MultipartFile> attachments, 
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để gửi góp ý.");
            return "redirect:/app/login";
        }
        
        feedback.setUserId(loggedInUser.getId());

        try {
            feedbackService.createFeedback(feedback, attachments);
            redirectAttributes.addFlashAttribute("successMessage", "Góp ý của bạn đã được gửi thành công!");
        } catch (Exception e) {
            System.err.println("Lỗi khi gửi góp ý: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi gửi góp ý. Vui lòng thử lại!");
        }
        return "redirect:/app/supports?tab=suggest";
    }
}