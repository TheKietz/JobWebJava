<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hotline CSKH & Hỗ trợ dịch vụ</title>
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
            <h5 class="mb-3">Hotline CSKH & Hỗ trợ dịch vụ</h5>
            <p class="text-muted">
                Chúng tôi rất sẵn lòng được hỗ trợ bạn. Vui lòng liên hệ theo thông tin bên dưới để nhận trợ giúp.
            </p>
            <div class="row">
                <div class="col-md-3">
                 <i class="fa fa-phone"> </i> 
                <span>SĐT</span>
                </div>
                <div class="col-md-3">
                    <i class="fa fa-phone"> </i> 
                <span>SĐT</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <i class="fa fa-email"> </i> 
                <span>Email</span>
                </div>
                <div class="col-md-3">
                    <i class="fa fa-chat"> </i> 
                <span>Live chat</span>
                </div>
            </div>
        </div>
    </div>
</div>
