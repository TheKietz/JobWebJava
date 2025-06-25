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
                        <div class="card-title">Application Form</div>
                        <h2>${isEdit ? 'Chỉnh sửa' : 'Thêm'} Banner</h2>
                        <hr>                    
                        <form:form modelAttribute="banner" action="${pageContext.request.contextPath}/admin/banners/save" enctype="multipart/form-data" method="post">

                            <form:hidden path="id" />

                            <div class="mb-3">
                                <form:label path="title" cssClass="form-label">Tiêu đề</form:label>
                                <form:input path="title" cssClass="form-control" required="true" />
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Ảnh banner</label>
                                <input type="file" name="imageFile" class="form-control" />
                                <c:if test="${banner.imageUrl != null}">
                                    <img src="${pageContext.request.contextPath}/uploads/${banner.imageUrl}" width="120" />
                                </c:if>
                            </div>

                            <div class="mb-3">
                                <form:label path="linkUrl" cssClass="form-label">Link đích (tùy chọn)</form:label>
                                    <input type="file" name="imageFile" class="form-control" />
                                </div>

                                <div class="mb-3">
                                <form:label path="position" cssClass="form-label">Vị trí</form:label>
                                <form:select path="position" cssClass="form-select" >
                                    <form:options items="${positions}" />
                                </form:select>
                            </div>

                            <div class="mb-3">
                                <form:label path="status" cssClass="form-label" >Trạng thái</form:label>
                                <form:select path="status" cssClass="form-select">
                                    <form:options items="${statuses}" />
                                </form:select>
                            </div>

                            <div class="mb-3">
                                <form:label path="startDate" cssClass="form-label">Ngày bắt đầu</form:label>
                                <form:input path="startDate" cssClass="form-control" type="date" />
                            </div>

                            <div class="mb-3">
                                <form:label path="endDate" cssClass="form-label">Ngày kết thúc</form:label>
                                <form:input path="endDate" cssClass="form-control" type="date" />
                            </div>

                            <button type="submit" class="btn btn-primary">Lưu</button>
                            <a href="${pageContext.request.contextPath}/admin/banners" class="btn btn-secondary">Hủy</a>
                        </form:form>
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