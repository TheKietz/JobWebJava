<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="content-wrapper">
    <div class="container-fluid">
        <div class="card-body">
            <div class="row mb-3">
                <div class="col-sm-3">
                    <h5 class="card-title">Job List</h5>
                </div>
                <div class="col-md-4">
                    <a href="${pageContext.request.contextPath}/admin/jobs/add" class="button-link btn-link">Add New Job</a>
                </div>
                <div class="col-md-5 text-right">                    
                    <form action="${pageContext.request.contextPath}/admin/jobs" method="get" class="form-inline d-inline">
                        <div class="form-group mr-2 mb-0 d-flex align-items-center">
                            <label class="mr-2">Jobs per page:</label>
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
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Employer ID</th>
                            <th scope="col">Title</th>
                            <th scope="col">Description</th>
                            <th scope="col">Location</th>
                            <th scope="col">Job Type</th>
                            <th scope="col">Salary Range</th>
                            <th scope="col">Posted At</th>
                            <th scope="col">Expiry Date</th>
                            <th scope="col">Function</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="c" items="${jobs}" varStatus="status">
                            <tr>
                                <th scope="row">${(currentPage - 1) * pageSize + status.index + 1}</th>
                                <td>${fn:escapeXml(c.employerID)}</td>
                                <td>${fn:escapeXml(c.title)}</td>
                                <td>${fn:escapeXml(c.description)}</td>
                                <td>${fn:escapeXml(c.location)}</td>
                                <td>${fn:escapeXml(c.jobType)}</td>
                                <td>${fn:escapeXml(c.salaryRange)}</td>
                                <td>${c.postedAt}</td>
                                <td>${c.expiryDate}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/jobs/edit/${c.jobID}" class="btn btn-primary btn-sm">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/admin/jobs/delete/${c.jobID}" 
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Bạn có chắc muốn xóa công việc \'' + '${fn:replace(fn:escapeXml(c.title), '\'', '\\\'')}' + '\'?')">
                                        Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty jobs}">
                            <tr>
                                <td colspan="10" class="text-center">No Jobs Found.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
            <c:if test="${totalPages > 0}">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/jobs?page=${currentPage - 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/jobs?page=${i}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/jobs?page=${currentPage + 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>