<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="content-wrapper">
    <div class="container-fluid">
        <div class="card-body">
            <!-- Display success/error messages -->
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
            <div class="row mb-3 d-flex align-items-center">
                <div class="col-sm-2">
                    <h5 class="card-title">User List</h5>
                </div>
                <div class="col-md-3">
                    <a href="${pageContext.request.contextPath}/admin/users/add?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-primary">New Admin User</a>
                </div>
                <div class="col-md-7">
                    <div class="d-flex justify-content-end align-items-center">
                        <form action="${pageContext.request.contextPath}/admin/users" method="get" class="form-inline d-flex align-items-center me-3">
                            <input type="text" name="keyword" class="form-control form-control-sm me-2" placeholder="Search by name or email" value="${fn:escapeXml(keyword)}">
                            <button type="submit" class="btn btn-primary btn-sm">Search</button>
                            <input type="hidden" name="page" value="1">
                            <input type="hidden" name="size" value="${pageSize}">
                        </form>
                        <form action="${pageContext.request.contextPath}/admin/users" method="get" class="form-inline d-flex align-items-center">
                            <input type="hidden" name="keyword" value="${fn:escapeXml(keyword)}">
                            <input type="hidden" name="page" value="${currentPage}">
                            <div class="form-group d-flex align-items-center mb-0">
                                <label class="me-2 mb-0">Users per page:</label>
                                <select name="size" class="form-control form-control-sm" onchange="this.form.submit()">
                                    <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                                    <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                                    <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                                </select>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
           <div class="table-responsive">
                    <table class="table table-bordered table-hover bg-info-light2">
                        <thead class="table-active">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Full Name</th>
                            <th scope="col">Email</th>
                            <th scope="col">Phone</th>
                            <th scope="col">Role</th>
                            <th scope="col">Status</th>
                            <th scope="col">Created At</th>
                            <th scope="col">Function</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}" varStatus="status">
                            <tr>
                                <th scope="row">${(currentPage - 1) * pageSize + status.index + 1}</th>
                                <td>${fn:escapeXml(user.fullName)}</td>
                                <td>${fn:escapeXml(user.email)}</td>
                                <td>${fn:escapeXml(user.phone)}</td>
                                <td>${fn:escapeXml(user.role)}</td>
                                <td>${fn:escapeXml(user.status)}</td>
                                <td>${user.createdAt}</td>                                
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/users/edit/${user.id}?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-primary btn-sm">Edit</a>
                                    <a href="${pageContext.request.contextPath}/admin/users/delete/${user.id}?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" 
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Are you sure you want to delete user \'' + '${fn:replace(fn:escapeXml(user.fullName), '\'', '\\\'')}' + '\'?')">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty users}">
                            <tr>
                                <td colspan="9" class="text-center">No Users Found.</td>
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
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${currentPage - 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${i}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${currentPage + 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>