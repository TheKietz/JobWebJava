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
                                    <label for="keyword" class="form-label"><b>Tìm kiếm</b></label>
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
                    <div class="col-12 col-md-8">
                        <div class="row g-4">
                            <c:forEach var="employer" items="${employers}">
                                <div class="col-md-6 col-lg-4 d-flex">
                                    <div class="rounded border border-secondary d-flex flex-column h-100 w-100 position-relative">

                                        <!-- Ảnh logo -->
                                        <div class="fruite-img">
                                            <c:choose>
                                                <c:when test="${not empty employer.logoUrl}">
                                                    <img src="${pageContext.request.contextPath}/uploads/${employer.logoUrl}"
                                                         class="img-fluid w-100 rounded-top"
                                                         style="height: 200px; object-fit: cover;"
                                                         alt="${employer.companyName}" />
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}/template/images/company-placeholder.png"
                                                         class="img-fluid w-100 rounded-top"
                                                         style="height: 200px; object-fit: cover;"
                                                         alt="Placeholder Logo" />
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <!-- Badge -->
                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                             style="top: 10px; left: 10px;">
                                            ${employer.companySize}
                                        </div>

                                        <!-- Nội dung -->
                                        <div class="p-4 flex-grow-1 d-flex flex-column">
                                            <h4 style="font-size: 15px;">
                                                <a href="<c:url value='/employers/detail/${employer.id}'/>">
                                                    ${employer.companyName}
                                                </a>
                                            </h4>
                                            <p style="font-size: 13px;">
                                                Địa chỉ: ${employer.address}<br/>
                                                Website: <a href="${employer.website}" target="_blank">${employer.website}</a>
                                            </p>

                                            <!-- Nút luôn nằm dưới -->
                                            <div class="mt-auto text-center">
                                                <a href="${pageContext.request.contextPath}/employers/detail/${employer.id}"
                                                   class="btn btn-sm border border-secondary rounded-pill px-3 text-primary">
                                                    <i class="fa fa-eye me-2 text-primary"></i> Xem chi tiết
                                                </a>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
