
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Job Board - Trang tìm việc làm</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- SEO -->
    <meta name="description" content="Nền tảng tìm kiếm việc làm tốt nhất">

    <!-- Favicon & Manifest -->
    <link rel="manifest" href="<c:url value='/template/site.webmanifest'/>">
    <link rel="shortcut icon" href="<c:url value='/template/assets1/img/favicon.ico'/>">

    <!-- CSS -->
    <link rel="stylesheet" href="<c:url value='/template/assets1/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/template/assets1/css/style.css'/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <!-- JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .header-area {
            background-color: #ffffff;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        }
        .nav-link {
            font-weight: 500;
            color: #444;
        }
        .nav-link:hover {
            color: #28a745;
        }
        .btn-register {
            border: 1px solid #28a745;
            color: #28a745;
            background-color: white;
            padding: 6px 12px;
            border-radius: 6px;
            text-decoration: none !important;
            font-weight: 500;
            transition: all 0.3s ease;
        }
        .btn-register:hover,
        .btn-register:focus,
        .btn-register:active {
            background-color: #28a745;
            color: white;
            text-decoration: none;
        }
        .btn-login {
            background-color: #28a745;
            color: white;
            padding: 6px 12px;
            border-radius: 6px;
            text-decoration: none !important;
            font-weight: 500;
            transition: all 0.3s ease;
            border: 1px solid #28a745;
        }
        .btn-login:hover,
        .btn-login:focus,
        .btn-login:active {
            background-color: #218838;
            border-color: #218838;
            color: white;
            text-decoration: none;
        }
        .search-bar {
            background-color: white;
            border-radius: 999px;
            overflow: hidden;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 900px;
            margin: auto;
        }
        .keyword-input {
            border: none;
            padding: 14px 20px;
            flex-grow: 1;
            border-right: 1px solid #e0e0e0;
            border-radius: 0;
            outline: none;
            box-shadow: none;
        }
        .keyword-input::placeholder {
            color: #6c757d;
        }
        .location-select {
            border-right: 1px solid #e0e0e0;
            color: #2c3e50;
            cursor: pointer;
            font-size: 14px;
            height: 100%;
        }
        .search-btn {
            border-radius: 999px;
            padding: 10px 22px;
            font-weight: 500;
            white-space: nowrap;
            display: flex;
            align-items: center;
            background-color: #28a745;
            border: none;
        }
        .search-btn:hover {
            background-color: #218838;
        }
        .avatar-img {
            width: 36px;
            height: 36px;
            object-fit: cover;
        }
    </style>
