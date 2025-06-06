/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service;

import com.job.model.Employer;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


/**
 *
 * @author 11090
 */
@Service
public class EmployerAdminService {
    private final List<Employer> EmployerList = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    
    @PostConstruct
    public void init() {
    add(new Employer(1, 1, "FPT Software", "https://www.fptsoftware.com", "Công ty phần mềm hàng đầu Việt Nam.", "https://giaoduc247.vn/uploads/082021/images/uef(1).png"));
    add(new Employer(2, 2, "VNG Corporation", "https://www.vng.com.vn", "Công ty công nghệ và Internet hàng đầu Việt Nam.", "https://example.com/logo/vng.jpg"));
    add(new Employer(3, 3, "Tiki.vn", "https://www.tiki.vn", "Sàn thương mại điện tử Việt Nam.", "https://example.com/logo/tiki.png"));
    add(new Employer(4, 4, "VNPay", "https://www.vnpay.vn", "Giải pháp thanh toán điện tử tiện lợi.", "https://example.com/logo/vnpay.png"));
    add(new Employer(5, 5, "Shopee Việt Nam", "https://shopee.vn", "Sàn thương mại điện tử phổ biến tại Đông Nam Á.", "https://example.com/logo/shopee.jpg"));
    add(new Employer(6, 6, "Grab Việt Nam", "https://www.grab.com/vn", "Ứng dụng đặt xe và giao hàng lớn nhất Việt Nam.", "https://example.com/logo/grab.jpeg"));
    add(new Employer(7, 7, "Techcombank", "https://www.techcombank.com.vn", "Ngân hàng TMCP uy tín.", "https://example.com/logo/techcombank.png"));
    add(new Employer(8, 8, "Zalo", "https://zalo.me", "Ứng dụng nhắn tin hàng đầu Việt Nam.", "https://example.com/logo/zalo.png"));
    add(new Employer(9, 9, "Viettel", "https://www.viettel.com.vn", "Tập đoàn viễn thông quân đội.", "https://example.com/logo/viettel.jpg"));
    add(new Employer(10,10, "VinGroup", "https://www.vingroup.net", "Tập đoàn đầu tư đa lĩnh vực lớn nhất Việt Nam.", "https://example.com/logo/vingroup.png"));
    add(new Employer(11,11, "Tinhvan Group", "https://www.tinhvan.com", "Chuyên cung cấp giải pháp phần mềm và dịch vụ IT.", "https://example.com/logo/tinhvan.jpg"));
    add(new Employer(12,12, "CMC Corporation", "https://www.cmc.com.vn", "Tập đoàn CNTT hàng đầu Việt Nam.", "https://example.com/logo/cmc.png"));
    add(new Employer(13,13, "Axon Active", "https://www.axonactive.com", "Phát triển phần mềm theo mô hình Agile.", "https://example.com/logo/axonactive.jpg"));
    add(new Employer(14,14, "NashTech", "https://www.nashtechglobal.com", "Công ty phần mềm toàn cầu.", "https://example.com/logo/nashtech.jpeg"));
    add(new Employer(15,15, "KMS Technology", "https://www.kms-technology.com", "Phát triển và kiểm thử phần mềm.", "https://example.com/logo/kms.png"));
}


    public List<Employer> getPage(List<Employer> list, int page, int size) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(from + size, list.size());
        if (from >= list.size()) {
            return new ArrayList<>();
        }
        return list.subList(from, to);
    }

    public List<Employer> findAll() {
        return EmployerList;
    }

    public Employer findByID(int id) {
        return EmployerList.stream()
                .filter(c -> c.getEmployerID() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Employer employer) {
        employer.setEmployerID(idGenerator.getAndIncrement());
        EmployerList.add(employer);
    }

    public void update(Employer employer) {
        for (int i = 0; i < EmployerList.size(); i++) {
            if (EmployerList.get(i).getEmployerID() == employer.getEmployerID()) {
                Employer existingEmployer = EmployerList.get(i);
                EmployerList.set(i, employer);
                return;
            }
        }
    }

    public void deleteByID(int id) {
        EmployerList.removeIf(c -> c.getEmployerID() == id);
    }

    public List<Employer> search(String keyword) {
    if (keyword == null || keyword.isBlank()) {
        System.out.println("No keyword provided, returning full list: " + EmployerList.size() + " employers");
        return EmployerList;
    }
    final String trimmedKeyword = keyword.trim();
    System.out.println("Search keyword: '" + trimmedKeyword + "'");
    List<Employer> results = EmployerList.stream()
            .filter(c -> c.getCompanyName().toLowerCase().contains(trimmedKeyword.toLowerCase())
                    || c.getWebsite().toLowerCase().contains(trimmedKeyword.toLowerCase()))
            .collect(Collectors.toList());
    System.out.println("Search results for '" + trimmedKeyword + "': " + results.size() + " employers");
    return results;
}

    public int countPages(List<Employer> list, int size) {
        return (int) Math.ceil((double) list.size() / size);
    }

    public Employer findByUserID(int userID) {
        return EmployerList.stream()
                .filter(c -> c.getUserID() == userID)
                .findFirst()
                .orElse(null);
    }
}
