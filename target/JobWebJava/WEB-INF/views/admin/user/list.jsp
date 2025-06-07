<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="content-wrapper">
    <div class="container-fluid">
        <div class="card-body">
            <div class="row">
                <div class="col-xm-3">
                    <h5 class="card-title">Customer List</h5>
                </div>
                <div class="col-md-4">
                    <a href="${pageContext.request.contextPath}/admin/users/add" target="target" class="button-link btn-link">
                        Add new user
                    </a>
                </div>
                <div class="col text-right">
                    <!-- Page Size Selector -->
                    <form action="${pageContext.request.contextPath}/admin/users" method="get" class="form-inline align-items-center">
                        <div class="form-group mr-2 mb-0 d-flex align-items-center">
                            <label for="sizeSelect" class="mb-0 mr-2">Users per page:</label>
                            <select id="sizeSelect" name="size" class="form-control form-control-sm" onchange="this.form.submit()">
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
                                <th scope="row">${status.index + 1}</th>
                                <td>${c.fullName}</td>                                
                                <td>${c.email}</td>
                                <td>${c.role}</td>                                
                                <td>${c.createdAt}</td>                        
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/users/edit/${c.userID}" class="btn btn-primary btn-sm">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/admin/users/delete/${c.userID}" class="btn btn-danger btn-sm"
                                       onclick="return confirm('Bạn có chắc muốn xóa khách hàng này?')">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
            <c:if test="${totalPages > 0}">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${currentPage - 1}&size=${pageSize}&keyword=${keyword}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${i}&size=${pageSize}&keyword=${keyword}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${currentPage + 1}&size=${pageSize}&keyword=${keyword}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>