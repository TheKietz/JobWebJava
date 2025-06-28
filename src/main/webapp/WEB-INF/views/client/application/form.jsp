<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container mt-5 mb-5">
    <h2 class="mb-4 text-center">Ứng tuyển công việc</h2>

    <!-- Thông tin công việc -->
    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title text-primary">${job.title}</h5>
            <p class="card-text text-muted">${job.description}</p>
        </div>
    </div>

    <!-- Form ứng tuyển -->
    <form method="post" action="${pageContext.request.contextPath}/applications/apply" enctype="multipart/form-data">
        <input type="hidden" name="jobId" value="${job.id}" />

        <div class="mb-3">
            <label for="resumeFile" class="form-label fw-bold">Tải lên CV của bạn:</label>
            <input type="file" name="resumeFile" accept=".pdf,.doc,.docx" class="form-control" required />
        </div>

        <button type="submit" class="btn btn-primary w-100">Gửi Đơn Ứng Tuyển</button>
    </form>

    <!-- Thông báo thành công -->
    <c:if test="${not empty success}">
        <div class="alert alert-success mt-3">${success}</div>
    </c:if>

    <!-- Thông báo lỗi -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3">${error}</div>
    </c:if>
</div>