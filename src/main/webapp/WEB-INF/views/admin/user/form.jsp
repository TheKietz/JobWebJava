<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="content-wrapper">
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${user.id == null ? 'Add User' : 'Edit User'}</h5>
                        <hr>
                        <!-- Hiển thị lỗi chung -->
                        <c:if test="${not empty result && result.hasErrors()}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                Please fix the errors below:
                                <ul>
                                    <c:forEach items="${result.allErrors}" var="error">
                                        <li>${fn:escapeXml(error.defaultMessage)}</li>
                                    </c:forEach>
                                </ul>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${fn:escapeXml(error)}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <c:if test="${not empty success}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                ${fn:escapeXml(success)}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <form:form method="post" modelAttribute="user" action="${pageContext.request.contextPath}/admin/users/save">
                            <form:hidden path="id"/>
                            <form:hidden path="createdAt"/>
                            <form:hidden path="role" value="${user.role != null ? user.role.name() : 'ADMIN'}"/> <!-- Chuỗi enum -->
                            <input type="hidden" name="size" value="${pageSize}"/>
                            <input type="hidden" name="keyword" value="${fn:escapeXml(keyword)}"/>
                            <div class="mb-3">
                                <label for="fullName" class="form-label">Full Name <span class="text-danger">*</span></label>
                                <form:input path="fullName" cssClass="form-control" id="fullName" placeholder="Enter Full Name" required="true"/>
                                <form:errors path="fullName" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                                <form:input path="email" type="email" cssClass="form-control" id="email" placeholder="Enter Email Address" required="true"/>
                                <form:errors path="email" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Password <span class="text-danger">${user.id == null ? '*' : ''}</span></label>
                                <form:input path="password" type="text" cssClass="form-control" id="password" placeholder="Enter Password" autocomplete="off"/>
                                <form:errors path="password" cssClass="text-danger small"/>
                                <small class="form-text text-muted">${user.id != null ? 'Current password is displayed. Leave blank to keep it or enter a new one.' : 'Password must be at least 8 characters'}</small>
                            </div>
                            <div class="mb-3">
                                <label for="phone" class="form-label">Phone</label>
                                <form:input path="phone" cssClass="form-control" id="phone" placeholder="Enter Phone Number"/>
                                <form:errors path="phone" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="roleDisplay" class="form-label">Role</label>
                                <input type="text" class="form-control" id="roleDisplay" value="${user.role != null ? user.role.name() : 'ADMIN'}" disabled="true"/>
                                <form:errors path="role" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="status" class="form-label">Status</label>
                                <form:select path="status" cssClass="form-control" id="status">
                                    <form:option value="ACTIVE" label="Active"/>
                                    <form:option value="INACTIVE" label="Inactive"/>
                                </form:select>
                                <form:errors path="status" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <button type="submit" class="btn btn-primary">Save</button>
                                <a href="${pageContext.request.contextPath}/admin/users?page=1&size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-secondary">Cancel</a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <div class="overlay toggle-menu"></div>
    </div>
</div>