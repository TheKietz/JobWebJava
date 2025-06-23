<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    <div class="col-md-3 text-end">
                        <a href="${pageContext.request.contextPath}/admin/jobs/add?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-success">Add Job</a>
                    </div>
                    <div class="col-md-3 text-right">
                        <div >                    
                            <form action="${pageContext.request.contextPath}/admin/applications" method="get" class="form-inline d-inline">
                                <div class="form-group mr-2 mb-0 d-flex align-items-center">
                                    <label class="mr-2">Applications per page:</label>
                                    <select name="size" class="form-control form-control-sm" onchange="this.form.submit()">
                                        <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                                        <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                                        <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                                    </select>
                                </div>
                                <input type="hidden" name="page" value="1">
                                <input type="hidden" name="keyword" value="${fn:escapeXml(keyword)}">
                            </form>
                        </div> 
                    </div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th class="col-1">ID</th>
                                <th class="col-1">Công ty</th>
                                <th class="col-2">Tiêu đề</th>    
                                <th class="col-1">Kiểu công việc</th>
                                <th class="col-1">Trạng thái</th>
                                <th class="col-2">Loại công việc</th>
                                <th class="col-2">Ngày đăng</th>     
                                <th class="col-2">Ngày hết hạn</th>   
                                <th class="col-2">Chức năng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty jobs}">
                                    <tr>
                                        <td colspan="12" class="text-center">No jobs found.</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="job" items="${jobs}">
                                        <tr>
                                            <td>${job.id}</td>
                                            <td>${fn:escapeXml(companyName[job.employerId])}</td>
                                            <td>${fn:escapeXml(job.title)}</td> 
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
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
                <c:if test="${totalPages > 1}">
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
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