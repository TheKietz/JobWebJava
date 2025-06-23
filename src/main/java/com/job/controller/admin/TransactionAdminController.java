package com.job.controller.admin;

import com.job.enums.CommonEnums;
import com.job.enums.CommonEnums.TransactionStatus;
import com.job.model.Transaction;
import com.job.model.User;

import com.job.service.ServicePackageService;
import com.job.service.TransactionService;
import com.job.service.client.UserService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/transactions")
public class TransactionAdminController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService; // để load danh sách user

    @Autowired
    private ServicePackageService packageService; // để load gói dịch vụ

    @GetMapping
    public String list(Model model,
                       HttpSession session,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != CommonEnums.Role.ADMIN) {
            System.out.println("Unauthorized access to /admin/applications, redirecting to login");
            return "redirect:/admin/login";
        }
        
        final String trimmedKeyword = (keyword != null) ? keyword.trim() : null;
        size = Math.max(1, size); 
        List<Transaction> trans = transactionService.search(trimmedKeyword);
        
        int totalPages = transactionService.countPages(trans, size);
        page = Math.max(1, Math.min(page, totalPages == 0 ? 1 : totalPages));
        List<Transaction> pagedTransactions = transactionService.getPage(trans, page, size);
        
        model.addAttribute("transactions", pagedTransactions);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size);
        model.addAttribute("body", "/WEB-INF/views/admin/transaction/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("users", userService.findAll()); // load user dropdown
        model.addAttribute("packages", packageService.findAll()); // load package dropdown
        model.addAttribute("statuses", TransactionStatus.values());
        return "admin/transaction/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Transaction transaction) {
        if (transaction.getCreatedAt() == null) {
            transaction.setCreatedAt(LocalDateTime.now());
        }
        if (transaction.getId() == null) {
            transactionService.add(transaction);
        } else {
            transactionService.update(transaction);
        }
        return "redirect:/admin/transactions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Transaction transaction = transactionService.findById(id);
        if (transaction == null) {
            return "redirect:/admin/transactions";
        }
        model.addAttribute("transaction", transaction);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("packages", packageService.findAll());
        model.addAttribute("statuses", TransactionStatus.values());
        return "admin/transaction/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        transactionService.deleteByID(id);
        return "redirect:/admin/transactions";
    }
}
