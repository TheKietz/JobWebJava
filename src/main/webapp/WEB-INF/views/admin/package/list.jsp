<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="content-wrapper">
    <div class="container-fluid">
        <h2>Quản lý gói</h2>
        <hr>

        <!-- Thông báo -->
        <c:if test="${not empty success}">
            <div class="alert alert-success">${fn:escapeXml(success)}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${fn:escapeXml(error)}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-info">${fn:escapeXml(message)}</div>
        </c:if>

        <!-- Tìm kiếm và thêm mới -->
        <div class="card mb-3">
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <form action="${pageContext.request.contextPath}/admin/packages" method="get">
                            <div class="input-group">
                                <input type="text" name="keyword" class="form-control" placeholder="Search by name" value="${fn:escapeXml(keyword)}"/>
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-3 text-end">
                        <a href="${pageContext.request.contextPath}/admin/packages/add" class="btn btn-success">Add Package</a>
                    </div>
                    <div class="col-md-3 text-right">
                        <div >                    
                            <form action="${pageContext.request.contextPath}/admin/packages" method="get" class="form-inline d-inline">
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
                </div>

                <!-- Bảng dữ liệu -->
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Duration (days)</th>
                                <th>Job Post Limit</th>
                                <th>Resume Access</th>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty packages}">
                                    <tr><td colspan="8" class="text-center">No packages found.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="pack" items="${packages}">
                                        <tr>
                                            <td>${pack.id}</td>
                                            <td>${fn:escapeXml(pack.name)}</td>
                                            <td><fmt:formatNumber value="${pack.price}" type="currency" currencySymbol="₫" /></td>
                                            <td>${pack.durationDays}</td>
                                            <td>${pack.jobPostLimit}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${pack.resumeAccess}">Yes</c:when>
                                                    <c:otherwise>No</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${fn:escapeXml(pack.description)}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/packages/edit/${pack.id}" class="btn btn-sm btn-primary">Edit</a>
                                                <a href="${pageContext.request.contextPath}/admin/packages/delete/${pack.id}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this package?')">Delete</a>
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
