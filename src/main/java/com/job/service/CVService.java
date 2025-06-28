package com.job.service;

import com.job.model.CV;
import com.job.repository.CVRepository;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CVService {

    @Autowired
    private CVRepository cvRepository;

    public List<CV> getByCandidateId(int candidateId) {
        return cvRepository.findByCandidateId(candidateId);
    }

    public void create(CV cv) {
        cvRepository.save(cv);
    }

    public void delete(int id) {
        cvRepository.delete(id);
    }

//    public String generateDocx(CV cv) {
//        String fileName = UUID.randomUUID().toString() + "_cv.docx";
//        Path outputPath = Paths.get("uploads/cv/", fileName);
//
//        try (XWPFDocument doc = new XWPFDocument()) {
//            XWPFParagraph title = doc.createParagraph();
//            title.setAlignment(ParagraphAlignment.CENTER);
//            XWPFRun runTitle = title.createRun();
//            runTitle.setText(cv.getTitle());
//            runTitle.setBold(true);
//            runTitle.setFontSize(16);
//
//            XWPFParagraph summary = doc.createParagraph();
//            summary.setAlignment(ParagraphAlignment.LEFT);
//            XWPFRun runSummary = summary.createRun();
//            runSummary.setText(cv.getSummary());
//
//            Files.createDirectories(outputPath.getParent()); // ensure folder exists
//            try (FileOutputStream out = new FileOutputStream(outputPath.toFile())) {
//                doc.write(out);
//            }
//
//            return "/uploads/cv/" + fileName; // relative URL for download
//        } catch (IOException e) {
//            throw new RuntimeException("Lỗi khi tạo file CV", e);
//        }
//    }

}
