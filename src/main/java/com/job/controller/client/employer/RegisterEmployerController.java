package com.job.controller.client.employer;

import com.job.dto.EmployerRegisterDTO;
import com.job.enums.CommonEnums.Role;
import com.job.enums.CommonEnums.Status;
import com.job.model.Employer;
import com.job.model.User;
import com.job.service.client.EmployerService;
import com.job.service.client.UserService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employers")
public class RegisterEmployerController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterEmployerController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        logger.info("Handling GET /employers/register");
        if (!model.containsAttribute("registerDTO")) {
            logger.debug("Adding new EmployerRegisterDTO to model");
            model.addAttribute("registerDTO", new EmployerRegisterDTO());
        }
        return "client/employer/register";
    }

    @PostMapping("/register")
    @Transactional
    public String processRegister(
            @Valid @ModelAttribute("registerDTO") EmployerRegisterDTO registerDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        logger.info("Processing POST /employers/register: email={}", registerDTO.getUser().getEmail());

        // Validate User
        User user = registerDTO.getUser();
        if (userService.findByEmail(user.getEmail()) != null) {
            result.addError(new FieldError("registerDTO", "user.email", "Email đã được sử dụng"));
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            result.addError(new FieldError("registerDTO", "user.password", "Mật khẩu không được để trống"));
        } else if (user.getPassword().length() < 8) {
            result.addError(new FieldError("registerDTO", "user.password", "Mật khẩu phải có ít nhất 8 ký tự"));
        } else if (!user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
            result.addError(new FieldError("registerDTO", "user.password", "Mật khẩu phải chứa chữ hoa, chữ thường và số"));
        } else if (!user.getPassword().equals(registerDTO.getPasswordConfirm())) {
            result.addError(new FieldError("registerDTO", "passwordConfirm", "Mật khẩu và xác nhận mật khẩu không khớp"));
        }
        if (user.getPhone() != null && !user.getPhone().isBlank() && !user.getPhone().matches("^\\d{10}$")) {
            result.addError(new FieldError("registerDTO", "user.phone", "Số điện thoại phải có 10 chữ số"));
        }

        // Validate Employer
        Employer employer = registerDTO.getEmployer();
        if (employer.getCompanyName() == null || employer.getCompanyName().isBlank()) {
            result.addError(new FieldError("registerDTO", "employer.companyName", "Tên công ty không được để trống"));
        }
        if (employer.getAddress() == null || employer.getAddress().isBlank()) {
            result.addError(new FieldError("registerDTO", "employer.address", "Địa chỉ không được để trống"));
        }
        if (employer.getWebsite() != null && !employer.getWebsite().isBlank() && 
            !employer.getWebsite().matches("^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
            result.addError(new FieldError("registerDTO", "employer.website", "Website không hợp lệ"));
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.warn("Validation error: {}", error));
            return "client/employer/register";
        }

        try {
            // Lưu User
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.EMPLOYER);
            user.setStatus(Status.ACTIVE);
            user.setCreatedAt(LocalDateTime.now());
            userService.add(user);
            logger.info("Added new user: id={}, email={}", user.getId(), user.getEmail());

            // Kiểm tra userId
            if (user.getId() == null) {
                logger.error("User ID is null after saving user: email={}", user.getEmail());
                throw new RuntimeException("Không thể lưu user: ID null");
            }

            // Lưu Employer
            employer.setUserId(user.getId());
            logger.debug("Setting employer userId={}", user.getId());
            employerService.add(employer);
            logger.info("Added new employer: companyName={}, userId={}", employer.getCompanyName(), employer.getUserId());

            redirectAttributes.addFlashAttribute("success", "Đăng ký nhà tuyển dụng thành công. Vui lòng đăng nhập.");
            return "redirect:/employers/login";
        } catch (Exception e) {
            logger.error("Error registering employer: email={}, error={}", user.getEmail(), e.getMessage(), e);
            model.addAttribute("error", "Lỗi khi đăng ký: " + e.getMessage());
            return "client/employer/register";
        }
    }
}
