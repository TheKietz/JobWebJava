/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.service;

import com.job.model.Candidate;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class CandidateAdminService {

    private final List<Candidate> CandidateList = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @PostConstruct
    public void init() {
        add(new Candidate(1,1, "https://example.com/resume/nguyen-a.pdf", "Ứng viên có 2 năm kinh nghiệm lập trình Java.", "Java, Spring Boot, MySQL"));
        add(new Candidate(2,2, "https://example.com/resume/le-b.pdf", "Sinh viên mới tốt nghiệp chuyên ngành Công nghệ thông tin.", "HTML, CSS, JavaScript"));
        add(new Candidate(3,3, "https://example.com/resume/tran-c.pdf", "Lập trình viên web với kinh nghiệm ReactJS và NodeJS.", "ReactJS, NodeJS, MongoDB"));
        add(new Candidate(4, 4,"https://example.com/resume/phan-d.pdf", "Kỹ sư dữ liệu với kiến thức về phân tích dữ liệu.", "Python, SQL, Pandas"));
        add(new Candidate(5, 5,"https://example.com/resume/do-e.pdf", "Chuyên gia thiết kế UI/UX sáng tạo và tỉ mỉ.", "Figma, Adobe XD, UX Research"));
        add(new Candidate(6, 6,"https://example.com/resume/ha-f.pdf", "Lập trình viên backend với kinh nghiệm trong phát triển API.", "Java, Spring MVC, REST"));
        add(new Candidate(7, 7,"https://example.com/resume/vo-g.pdf", "Ứng viên đam mê AI và Machine Learning.", "Python, TensorFlow, scikit-learn"));
        add(new Candidate(8, 8,"https://example.com/resume/hoang-h.pdf", "Tester chuyên nghiệp với kinh nghiệm kiểm thử tự động.", "Selenium, Postman, TestNG"));
        add(new Candidate(9, 9,"https://example.com/resume/bui-i.pdf", "Lập trình viên fullstack với 4 năm kinh nghiệm.", "Angular, Java, PostgreSQL"));
        add(new Candidate(10,10, "https://example.com/resume/dang-j.pdf", "Sinh viên thực tập ngành CNTT, yêu thích lập trình.", "C++, HTML, Git"));
        add(new Candidate(11, 11,"https://example.com/resume/ngo-k.pdf", "Data analyst với kỹ năng phân tích và trực quan hóa dữ liệu.", "Power BI, Excel, SQL"));
        add(new Candidate(12, 12,"https://example.com/resume/nguyen-l.pdf", "DevOps engineer với khả năng triển khai hệ thống CI/CD.", "Docker, Jenkins, AWS"));
        add(new Candidate(13, 13,"https://example.com/resume/le-m.pdf", "Ứng viên có khả năng giao tiếp tốt và làm việc nhóm.", "Scrum, Communication, Leadership"));
        add(new Candidate(14, 14,"https://example.com/resume/tran-n.pdf", "Sinh viên năm cuối ngành phần mềm, tìm kiếm cơ hội thực tập.", "Java, Firebase, Android"));
        add(new Candidate(15, 15,"https://example.com/resume/phan-o.pdf", "Front-end developer với kinh nghiệm dự án freelance.", "Vue.js, Bootstrap, REST API"));
    }

    public List<Candidate> getPage(List<Candidate> list, int page, int size) {
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

    public List<Candidate> findAll() {
        return CandidateList;
    }

    public Candidate findByID(int id) {
        return CandidateList.stream()
                .filter(c -> c.getCandidateID() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Candidate candidate) {
        candidate.setCandidateID(idGenerator.getAndIncrement());
        CandidateList.add(candidate);
    }

    public void update(Candidate candidate) {
        for (int i = 0; i < CandidateList.size(); i++) {
            if (CandidateList.get(i).getCandidateID() == candidate.getCandidateID()) {
                Candidate existingCandidate = CandidateList.get(i);

                CandidateList.set(i, candidate);
                return;
            }
        }
    }

    public Candidate findByUserID(int userID) {
        return CandidateList.stream()
                .filter(c -> c.getUserID() == userID)
                .findFirst()
                .orElse(null);
    }

    public void deleteByID(int id) {
        CandidateList.removeIf(c -> c.getCandidateID() == id);
    }

    public int countPages(List<Candidate> list, int size) {
        return (int) Math.ceil((double) list.size() / size);
    }
}
