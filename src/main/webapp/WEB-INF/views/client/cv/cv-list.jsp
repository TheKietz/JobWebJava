<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach var="cv" items="${cvs}">
    <div class="card mb-3">
        <div class="card-body">
            <h5>${cv.title}</h5>
            <p>${cv.summary}</p>
            <a href="${pageContext.request.contextPath}${cv.fileUrl}" class="btn btn-primary" target="_blank">Tải CV</a>
            <form method="post" action="${pageContext.request.contextPath}/cv/delete/${cv.id}" style="display:inline;">
                <button class="btn btn-danger btn-sm" type="submit">Xóa</button>
            </form>
        </div>
    </div>
</c:forEach>
