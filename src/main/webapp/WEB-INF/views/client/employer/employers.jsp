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
                            <li class="breadcrumb-item active" aria-current="page">Danh Sách Công Ty</li>
                        </ol>
                    </nav>
                </div>

                <div class="row g-4 fruite">
                    <!-- Bộ lọc đơn giản (nếu cần mở rộng) -->
                    <div class="col-12 col-md-4">
                        <div class="row g-4">
                            <form method="get" action="<c:url value='/employers'/>">
                                <div class="col-12">
                                    <label for="keyword" class="form-label"><b>Từ khóa</b></label>
                                    <input type="text" id="keyword" name="keyword" class="form-control" value="${keyword}" placeholder="Tìm theo tên công ty hoặc website">
                                </div>

                                <div class="col-12 mt-3">
                                    <button type="submit" class="btn border-secondary rounded-pill px-4 py-3 text-primary text-uppercase mb-4">
                                        Tìm Công Ty
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Danh sách công ty -->
                    <div class="col-12 col-md-8 text-center">
                        <div class="row g-4">
                            <c:forEach var="employer" items="${employers}">
                                <div class="col-md-6 col-lg-4">
                                    <div class="rounded position-relative fruite-item">
                                        <div class="fruite-img">
                                            <img src="${employer.logoUrl != null ? employer.logoUrl : pageContext.request.contextPath + '/template/images/company-placeholder.png'}"
                                                 class="img-fluid w-100 rounded-top" alt="" style="height: 235px; object-fit: cover;">
                                        </div>
                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                             style="top: 10px; left: 10px;">
                                            ${employer.companySize}
                                        </div>
                                        <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                            <h4 style="font-size: 15px;">
                                                <a href="<c:url value='/employers/detail/${employer.id}'/>">
                                                    ${employer.companyName}
                                                </a>
                                            </h4>
                                            <p style="font-size: 13px;">
                                                Địa chỉ: ${employer.address}<br/>
                                                Website: <a href="${employer.website}" target="_blank">${employer.website}</a>
                                            </p>
                                            <div class="d-flex justify-content-center">
                                                <a href="<c:url value='/employers/detail/${employer.id}'/>"
                                                   class="btn btn-sm border border-secondary rounded-pill px-3 text-primary">
                                                    <i class="fa fa-eye me-2 text-primary"></i> Xem chi tiết
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <!-- Pagination -->
                            <c:choose>
                                <c:when test="${not empty employers}">
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
                                    <div>Không tìm thấy công ty nào phù hợp.</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
