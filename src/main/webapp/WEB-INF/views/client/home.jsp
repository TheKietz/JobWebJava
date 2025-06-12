<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>TopCV Main Content</title>
        <link rel="stylesheet" href="<c:url value='/template/assets1/css/bootstrap.min.css'/>">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body>
        <main>
            <!-- Banner -->
            <section class="banner">
                <div class="d-flex justify-content-center my-4">
                    <!-- Search -->
                    <form class="custom-search-form d-flex align-items-center" action="${pageContext.request.contextPath}/search" method="get">
                        <!-- Ô từ khóa -->
                        <input type="text" class="form-control search-input" name="keyword" placeholder="Vị trí tuyển dụng, tên công ty" value="${fn:escapeXml(keyword)}">

                        <!-- Địa điểm -->
                        <div class="location-box d-flex align-items-center">
                            <i class="bi bi-geo-alt me-1"></i>
                            <span>Địa điểm</span>
                            <i class="bi bi-chevron-down ms-1"></i>
                        </div>

                        <!-- Nút tìm kiếm -->
                        <button type="submit" class="btn search-button">
                            <i class="bi bi-search me-1"></i> Tìm Kiếm
                        </button>
                    </form>

                </div>
                <div class="container">
                    <h1 style="color: #02ba5a">JobFinder- Tiếp lợi thế, nối thành công</h1>
                    <p>Với Hệ sinh thái HR Tech, JobFinder luôn đồng hành để bạn thành công trong sự nghiệp</p>
                    <a href="#" class="btn-login">Tìm hiểu thêm</a>
                </div>
            </section>

            <!-- Job Listings -->
            <section class="job-list py-5">
                <div class="container">
                    <h2>Việc làm tốt nhất</h2>
                    <div class="row">
                        <c:forEach var="job" items="${jobs}">
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">${fn:escapeXml(job.title)}</h5>
                                        <p class="card-text">${fn:escapeXml(job.company.name)}</p>
                                        <p class="card-text">${job.salary}</p>
                                        <p class="card-text">${job.short_cities}</p>
                                        <a href="#" class="btn btn-outline-primary">Ứng tuyển</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <a href="#" class="btn btn-link">Xem tất cả</a>
                </div>
            </section>

            <!-- Top Categories -->
            <section class="category-list py-5 bg-light">
                <div class="container">
                    <h2>Top ngành nghề nổi bật</h2>
                    <div class="list-group">
                        <c:forEach var="category" items="${categories}">
                            <a href="#" class="list-group-item list-group-item-action">${category.name} (${category.job_category_count} việc làm)</a>
                        </c:forEach>
                    </div>
                </div>
            </section>

            <!-- Blog Posts -->
            <section class="blog-posts py-5">
                <div class="container">
                    <h2>Bài viết nổi bật</h2>
                    <div class="row">
                        <div class="col-md-6 blog-post">
                            <h3>TopCV Pro – Không gian tuyển dụng chuyên biệt</h3>
                            <p>Mỗi chúng ta đều mang trong mình một khát vọng chạm tới đỉnh cao trong sự nghiệp...</p>
                            <a href="#" class="btn btn-link">Xem thêm</a>
                        </div>
                        <div class="col-md-6 blog-post">
                            <h3>Các loại bảo hiểm khi đi làm phải đóng</h3>
                            <p>Khi ký hợp đồng lao động, người lao động và người sử dụng lao động sẽ có những thỏa thuận...</p>
                            <a href="#" class="btn btn-link">Xem thêm</a>
                        </div>
                    </div>
                    <a href="#" class="btn btn-link">Xem thêm bài viết nổi bật</a>
                </div>
            </section>

            <!-- Tools -->
            <section class="tools py-5 bg-light">
                <div class="container">
                    <h2>Công cụ hỗ trợ</h2>
                    <div class="row">
                        <div class="col-md-3">
                            <a href="#" class="btn btn-outline-secondary w-100 mb-3">Tính lương Gross - Net</a>
                        </div>
                        <div class="col-md-3">
                            <a href="#" class="btn btn-outline-secondary w-100 mb-3">Trắc nghiệm MBTI</a>
                        </div>
                        <div class="col-md-3">
                            <a href="#" class="btn btn-outline-secondary w-100 mb-3">Tạo CV miễn phí</a>
                        </div>
                        <div class="col-md-3">
                            <a href="#" class="btn btn-outline-secondary w-100 mb-3">Tính bảo hiểm thất nghiệp</a>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    </body>
</html>

<style>
    .banner {
        background: url('${pageContext.request.contextPath}/template/images/desktop_bg.png') no-repeat center;
        background-size: cover;
        padding: 100px 0;
        color: whitesmoke;
        text-align: center;
    }
    .banner h1 {
        font-size: 36px;
    }
    .job-list .card {
        margin-bottom: 20px;
    }
    .job-list .card-title {
        font-size: 18px;
    }
    .category-list .list-group-item {
        cursor: pointer;
    }
    .blog-post {
        margin-bottom: 30px;
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
    .custom-search-form {
        background-color: #fff;
        border-radius: 999px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        overflow: hidden;
        width: 100%;
        max-width: 900px;
    }

    .search-input {
        border: none;
        padding: 12px 20px;
        flex: 1;
        border-right: 1px solid #ddd;
        outline: none;
        box-shadow: none;
        font-size: 15px;
    }

    .location-box {
        padding: 0 15px;
        font-size: 14px;
        color: #343a40;
        border-right: 1px solid #ddd;
        white-space: nowrap;
    }

    .search-button {
        background-color: #28a745;
        color: white;
        padding: 10px 22px;
        font-weight: 500;
        border: none;
        border-top-right-radius: 999px;
        border-bottom-right-radius: 999px;
        border-left: 1px solid #28a745;
        display: flex;
        align-items: center;
        transition: background-color 0.3s;
    }

    .search-button:hover {
        background-color: #218838;
        color: white;
    }

    .custom-search-form input:focus,
    .custom-search-form button:focus {
        outline: none;
        box-shadow: none;
    }

</style>