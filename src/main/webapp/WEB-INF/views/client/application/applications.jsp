<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<main>
    <div class="container py-5">
        <h2 class="mb-4">Đơn ứng tuyển của bạn</h2>

        <c:choose>
            <c:when test="${not empty applications}">
                <div class="row">
                    <c:forEach var="app" items="${applications}">
                        <div class="col-md-6 mb-4">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <a href="<c:url value='/jobs/detail/${app.job.id}'/>">
                                            ${app.job.title}
                                        </a>
                                    </h5>
                                    <p class="card-text">
                                        <strong>Địa điểm:</strong> ${app.job.location}<br />
                                        <strong>Lương:</strong> ${app.job.salaryMin} - ${app.job.salaryMax}<br />
                                        <strong>Hạn nộp:</strong>
                                        <fmt:formatDate value="${app.job.expiredAt}" pattern="dd/MM/yyyy" /><br />
                                        <strong>Trạng thái:</strong> ${app.status}<br />
                                        <strong>Điểm đánh giá:</strong> ${app.score}<br />
                                        <strong>Ứng tuyển lúc:</strong>
                                        <fmt:formatDate value="${app.appliedAt}" pattern="dd/MM/yyyy HH:mm" /><br />
                                        <strong>Link CV:</strong>
                                        <a href="${pageContext.request.contextPath}${app.resumeUrl}" target="_blank">Xem CV</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">
                    Bạn chưa ứng tuyển công việc nào.
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>