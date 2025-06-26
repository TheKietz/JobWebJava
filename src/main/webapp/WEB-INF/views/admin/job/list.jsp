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
                    <form action="${pageContext.request.contextPath}/admin/jobs" method="get" class="form-inline d-flex justify-content-between align-items-center mb-3">
                        <div class="col-md-4">
                            <div class="input-group ">
                                <input type="text" id="keywordInput" name="keyword" class="form-control" placeholder="Search by title, description, location" value="${fn:escapeXml(keyword)}"/>
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </div>
                        <div class="col-md-2 text-end">
                            <a href="${pageContext.request.contextPath}/admin/jobs/add?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-success">Add Job</a>
                        </div>
                        <div class="col-md-3 text-right">
                            <div >                    
                                <div class="form-group mr-3 d-flex align-items-center">
                                    <label for="pageSizeSelect" class="mr-2">Jobs per page:</label>
                                    <select id="pageSizeSelect" name="size" class="form-control form-control-sm" onchange="this.form.submit()">
                                        <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                                        <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                                        <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                                    </select>
                                </div>
                            </div> 
                        </div>
                        <div class="col-md-3 text-right">
                            <div class="form-group mr-3 d-flex align-items-center">
                                <label for="statusSelect" class="mr-2">Status:</label>
                                <select id="statusSelect" name="status" class="form-control form-control-sm" onchange="this.form.submit()">
                                    <option value="">All</option> <%-- Tùy chọn "Tất cả trạng thái" --%>
                                    <%-- Đây là đoạn code quan trọng cần có: --%>
                                    <c:forEach var="jobStatus" items="${jobStatuses}">
                                        <option value="${jobStatus.name()}" ${jobStatus.name() == selectedStatus ? 'selected' : ''}>
                                            ${jobStatus.name()}
                                        </option>
                                    </c:forEach>
                                    <%-- Kết thúc đoạn code quan trọng --%>

                                </select>
                            </div>
                        </div>
                        <input type="hidden" name="page" value="1">
                    </form>
                </div>
            </div>
            <div class="table-responsive">
                <table class="table table-bordered bg-info-light2">
                    <thead class="table-light">
                        <tr>
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
                                        <td>${fn:escapeXml(companyName[job.employerId])}</td>
                                        <td>${fn:escapeXml(job.title)}</td> 
                                        <td>${job.jobType}</td>
                                        <td>
                                            <c:set var="currentStatusName" value="${job.status.name()}" />
                                            <c:set var="translatedStatus" value="${translatedStatuses[currentStatusName]}" />
                                            <c:choose>
                                                <c:when test="${job.status == 'APPROVED'}">
                                                    <span class="badge bg-success">${translatedStatus}</span>
                                                </c:when>
                                                <c:when test="${job.status == 'PENDING'}">
                                                    <span class="badge bg-secondary">${translatedStatus}</span>
                                                </c:when>
                                                <c:when test="${job.status == 'REJECTED'}">
                                                    <span class="badge bg-danger">${translatedStatus}</span>
                                                </c:when>
                                                <c:when test="${job.status == 'EXPIRED'}">
                                                    <span class="badge bg-warning text-dark">${translatedStatus}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-info">${translatedStatus}</span> 
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
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
