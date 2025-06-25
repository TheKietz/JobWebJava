<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<div class="content-wrapper">
    <div class="container-fluid">
        <h2>Quản lý Banner</h2>
        <hr/>            
        <div class="card mb-3">
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <form action="${pageContext.request.contextPath}/admin/banners" method="get">
                            <div class="input-group">
                                <input type="text" name="keyword" class="form-control" placeholder="Search by title or category" value="${fn:escapeXml(keyword)}"/>
                                <input type="hidden" name="size" value="${pageSize}"/>
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-3 text-end">
                        <a href="${pageContext.request.contextPath}/admin/banners/add?size=${pageSize}&keyword=${fn:escapeXml(keyword)}" class="btn btn-success">Thêm Banner</a>
                    </div>
                    <div class="col-md-3 text-right">
                        <div >                    
                            <form action="${pageContext.request.contextPath}/admin/banners" method="get" class="form-inline d-inline">
                                <div class="form-group mr-2 mb-0 d-flex align-items-center">
                                    <label class="mr-2">Applications per page:</label>
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

            </div>

            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Tiêu đề</th>
                            <th>Ảnh</th>
                            <th>Link</th>
                            <th>Vị trí</th>
                            <th>Trạng thái</th>
                            <th>Thời gian</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty banners}">
                                <tr>
                                    <td colspan="12" class="text-center">No jobs found.</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="banner" items="${banners}">
                                    <tr>
                                        <td>${banner.title}</td>
                                        <td><img src="${pageContext.request.contextPath}/uploads/${banner.imageUrl}" width="100" alt="alt"/></td>
                                        <td><a href="${banner.linkUrl}" target="_blank">Xem</a></td>
                                        <td>${banner.position}</td>
                                        <td>${banner.status}</td>
                                        <td>${banner.startDate} → ${banner.endDate}</td>
                                        <td>
                                            <a href="<c:url value='/admin/banners/edit/${banner.id}'/>" class="btn btn-sm btn-warning">Sửa</a>
                                            <a href="<c:url value='/admin/banners/delete/${banner.id}'/>" class="btn btn-sm btn-danger" onclick="return confirm('Xóa banner này?')">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>                        
                    </tbody>
                </table>
            </div>
            <c:if test="${totalPages > 0}">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/banners?page=${currentPage - 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/banners?page=${i}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/banners?page=${currentPage + 1}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>
