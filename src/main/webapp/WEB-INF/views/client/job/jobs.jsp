<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>    

<main>
    <div class="container-fluid">
        <div class="container">
            <div class="row g-4 mb-5 mt-1">
                <div>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="<c:url value='/'/>">Trang chủ</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Danh Sách Việc Làm</li>
                        </ol>
                    </nav>
                </div>

                <div class="row g-4 fruite">
                    <!-- Bộ lọc đơn giản (nếu cần mở rộng) -->
                    <div class="col-12 col-md-4">
                        <div class="row g-4">
                            <form method="get" action="<c:url value='/jobs'/>">
                                <div class="col-12">
                                    <label for="keyword" class="form-label"><b>Tìm kiếm</b></label>
                                    <input type="text" id="keyword" name="keyword" class="form-control" value="${keyword}" placeholder="Nhập từ khoá việc làm mong muốn">
                                </div>

                                <div class="col-12 mt-3">
                                    <button type="submit" class="btn border-secondary rounded-pill px-4 py-3 text-primary text-uppercase mb-4">
                                        Tìm Việc Làm 
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
                                    <div class="rounded border border-secondary d-flex flex-column h-100 p-4 position-relative">
                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;">
                                            ${job.category}
                                        </div>

                                        <div class="pt-5 flex-grow-1">
                                            <h4 style="font-size: 15px;">
                                                <a href="<c:url value='/jobs/detail/${job.id}'/>">
                                                    ${job.title}
                                                </a>
                                            </h4>
                                            <p style="font-size: 13px;">
                                                Địa điểm: ${job.location} <br/>
                                                Hình thức: ${job.jobType}
                                            </p>
                                            <p class="text-dark fs-5 fw-bold mb-3" style="font-size: 15px;">
                                                <fmt:formatNumber value="${job.salaryMin}" type="currency" currencySymbol="₫"/>
                                                -
                                                <fmt:formatNumber value="${job.salaryMax}" type="currency" currencySymbol="₫"/>
                                            </p>
                                        </div>

                                        <div class="mt-auto">
                                            <a href="<c:url value='/jobs/detail/${job.id}'/>"
                                               class="btn btn border border-secondary rounded-pill px-3 text-primary w-100">
                                                <i class="fa fa-eye me-2 text-primary"></i> Xem chi tiết
                                            </a>
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