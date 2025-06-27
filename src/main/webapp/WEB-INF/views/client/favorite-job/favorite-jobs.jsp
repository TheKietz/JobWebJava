<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<main>
    <div class="container py-5">
        <h2 class="mb-4">Danh sách công việc đã lưu</h2>

        <c:choose>
            <c:when test="${not empty jobs}">
                <div class="row">
                    <c:forEach var="job" items="${jobs}">
                        <div class="col-md-6 mb-4">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <a href="<c:url value='/jobs/detail/${job.id}' />">${job.title}</a>
                                    </h5>
                                    <p class="card-text">
                                        <i class="fas fa-dollar-sign"></i> ${job.salaryMin} - ${job.salaryMax} <br />
                                        <i class="fas fa-calendar-alt"></i> Hạn nộp: ${job.expiredAt}
                                    </p>
                                    <form method="post" action="<c:url value='/favorite-jobs/remove/${job.id}'/>?redirect=/favorite-jobs">
                                        <button class="btn btn-danger btn-sm" type="submit">Bỏ lưu</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">
                    Bạn chưa lưu công việc nào.
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>


