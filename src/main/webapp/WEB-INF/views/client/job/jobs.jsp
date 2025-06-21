<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>    

<main>
    <div class="container-fluid">
        <div class="container">
            <div class="row g-4 mb-5">
                <div>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="<c:url value='/'/>">Trang chủ</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Danh Sách Việc Làm</li>
                        </ol>
                    </nav>
                </div>

                <div class="row g-4 fruite">
                    <!-- Bộ lọc -->
                    <div class="col-12 col-md-4">
                        <div class="row g-4">
                            <form method="get" action="<c:url value='/jobs'/>">
                                <!-- Ngành nghề -->
                                <div class="col-12" id="categoryFilter">
                                    <div class="mb-2"><b>Ngành nghề</b></div>
                                    <c:forEach var="cat" items="${fn:split('IT,Marketing,Finance,Design,HR', ',')}">
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="checkbox" name="category" value="${cat}" id="cat-${cat}">
                                            <label class="form-check-label" for="cat-${cat}">${cat}</label>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Cấp độ công việc -->
                                <div class="col-12" id="levelFilter">
                                    <div class="mb-2"><b>Cấp độ</b></div>
                                    <c:forEach var="level" items="${fn:split('Intern,Junior,Senior,Manager', ',')}">
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="checkbox" name="jobType" value="${level}" id="level-${level}">
                                            <label class="form-check-label" for="level-${level}">${level}</label>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Mức lương -->
                                <div class="col-12" id="salaryFilter">
                                    <div class="mb-2"><b>Mức lương</b></div>
                                    <c:forEach var="range" items="${fn:split('0-5,5-10,10-20,20-100', ',')}">
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="checkbox" name="salaryRange" value="${range}" id="salary-${range}">
                                            <label class="form-check-label" for="salary-${range}">${range} triệu</label>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Nút lọc -->
                                <div class="col-12">
                                    <button id="btnFilter"
                                            class="btn border-secondary rounded-pill px-4 py-3 text-primary text-uppercase mb-4">
                                        Lọc Việc Làm
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Danh sách công việc -->
                    <div class="col-12 col-md-8 text-center">
                        <div class="row g-4">
                            <c:forEach var="job" items="${jobs}">
                                <div class="col-md-6 col-lg-4">
                                    <div class="rounded position-relative fruite-item">
                                        <div class="fruite-img">
                                            <img src="<c:url value='/template/images/job.png'/>"
                                                 class="img-fluid w-100 rounded-top" alt="" style="height: 235px;">
                                        </div>
                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                             style="top: 10px; left: 10px;">
                                            ${job.category}
                                        </div>
                                        <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                            <h4 style="font-size: 15px;">
                                                <a href="<c:url value='/jobs/detail/${job.id}'/>">
                                                    ${job.title}
                                                </a>
                                            </h4>
                                            <p style="font-size: 13px;">
                                                Địa điểm: ${job.location} <br/>
                                                Hình thức: ${job.jobType}
                                            </p>
                                            <div class="d-flex flex-lg-wrap justify-content-center">
                                                <p class="text-dark fs-5 fw-bold mb-3"
                                                   style="font-size: 15px; width: 100%;">
                                                    <fmt:formatNumber value="${job.salaryMin}" type="currency" currencySymbol="₫"/>
                                                    -
                                                    <fmt:formatNumber value="${job.salaryMax}" type="currency" currencySymbol="₫"/>
                                                </p>
                                                <a href="<c:url value='/jobs/detail/${job.id}'/>"
                                                   class="btn mx-auto btn border border-secondary rounded-pill px-3 text-primary">
                                                    <i class="fa fa-eye me-2 text-primary"></i> Xem chi tiết
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <!-- Pagination -->
                            <c:choose>
                                <c:when test="${not empty jobs}">
                                    <div class="pagination d-flex justify-content-center mt-5">
                                        <li class="page-item ${page == 1 ? 'disabled' : ''}">
                                            <a class="page-link" href="?page=${page - 1}">&laquo;</a>
                                        </li>
                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                            <li class="page-item ${page == i ? 'active' : ''}">
                                                <a class="page-link" href="?page=${i}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item ${page == totalPages ? 'disabled' : ''}">
                                            <a class="page-link" href="?page=${page + 1}">&raquo;</a>
                                        </li>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div>Không có việc làm nào phù hợp.</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>