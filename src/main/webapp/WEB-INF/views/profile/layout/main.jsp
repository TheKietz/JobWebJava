<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/views/profile/layout/header.jsp" />

<c:import url="${body}" />

<jsp:include page="/WEB-INF/views/profile/layout/footer.jsp" />