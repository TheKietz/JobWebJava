<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="content-wrapper">
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${employer.id == null ? 'Add Employer' : 'Edit Employer'}</h5>
                        <hr>
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
                        <form:form method="post" modelAttribute="employer" action="${pageContext.request.contextPath}/admin/employers/save" enctype="multipart/form-data">
                            <form:hidden path="id"/>
                            <input type="hidden" name="size" value="${pageSize}"/>
                            <input type="hidden" name="keyword" value="${fn:escapeXml(keyword)}"/>
                            <div class="mb-3">
                                <label for="userId" class="form-label">User ID <span class="text-danger">*</span></label>
                                <form:input path="userId" type="number" cssClass="form-control" id="userId" placeholder="Enter User ID" required="true"/>
                                <form:errors path="userId" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="companyName" class="form-label">Company Name <span class="text-danger">*</span></label>
                                <form:input path="companyName" cssClass="form-control" id="companyName" placeholder="Enter Company Name" required="true"/>
                                <form:errors path="companyName" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="companyName" class="form-label">Company Size <span class="text-danger">*</span></label>
                                <form:input path="companySize" cssClass="form-control" type="text" value="${employer.companySize}" id="companyName" placeholder="Enter Company Size" required="true"/>
                                <form:errors path="companySize" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Address <span class="text-danger">*</span></label>
                                <form:textarea path="address" cssClass="form-control" id="address" placeholder="Enter Address" rows="4" required="true"/>
                                <form:errors path="address" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="website" class="form-label">Website</label>
                                <form:input path="website" type="url" cssClass="form-control" id="website" placeholder="Enter Website (e.g., https://example.com)"/>
                                <form:errors path="website" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <form:textarea path="description" cssClass="form-control" id="description" placeholder="Enter Description (optional)" rows="4"/>
                                <form:errors path="description" cssClass="text-danger small"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Logo URL</label>
                                <input type="file" name="imageFile" class="form-control" />
                                <c:if test="${employer.logoUrl != null}">
                                    <img src="${pageContext.request.contextPath}/uploads/${employer.logoUrl}" width="120" />
                                </c:if>
                            </div>
                            <div class="mb-3">
                                <button type="submit" class="btn btn-primary">Lưu</button>
                                <a href="${pageContext.request.contextPath}/admin/employers?page=1&size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-secondary">Hủy</a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <div class="overlay toggle-menu"></div>
    </div>
</div>
