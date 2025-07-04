<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Yêu cầu hỗ trợ & Báo cáo vi phạm</title>
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
            <h5 class="mb-3">Yêu cầu hỗ trợ & Báo cáo vi phạm</h5>
            <p class="text-muted">
                Với mong muốn tiếp nhận và xử lý các phản hồi từ phía nhà tuyển dụng một cách nhanh chóng,
                hệ thống cho phép gửi phản ánh để cải thiện trải nghiệm.
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

            <form action="${pageContext.request.contextPath}/app/supports/submit-report" method="POST" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="subject" class="form-label">Tiêu đề <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="subject" name="subject" placeholder="Nhập tiêu đề" required>
                </div>
                <div class="mb-3">
                    <label for="reportCategory" class="form-label">Loại báo cáo <span class="text-danger">*</span></label>
                    <select class="form-select" id="reportCategory" name="reportCategory" required>
                        <option selected disabled value="">Chọn loại báo cáo</option>
                        <option value="SALESMAN_REPORT">Báo cáo salesman</option>
                        <option value="UNSUITABLE_CANDIDATE">Ứng viên không phù hợp</option>
                        <option value="SYSTEM_BUG">Hệ thống lỗi</option>
                        <option value="OTHER">Khác</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="message" class="form-label">Mô tả <span class="text-danger">*</span></label>
                    <textarea class="form-control" id="message" name="message" rows="4" placeholder="Nhập mô tả chi tiết" required></textarea>
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