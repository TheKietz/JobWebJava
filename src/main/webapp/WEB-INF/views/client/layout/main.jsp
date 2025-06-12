<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Header -->
<jsp:include page="/WEB-INF/views/client/layout/header.jsp" />

<c:import url="${body}" />

<jsp:include page="/WEB-INF/views/client/layout/footer.jsp" />

