<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--Start footer-->
<footer class="footer">
    <div class="container">
        <div class="text-center">
           
        </div>
    </div>
</footer>
<!--End footer-->

<!--start color switcher-->
<div class="right-sidebar">
    <div class="switcher-icon">
        <i class="zmdi zmdi-settings zmdi-hc-spin"></i>
    </div>
    <div class="right-sidebar-content">

        <p class="mb-0">Gaussion Texture</p>
        <hr>

        <ul class="switcher">
            <li id="theme1"></li>
            <li id="theme2"></li>
            <li id="theme3"></li>
            <li id="theme4"></li>
            <li id="theme5"></li>
            <li id="theme6"></li>
        </ul>

        <p class="mb-0">Gradient Background</p>
        <hr>

        <ul class="switcher">
            <li id="theme7"></li>
            <li id="theme8"></li>
            <li id="theme9"></li>
            <li id="theme10"></li>
            <li id="theme11"></li>
            <li id="theme12"></li>
            <li id="theme13"></li>
            <li id="theme14"></li>
            <li id="theme15"></li>
        </ul>

    </div>
</div>
<!--end color switcher-->

</div><!--End wrapper-->
<!--end color switcher-->
<!-- Bootstrap core JavaScript-->
<script src="<c:url value='/template/admin/assets/js/jquery.min.js'/>"></script>
<script src="<c:url value='/template/admin/assets/js/popper.min.js'/>"></script>
<script src="<c:url value='/template/admin/assets/js/bootstrap.min.js'/>"></script>

<!-- simplebar js -->
<script src="<c:url value='/template/admin/assets/plugins/simplebar/js/simplebar.js'/>"></script>
<!-- sidebar-menu js -->
<script src="<c:url value='/template/admin/assets/js/sidebar-menu.js'/>"></script>
<!-- loader scripts -->
<script src="<c:url value='/template/admin/assets/js/jquery.loading-indicator.js'/>"></script>
<!-- Custom scripts -->
<script src="<c:url value='/template/admin/assets/js/app-script.js'/>"></script>
<!-- Chart js -->

<script src="<c:url value='/template/admin/assets/plugins/Chart.js/Chart.min.js'/>"></script>

<!-- Index js -->
<%--<script src="${pageContext.request.contextPath}/template/admin/assets/js/index.js"></script>--%>

</body>
</html>
