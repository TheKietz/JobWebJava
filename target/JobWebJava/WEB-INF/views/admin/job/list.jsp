
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="content-wrapper">
    <div class="container-fluid">
        <h2>Jobs Management</h2>
        <hr>
        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${fn:escapeXml(success)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${fn:escapeXml(error)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-info alert-dismissible fade show" role="alert">
                ${fn:escapeXml(message)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="card mb-3">
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <form action="${pageContext.request.contextPath}/admin/jobs" method="get">
                            <div class="input-group">
                                <input type="text" name="keyword" class="form-control" placeholder="Search by title or category" value="${fn:escapeXml(keyword)}"/>
                                <input type="hidden" name="size" value="${pageSize}"/>
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6 text-end">
                        <a href="${pageContext.request.contextPath}/admin/jobs/add?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-success">Add Job</a>
                    </div>
                </div>
                <!-- Thêm div với overflow-x: auto -->
                <div class="table-responsive" style="overflow-x: auto;">
                    <table class="table table-bordered" style="min-width: 1200px;">
                        <thead>
                            <tr>
                                <th style="width: 5%;">ID</th>
                                <th style="width: 8%;">Employer ID</th>
                                <th style="width: 12%;">Title</th>
                                <th style="width: 20%;">Description</th>
                                <th style="width: 10%;">Location</th>
                                <th style="width: 10%;">Salary Range</th>
                                <th style="width: 8%;">Job Type</th>
                                <th style="width: 8%;">Status</th>
                                <th style="width: 10%;">Category</th>
                                <th style="width: 10%;">Created At</th>
                                <th style="width: 10%;">Expired At</th>
                                <th style="width: 10%;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="job" items="${jobs}">
                                <tr>
                                    <td>${job.id}</td>
                                    <td>${job.employerId}</td>
                                    <td>${fn:escapeXml(job.title)}</td>
                                    <td class="text-truncate" style="max-width: 200px;" title="${fn:escapeXml(job.description)}">${fn:escapeXml(job.description)}</td>
                                    <td>${fn:escapeXml(job.location)}</td>
                                    <td>${job.salaryMin} - ${job.salaryMax}</td>
                                    <td>${job.jobType}</td>
                                    <td>${job.status}</td>
                                    <td>${fn:escapeXml(job.category)}</td>
                                    <td>${job.getPostedAtFormatted()}</td>
                                    <td>${job.getExpiryDateFormatted()}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/jobs/edit/${job.id}?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-sm btn-primary">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/jobs/delete/${job.id}?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this job?')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <c:if test="${totalPages > 1}">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:forEach begin="1" end="${totalPages}" var="page">
                                <li class="page-item ${page == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/admin/jobs?page=${page}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">${page}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
</div>
