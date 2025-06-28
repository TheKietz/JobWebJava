<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="vi">
    <body>
        <main>
            <!-- Banner -->
            <section class="banner w-100">
                <div class="container">
                    <h1 style="color: #02ba5a">JobFinder- Tiếp lợi thế, nối thành công</h1>
                    <p>Với hệ sinh thái HR Tech, JobFinder luôn đồng hành để bạn thành công trong sự nghiệp</p>
                </div>
            </section>

            <!-- Job Listings -->
            <section class="job-list py-4">
                <div class="container">
                    <h2>Việc làm tốt nhất</h2>
                    <c:if test="${empty jobs}">
                        <div class="alert alert-warning">Không có job nào để hiển thị</div>
                    </c:if>

                    <div class="row">
                        <c:forEach var="job" items="${jobs}">
                            <div class="col-md-4 mb-4">
                                <div class="card shadow-sm h-100">
                                    <div class="card-body">
                                        <h5 class="card-title">${job.title}</h5>
                                        <p class="card-text">
                                            <strong>Vị trí:</strong> ${job.location} <br/>
                                            <strong>Mức lương:</strong>
                                            <c:choose>
                                                <c:when test="${job.salaryMin != null && job.salaryMax != null}">
                                                    ${job.salaryMin} - ${job.salaryMax}
                                                </c:when>
                                                <c:otherwise>Thỏa thuận</c:otherwise>
                                            </c:choose><br/>
                                            <strong>Ngành: </strong> ${job.category}<br/>
                                            <strong>Loại công việc:</strong> ${job.jobType}<br/>
                                            <strong>Mô tả: </strong> ${job.description}
                                        </p>
                                        <a href="${pageContext.request.contextPath}/jobs/detail/${job.id}" class="btn btn-primary btn-sm">
                                            Xem chi tiết
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
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