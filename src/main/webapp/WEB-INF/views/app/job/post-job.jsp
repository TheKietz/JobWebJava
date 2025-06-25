<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tin tuyển dụng</title>
    </head>
    <body>
        
        <div class="table-responsive">
            <table class="table table-bordered bg-contrast">
                <thead class="table-light thead-success">
                    <tr>
                        <th class="col-2">Tiêu đề</th>  
                        <th class="col-1">Trạng thái</th>
                        <th class="col-2">Loại công việc</th>
                        <th class="col-2">Chức năng</th>
                    </tr>
                </thead>
                <tbody class="table table-bordered bg-white"> 
                    <c:choose>
                        <c:when test="${empty jobs}">
                            <tr>
                                <td colspan="12" class="text-center">No jobs found.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="job" items="${jobs}">
                                <tr>
                                    <td>${fn:escapeXml(job.title)}</td> 
                                    <td>
                                        <c:choose>
                                            <c:when test="${job.status=='APPROVED'}"><span class="badge bg-success">Đã duyệt</span > </c:when>
                                            <c:when test="${job.status=='PENDING'}"><span class="badge bg-secondary">Chờ duyệt</span > </c:when>
                                            <c:when test="${job.status=='REJECTED'}"><span class="badge bg-danger">Bị từ chối</span > </c:when>
                                            <c:when test="${job.status=='EXPIRED'}"><span class="badge bg-warning text-dark">Hết hạn</span > </c:when>
                                        </c:choose>
                                    </td>
                                    <td>${job.jobType}</td>                                         
                                    <td>
                                        <a href="${pageContext.request.contextPath}/app/job-post/details/${job.id}" class="btn btn-sm btn-primary">Chi tiết</a>
                                        <a href="${pageContext.request.contextPath}/app/job-post/delete/${job.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn muốn xóa bài đăng này?')">Xóa</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </body>
</html>
