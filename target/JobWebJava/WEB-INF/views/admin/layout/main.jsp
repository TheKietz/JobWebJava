<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/admin/layout/header.jsp"/>
<%--<jsp:include page="${body}"/>--%>
<c:import url="${body}" />
<jsp:include page="/WEB-INF/views/admin/layout/footer.jsp"/>
