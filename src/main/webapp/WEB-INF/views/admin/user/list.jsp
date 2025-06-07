<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="content-wrapper">
    <div class="container-fluid">
        <div class="card-body">
            <div class="row">
                <div class="col-sm-3">
                    <h5 class="card-title">User List</h5>
                </div>
                <div class="col-md-4">
                    <a href="${pageContext.request.contextPath}/user/add" class="button-link btn-link">
                        Add New Admin User
                    </a>
                </div>
                <div class="col-md-5 text-right">                    
                    <!-- Page Size Selector -->
                    <form action="${pageContext.request.contextPath}/user" method="get" class="form-inline d-inline">
                        <div class="form-group mr-2 mb-0 d-flex align-items-center">
                            <label class="mr-2">Users per page:</label>
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
                            <th scope="col">Full Name</th>                            
                            <th scope="col">Email</th>                            
                            <th scope="col">Role</th>
                            <th scope="col">Created At</th>
                            <th scope="col">Function</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="c" items="${users}" varStatus="status">
                            <tr>
                                <th scope="row">${(currentPage - 1) * pageSize + status.index + 1}</th>
                                <td>
                                    <c:set var="entityInfo" value="${userEntityMap[c.userID]}" />
                                    <c:choose>
                                        <c:when test="${not empty entityInfo}">
                                            <c:set var="entityParts" value="${fn:split(entityInfo, ':')}" />
                                            <c:set var="entityType" value="${entityParts[0]}" />
                                            <c:set var="entityId" value="${entityParts[1]}" />
                                            <c:choose>
                                                <c:when test="${entityType == 'employer'}">
                                                    <a href="${pageContext.request.contextPath}/admin/employers/edit/${entityId}" class="text-primary" style="cursor: pointer;">
                                                        ${fn:escapeXml(c.fullName)}
                                                    </a>
                                                </c:when>
                                                <c:when test="${entityType == 'candidate'}">
                                                    <a href="${pageContext.request.contextPath}/admin/candidates/edit/${entityId}" class="text-primary" style="cursor: pointer;">
                                                        ${fn:escapeXml(c.fullName)}
                                                    </a>
                                                </c:when>
                                                <c:when test="${entityType == 'admin'}">
                                                    <a href="${pageContext.request.contextPath}/admin/users/edit/${entityId}" class="text-primary" style="cursor: pointer;">
                                                        ${fn:escapeXml(c.fullName)}
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    ${fn:escapeXml(c.fullName)}
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            ${fn:escapeXml(c.fullName)}
                                        </c:otherwise>
                                    </c:choose>
                                </td>                                
                                <td>${fn:escapeXml(c.email)}</td>
                                <td>${fn:escapeXml(c.role)}</td>                                
                                <td>${c.createdAt}</td>                        
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/users/edit/${c.userID}" class="btn btn-primary btn-sm">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/admin/users/delete/${c.userID}" 
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Bạn có chắc muốn xóa người dùng \'' + '${fn:replace(fn:escapeXml(c.fullName), '\'', '\\\'')}' + '\'?')">
                                        Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty users}">
                            <tr>
                                <td colspan="6" class="text-center">No Users Found.</td>
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
                                <a class="page-link" href="${pageContext.request.contextPath}/users?page=${currentPage - 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/users?page=${i}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/users?page=${currentPage + 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>