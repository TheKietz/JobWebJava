# 💼 Cổng thông tin Việc làm trực tuyến (Job Portal)

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Spring MVC](https://img.shields.io/badge/Spring_MVC-6.x-green?logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-Railway-blue?logo=mysql)
![JSP](https://img.shields.io/badge/JSP_Servlet-View-red)

**Job Portal** là một nền tảng tuyển dụng toàn diện được thiết kế với kiến trúc **Java Spring MVC**. Hệ thống đóng vai trò là cầu nối giữa Nhà tuyển dụng và Ứng viên, đồng thời cung cấp một hệ thống quản trị trung tâm (Admin CMS) mạnh mẽ để kiểm soát luồng dữ liệu và giao dịch tài chính.

## ✨ Tính năng cốt lõi

- **Kiểm soát truy cập dựa trên vai trò (RBAC):** Phân quyền Session-based chặt chẽ cho 3 luồng người dùng: `ADMIN`, `EMPLOYER`, và `CANDIDATE`.
- **Admin CMS & Thống kê thông minh:** - Quản lý toàn diện hệ sinh thái: Người dùng, Gói dịch vụ (Service Packages), Tin tuyển dụng và Giao dịch tài chính (Transactions).
  - Sử dụng `Jackson ObjectMapper` kết hợp JavaScript để vẽ biểu đồ thống kê trực quan theo thời gian thực (Real-time Dashboard).
- **Cổng Nhà tuyển dụng (Employer Portal):**
  - Quản lý hồ sơ doanh nghiệp và đăng tải tin tuyển dụng (Job Postings).
  - Tối ưu hóa Data Binding (`@InitBinder`) tự động định dạng dữ liệu tiền tệ (`BigDecimal`) và thời gian (`LocalDateTime`).
  - Hệ thống xử lý Upload file (`MultipartFile`) an toàn cho ảnh đại diện và tài liệu đính kèm.
- **Xử lý Toàn vẹn dữ liệu:** Kiểm soát chặt chẽ các ngoại lệ (`DataIntegrityViolationException`) để đảm bảo tính nhất quán của Database.

## 🛠 Nền tảng Công nghệ

- **Back-end:** Java, Spring Framework (Spring MVC thuần, cấu hình XML: `dispatcher-servlet.xml`, `web.xml`).
- **Cơ sở dữ liệu:** MySQL (Triển khai thực tế trên Cloud Platform - Railway).
- **Front-end:** JSP, JSTL, Bootstrap, Chart.js (vẽ biểu đồ).
- **Bảo mật & Tiện ích:** BCrypt (mã hóa mật khẩu), SLF4J (Ghi log hệ thống), CSRF Filter.

## ⚙️ Hướng dẫn cài đặt

1. Clone repository: `git clone https://github.com/your-username/JobPortal.git`
2. Tạo database MySQL và import file script (nếu có).
3. Cập nhật thông tin kết nối Database trong file `dispatcher-servlet.xml` (bean `dataSource`).
4. Build và deploy project lên Apache Tomcat Server (v10+).
