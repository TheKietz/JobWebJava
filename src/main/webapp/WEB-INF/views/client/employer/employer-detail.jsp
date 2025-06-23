<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<main>
    <!-- Thông tin chi tiết công ty -->
    <div class="job-post-company pt-50 pb-120">
        <div class="container">
            <div class="row justify-content-between">
                <!-- Nội dung bên trái -->
                <div class="col-xl-7 col-lg-8">
                    <div class="single-job-items mb-50">
                        <div class="job-items">
                            <div class="company-img company-img-details">
                                <c:choose>
                                    <c:when test="${not empty employer.logoUrl}">
                                        <img src="${employer.logoUrl}" class="img-fluid" style="max-height: 120px;" alt="Logo công ty">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/template/images/company-placeholder.png" class="img-fluid" style="max-height: 120px;" alt="Logo mặc định">
                                    </c:otherwise>
                                </c:choose>

                            </div>
                            <div class="job-tittle mt-3">
                                <h4>${employer.companyName}</h4>
                                <ul>
                                    <li><strong>Địa chỉ:</strong> ${employer.address}</li>
                                    <li><strong>Quy mô công ty:</strong> ${employer.companySize}</li>
                                    <li>
                                        <strong>Website:</strong>
                                        <a href="${employer.website}" target="_blank">${employer.website}</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="job-post-details">
                        <div class="post-details1 mb-50">
                            <div class="small-section-tittle">
                                <h4>Giới thiệu về công ty</h4>
                            </div>
                            <p>${employer.description}</p>
                        </div>
                    </div>
                </div>

                <!-- Nội dung bên phải -->
                <div class="col-xl-4 col-lg-4">
                    <div class="post-details3 mb-50">
                        <div class="small-section-tittle">
                            <h4>Thông tin liên hệ</h4>
                        </div>
                        <ul>
                            <li>Mã nhà tuyển dụng: <span>${employer.id}</span></li>
                            <li>Địa chỉ: <span>${employer.address}</span></li>
                            <li>Website: 
                                <span><a href="${employer.website}" target="_blank">${employer.website}</a></span>
                            </li>
                            <li>Email:
                                <span>contact@example.com</span> <%-- bạn có thể cập nhật thật khi có bảng user --%>
                            </li>
                        </ul>
                        <div class="apply-btn2 mt-4">
                            <a href="<c:url value='/jobs?employerId=${employer.id}'/>" class="btn">Xem việc làm của công ty</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
