<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<main>
    <!-- Thông tin chi tiết công việc -->
    <div class="job-post-company pt-50 pb-120">
        <div class="container">
            <div class="row justify-content-between">

                <!-- Nội dung bên trái -->
                <div class="col-xl-7 col-lg-8">
                    <div class="single-job-items mb-50">
                        <div class="job-items">
                            <div class="company-img company-img-details">
                                <img src="${pageContext.request.contextPath}/template/assets/img/icon/job-list1.png" alt="Logo công ty">
                            </div>
                            <div class="job-tittle mt-20">
                                <h4>${job.title}</h4>
                                <ul>
                                    <li><strong>Mã nhà tuyển dụng:</strong> ${job.employerId}</li>
                                    <li><i class="fas fa-map-marker-alt"></i> ${job.location}</li>
                                    <li>
                                        <strong>Mức lương:</strong>
                                        <fmt:formatNumber value="${job.salaryMin}" type="currency" currencySymbol="₫"/> -
                                        <fmt:formatNumber value="${job.salaryMax}" type="currency" currencySymbol="₫"/>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="job-post-details">
                        <div class="post-details1 mb-50">
                            <div class="small-section-tittle">
                                <h4>Mô tả công việc</h4>
                            </div>
                            <p>${job.description}</p>
                        </div>
                    </div>
                </div>

                <!-- Nội dung bên phải -->
                <div class="col-xl-4 col-lg-4">
                    <div class="post-details3 mb-50">
                        <div class="small-section-tittle">
                            <h4>Thông tin công việc</h4>
                        </div>
                        <ul>
                            <li>Ngày đăng:
                                <span>
                                    <span>${job.postedAtFormatted}</span>
                                </span>
                            </li>
                            <li>Địa điểm: <span>${job.location}</span></li>
                            <li>Hình thức làm việc: <span>${job.jobType}</span></li>
                            <li>Mức lương:
                                <span>
                                    <fmt:formatNumber value="${job.salaryMin}" type="currency" currencySymbol="₫" /> -
                                    <fmt:formatNumber value="${job.salaryMax}" type="currency" currencySymbol="₫" />
                                </span>
                            </li>
                            <li>Hạn nộp hồ sơ:
                                <span>
                                    <span>${job.expiryDateFormatted}</span>
                                </span>
                            </li>
                        </ul>
                        <div class="apply-btn2">
                            <button>
                                <a href="${pageContext.request.contextPath}/client/application/apply?jobId=${job.id}" class="btn btn-success">Ứng tuyển ngay</a>
                            </button>
                        </div>

                        <div class="mt-3">
                            <c:choose>
                                <c:when test="${isFavorite}">
                                    <form method="post" action="<c:url value='/favorite-jobs/remove/${job.id}'/>?redirect=/jobs/detail/${job.id}">
                                        <button type="submit" class="btn btn-outline-danger w-100">
                                            <i class="fa fa-times-circle"></i> Bỏ lưu công việc
                                        </button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form method="post" action="<c:url value='/favorite-jobs/add/${job.id}'/>">
                                        <button type="submit" class="btn btn-outline-primary w-100">
                                            <i class="fa fa-bookmark"></i> 
                                            <a href="${pageContext.request.contextPath}/jobs/detail/${job.id}">
                                                Lưu công việc
                                                <a/> 
                                        </button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="post-details4 mb-50">
                        <div class="small-section-tittle">
                            <h4>Thông tin công ty</h4>
                        </div>
                        <span>Công ty #${employer.id}</span>
                        <p>${employer.description}</p>
                        <ul>
                            <li>Tên công ty: <span>${employer.companyName}</span></li>
                            <li>Website: <a href="${employer.website}" target="_blank">${employer.website}</a></li>
                            <li>Địa chỉ: <span>${employer.address}</span></li>
                            <li>Email: <span>${employerEmail}</span></li><!-- nếu có -->
                        </ul>

                    </div>
                </div>

            </div>
        </div>
    </div>
</main>
