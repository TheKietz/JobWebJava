<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Header -->

<jsp:include page="/WEB-INF/views/app/layout/header.jsp" />
      <!-- End Navbar -->
      <div class="content">
        <div class="row">
          <div class="col-md-12">
              <jsp:include page="${body}" />
          </div>
        </div>
      </div>
<jsp:include page="/WEB-INF/views/app/layout/footer.jsp" />