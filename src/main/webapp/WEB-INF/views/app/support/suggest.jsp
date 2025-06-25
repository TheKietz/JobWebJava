<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Góp ý sản phẩm</title>
        <!-- Font Awesome CDN -->
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
        <!-- Sidebar bên trái -->
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

        <!-- Nội dung bên phải -->
        <div class="col-md-9 p-4">
            <h5 class="mb-3">Góp ý sản phẩm</h5>
            <p class="text-muted">
                JobFinder rất mong muốn lắng nghe các phản hồi, góp ý từ phía Nhà tuyển dụng / Doanh nghiệp 
                để liên tục cải tiến, bổ sung tính năng, giúp sản phẩm Smart Recruitment Platform ngày càng 
                hữu ích và thân thiện hơn với người dùng.
            </p>
            <form>
                <div class="mb-3">
                    <label for="type" class="form-label">Đối tượng góp ý <span class="text-danger">*</span></label>
                    <select class="form-select" id="type">
                        <option selected disabled>Chọn loại báo cáo</option>
                        <option>Tổng quan sản phẩm</option>
                        <option>Tính năng kích hoạt tài khoản qua email</option>
                        <option>Tin tuyển dụng </option>
                        <option>Lọc CV </option>
                        <option>Quản lý CV </option>
                        <option>Báo cáo tuyển dụng</option>
                        <option>Khác</option>
                    </select>
                </div>               
                <div class="mb-3">
                    <label for="desc" class="form-label">Nội dung góp ý <span class="text-danger">*</span></label>
                    <textarea class="form-control" id="desc" rows="4" placeholder="Nhập nội dung chi tiết"></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Tài liệu chứng minh</label>
                    <input class="form-control" type="file" multiple>
                    <div class="form-text">Tối đa 2 file, không vượt quá 5MB. Hỗ trợ: JPG, PNG, PDF, DOCX.</div>
                </div>
                <div class="text-end">
                    <button type="submit" class="btn btn-success">Gửi báo cáo</button>
                </div>
            </form>
        </div>
    </div>
</div>
