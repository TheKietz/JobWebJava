<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Góp ý sản phẩm</title>
    <style>
        .support-wrapper {
            min-height: 600px; /* hoặc bất kỳ chiều cao nào bạn muốn */

        }
        .sidebar-support {
            background-color: whitesmoke;
            color: white;
            min-height: 600px;
        }

        .sidebar-support .list-group-item {
            background-color: whitesmoke;
            color: #000;
            border: none;
        }

        .sidebar-support .list-group-item.active {
            background-color: #ffffff;
        }

        .sidebar-support .list-group-item a {
            color: #000;
            text-decoration: none;
        }
    </style>


</head>
<div class="card support-wrapper">
    <div class="row no-gutters">
        <div class="col-md-3 sidebar-support p-0">
            <ul class="list-group list-group-flush">
                <li class="list-group-item ${activeTab == 'report' ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/app/supports?tab=report">
                        <i class="fa fa-envelope me-2"></i> Yêu cầu hỗ trợ & Báo cáo vi phạm
                    </a>
                </li>
                <li class="list-group-item ${activeTab == 'suggest' ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/app/supports?tab=suggest">
                        <i class="fa fa-comment-dots me-2"></i> Góp ý sản phẩm
                    </a>
                </li>
                <li class="list-group-item ${activeTab == 'consult' ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/app/supports?tab=consult">
                        <i class="fa fa-user-tie me-2"></i> Tư vấn tuyển dụng
                    </a>
                </li>
                <li class="list-group-item ${activeTab == 'hotline' ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/app/supports?tab=hotline">
                        <i class="fa fa-phone me-2"></i> Hotline CSKH & Hỗ trợ dịch vụ
                    </a>
                </li>
                <li class="list-group-item ${activeTab == 'docs' ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/app/supports?tab=docs">
                        <i class="fa fa-book me-2"></i> Tài liệu hướng dẫn
                    </a>
                </li>
            </ul>
        </div>

        <div class="col-md-9 p-4">
            <h5 class="mb-3">Góp ý sản phẩm</h5>
            <p class="text-muted">
                JobFinder rất mong muốn lắng nghe các phản hồi, góp ý từ phía Nhà tuyển dụng / Doanh nghiệp 
                để liên tục cải tiến, bổ sung tính năng, giúp sản phẩm Smart Recruitment Platform ngày càng 
                hữu ích và thân thiện hơn với người dùng.
            </p>

            <%-- Hiển thị thông báo thành công/lỗi --%>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">
                    ${successMessage}
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${errorMessage}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/app/supports/submit-suggest" method="POST" enctype="multipart/form-data">
                <%-- Đối tượng góp ý sẽ được map vào trường 'subject' của Feedback --%>
                <div class="mb-3">
                    <label for="subject" class="form-label">Đối tượng góp ý <span class="text-danger">*</span></label>
                    <select class="form-select" id="subject" name="subject" required>
                        <option selected disabled value="">Chọn đối tượng góp ý</option>
                        <option value="PRODUCT_OVERVIEW">Tổng quan sản phẩm</option>
                        <option value="EMAIL_ACTIVATION_FEATURE">Tính năng kích hoạt tài khoản qua email</option>
                        <option value="JOB_POSTINGS">Tin tuyển dụng</option>
                        <option value="CV_FILTERING">Lọc CV</option>
                        <option value="CV_MANAGEMENT">Quản lý CV</option>
                        <option value="RECRUITMENT_REPORTS">Báo cáo tuyển dụng</option>
                        <option value="OTHER">Khác</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="message" class="form-label">Nội dung góp ý <span class="text-danger">*</span></label>
                    <textarea class="form-control" id="message" name="message" rows="4" placeholder="Nhập nội dung chi tiết" required></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Tài liệu chứng minh</label>
                    <input class="form-control" type="file" name="attachments" multiple>
                    <div class="form-text">Tối đa 2 file, không vượt quá 5MB. Hỗ trợ: JPG, PNG, PDF, DOCX.</div>
                </div>
                <div class="text-end">
                    <button type="submit" class="btn btn-success">Gửi báo cáo</button>
                </div>
            </form>
        </div>
    </div>
</div>