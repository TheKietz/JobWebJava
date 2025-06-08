<<<<<<< HEAD
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/views/client/layout/header.jsp" />

<jsp:include page="${body}" />
=======
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/views/client/layout/header.jsp" />

<c:import url="${body}" />
>>>>>>> recover-lost-commits

<jsp:include page="/WEB-INF/views/client/layout/footer.jsp" />