</head>
<body>
    <header>
        <div class="header-area header-transparrent">
            <div class="headder-top header-sticky border-bottom">
                <div class="container-fluid">
                    <div class="d-flex justify-content-between align-items-center py-2">
                        <!-- Logo + slogan -->
                        <div class="d-flex align-items-center gap-2">
                            <a href="<c:url value='/'/>">
                                <img src="<c:url value='/template/assets1/img/logo/logo.png'/>" alt="Logo" style="height: 40px;">
                            </a>
                        </div>
                        <!-- Navigation -->
                        <nav class="navbar navbar-expand-lg">
                            <div class="collapse navbar-collapse show" id="navbarNav">
                                <ul class="navbar-nav align-items-center gap-3">
                                    <!-- Menu Việc làm -->
                                    <li class="nav-item dropdown">
                                        <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">Việc làm</a>
                                        <ul class="dropdown-menu">
                                            <li><a class="dropdown-item" href="#"><img src="${pageContext.request.contextPath}/template/assets1/img/icon/search-job.png"  width="18" height="18">Tìm việc làm</a></li>
                                            <li><a class="dropdown-item" href="#">Việc làm đã lưu</a></li>
                                            <li><a class="dropdown-item" href="#">Việc làm đã ứng tuyển</a></li>
                                            <li><a class="dropdown-item" href="#">Việc làm phù hợp</a></li>
                                            <li><hr class="dropdown-divider"></li>
                                            <li><a class="dropdown-item" href="#">Danh sách công ty</a></li>
                                            <li><a class="dropdown-item" href="#">Top công ty</a></li>
                                        </ul>
                                    </li>
                                    <!-- Tạo CV -->
                                    <li class="nav-item dropdown">
                                        <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">Tạo CV</a>
                                        <ul class="dropdown-menu">
                                            <li><a class="dropdown-item" href="#">Mẫu CV Đơn giản</a></li>
                                            <li><a class="dropdown-item" href="#">Mẫu CV Ấn tượng</a></li>
                                            <li><a class="dropdown-item" href="#">Mẫu CV Chuyên nghiệp</a></li>
                                        </ul>
                                    </li>
                                    <!-- Công cụ -->
                                    <li class="nav-item dropdown">
                                        <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">Công cụ</a>
                                        <ul class="dropdown-menu">
                                            <li><a class="dropdown-item" href="#">Tính lương Gross - Net</a></li>
                                            <li><a class="dropdown-item" href="#">Tính thuế TNCN</a></li>
                                            <li><a class="dropdown-item" href="#">Tính lãi suất kép</a></li>
                                        </ul>
                                    </li>
                                    
                                    <li class="nav-item"><a class="nav-link" href="#">Cẩm nang nghề</a></li>
                                    <li class="nav-item"><a class="nav-link" href="#">JobFinder Pro</a></li>
                                    <!-- Đăng nhập Employer -->
                                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/employers/login">Đăng tin tuyển dụng</a></li>
                                </ul>
                            </div>
                        </nav>
                        <!-- Search + Auth -->
                        <div class="d-flex align-items-center gap-3">
                            <c:choose>
                                <c:when test="${empty sessionScope.loggedInUser}">
                                    <!-- Chưa đăng nhập: Hiển thị Đăng ký và Đăng nhập -->
                                    <div class="d-flex align-items-center ms-3">
                                        <div class="me-2">
                                            <a class="btn-register" href="${pageContext.request.contextPath}/signup">Đăng ký</a>
                                        </div>
                                        <div>
                                            <a class="btn-login" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <!-- Đã đăng nhập: Hiển thị avatar dropdown -->
                                    <div class="dropdown">
                                        <a href="#" class="d-flex align-items-center nav-link dropdown-toggle" data-bs-toggle="dropdown">
                                            <img src="<c:url value='/template/assets1/img/avata.png'/>" alt="Avatar" class="avatar-img rounded-circle me-1">
                                            <span class="d-none d-md-inline">${fn:escapeXml(sessionScope.loggedInUser.fullName)}</span>
                                        </a>
                                        <ul class="dropdown-menu dropdown-menu-end">
                                            <li class="px-3 py-2">
                                                <strong>${fn:escapeXml(sessionScope.loggedInUser.fullName)}</strong><br>
                                                <small class="${sessionScope.loggedInUser.status == 'ACTIVE' ? 'text-success' : 'text-warning'}">
                                                    ${sessionScope.loggedInUser.status == 'ACTIVE' ? 'Tài khoản đã xác thực' : 'Tài khoản chưa xác thực'}
                                                </small><br>
                                                <small>ID: ${sessionScope.loggedInUser.id}</small><br>
                                                <small>${fn:escapeXml(sessionScope.loggedInUser.email)}</small>
                                            </li>
                                            <li><hr class="dropdown-divider"></li>
                                            <li><a class="dropdown-item" href="/saved-jobs">Việc làm đã lưu</a></li>
                                            <li><a class="dropdown-item" href="/applied-jobs">Đã ứng tuyển</a></li>
                                            <li><a class="dropdown-item" href="/cv-management">CV của tôi</a></li>
                                            <li><hr class="dropdown-divider"></li>
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                                        </ul>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
</body>
</html>
