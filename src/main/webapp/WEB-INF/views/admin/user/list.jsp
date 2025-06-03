<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
        </div>
    </div>
</div>