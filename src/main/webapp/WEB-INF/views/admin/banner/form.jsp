<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
    select.form-select {
        background-color: white !important;
        color: black !important;
    }

    select.form-select option {
        background-color: white;
        color: black;
    }
</style>

<div class="content-wrapper">
    <div class="container-fluid">            
        <div class="row justify-content-center"> 
            <div class="col-md-6"> 
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Banner Form</div>
                        <h2>${isEdit ? 'Chỉnh sửa' : 'Thêm'} Banner</h2>
                        <hr>                    
                        <form:form method="post" modelAttribute="banner" action="${pageContext.request.contextPath}/admin/banners/save" enctype="multipart/form-data">
                            <form:hidden path="id"/>
                            <div class="mb-3">
                                <label for="title" class="form-label">Tiêu đề</label>
                                <form:input path="title" cssClass="form-control" required="true"/>
                                <form:errors path="title" cssClass="text-danger"/>
                            </div>
                            <div class="mb-3">
                                <label for="imageFile" class="form-label">Ảnh banner</label>
                                <input type="file" name="imageFile" class="form-control" accept="image/*"/>
                                <c:if test="${banner.imageUrl != null}">
                                    <p>Current Image: <a href="${pageContext.request.contextPath}${banner.imageUrl}" target="_blank">${banner.imageUrl}</a></p>
                                    </c:if>
                            </div>
                            <div class="mb-3">
                                <label for="linkUrl" class="form-label">Link đích (tùy chọn)</label>
                                <form:input path="linkUrl" cssClass="form-control"/>
                                <form:errors path="linkUrl" cssClass="text-danger"/>
                            </div>
                            <div class="mb-3">
                                <label for="position" class="form-label">Vị trí</label>
                                <form:select path="position" cssClass="form-control">
                                    <form:options items="${positions}"/>
                                </form:select>
                                <form:errors path="position" cssClass="text-danger"/>
                            </div>
                            <div class="mb-3">
                                <label for="status" class="form-label">Trạng thái</label>
                                <form:select path="status" cssClass="form-control">
                                    <form:options items="${statuses}"/>
                                </form:select>
                                <form:errors path="status" cssClass="text-danger"/>
                            </div>
                            <div class="mb-3">
                                <label for="startDate" class="form-label">Ngày bắt đầu</label>
                                <form:input path="startDate" type="date" cssClass="form-control"/>
                                <form:errors path="startDate" cssClass="text-danger"/>
                            </div>
                            <div class="mb-3">
                                <label for="endDate" class="form-label">Ngày kết thúc</label>
                                <form:input path="endDate" type="date" cssClass="form-control"/>
                                <form:errors path="endDate" cssClass="text-danger"/>
                            </div>
                            <button type="submit" class="btn btn-primary">${isEdit ? 'Cập nhật' : 'Lưu'}</button>
                            <a href="${pageContext.request.contextPath}/admin/banners" class="btn btn-secondary">Hủy</a>
                        </form:form>

                        <c:if test="${not empty success}">
                            <div class="alert alert-success">${success}</div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                    </div>
                </div>   
            </div>
        </div>
        <!-- End Row -->
        <!-- Start overlay -->
        <div class="overlay toggle-menu"></div>
        <!-- End overlay -->
    </div>
    <!-- End container-fluid -->
</div>