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
                                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/jobs">Danh sách việc làm</a></li>
                                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/favorite-jobs">Việc làm đã lưu</a></li>
                                                <li><a class="dropdown-item" href="#">Việc làm đã ứng tuyển</a></li>
                                            </ul>
                                        </li>

                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
                                                Công ty
                                            </a>
                                            <ul class="dropdown-menu">
                                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/employers">Danh sách công ty</a></li>
                                                <li><a class="dropdown-item" href="#">Công ty đã lưu</a></li>
                                            </ul>
                                        </li>

                                        <!-- Tạo CV -->
                                        <li class="nav-item">
                                            <a class="nav-link" href="#" data-bs-toggle="dropdown">Tạo CV</a>
                                        </li>
                                        <!-- Đăng nhập Employer -->
                                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/app/login">Đăng tin tuyển dụng</a></li>
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
                                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/favorites">Việc làm đã lưu</a></li>
                                                <li><a class="dropdown-item" href="/applied-jobs">Đã ứng tuyển</a></li>
                                                <li><a class="dropdown-item" href="/cv-management">CV của tôi</a></li>
                                                <li><hr class="dropdown-divider"></li>
                                                <li><form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="post" style="display: none;">
                                                    </form>

                                                    <a href="#" class="dropdown-item text-danger" onclick="document.getElementById('logoutForm').submit(); return false;">
                                                        <i class="nc-icon nc-button-power"></i> Đăng xuất
                                                    </a></li>
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
