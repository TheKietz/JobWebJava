<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
    /* CSS tùy chỉnh cho dropdown cập nhật trạng thái */
    .form-select.custom-status-select {
        border: 1px solid #17a2b8; /* Viền màu xanh info hoặc primary */
        box-shadow: 0 0 0.5rem rgba(23, 162, 184, 0.25); /* Thêm bóng đổ nhẹ */
        font-weight: 500; /* Làm chữ đậm hơn một chút */
        color: #212529; /* Đảm bảo màu chữ rõ ràng (màu đen) */
        background-color: #f8f9fa; /* Hoặc một màu nền nhạt để nổi bật hơn */
        transition: all 0.2s ease-in-out; /* Hiệu ứng chuyển động nhẹ */
    }

    .form-select.custom-status-select:focus {
        border-color: #138496; /* Viền đậm hơn khi focus */
        box-shadow: 0 0 0.75rem rgba(19, 132, 150, 0.5); /* Bóng đổ mạnh hơn khi focus */
        outline: 0;
    }

    /* Các màu nền cho option (tùy chọn, có thể không hoạt động đồng nhất trên mọi trình duyệt) */
    .custom-status-select option[value="PENDING"] { background-color: #cfe2ff; color: #084298; } /* Light blue */
    .custom-status-select option[value="COMPLETED"] { background-color: #d1e7dd; color: #0f5132; } /* Light green */
    .custom-status-select option[value="CANCELLED"] { background-color: #f8d7da; color: #842029; } /* Light red */
    .custom-status-select option[value="EXPIRED"] { background-color: #fff3cd; color: #664d03; } /* Light yellow */
    .custom-status-select option[value="FAILED"] { background-color: #e2e3e5; color: #212529; } /* Light grey/dark */
    /* Nếu bạn muốn màu chữ của option cũng là màu của badge, hãy thử các dòng trên.
       Lưu ý rằng hỗ trợ CSS cho <option> không hoàn hảo trên mọi trình duyệt.
       Màu nền có thể không hiển thị, nhưng màu chữ thường hoạt động. */
</style>
<div class="content-wrapper">
    <div class="container-fluid">
        <h2>Quản lý Đơn hàng (Admin)</h2>
        <hr>
        <%-- Hiển thị thông báo (success, error, message) --%>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${fn:escapeXml(successMessage)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${fn:escapeXml(errorMessage)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-info alert-dismissible fade show" role="alert">
                ${fn:escapeXml(message)}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <div class="card mb-3">
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <%-- Form tìm kiếm (tùy chọn: nếu bạn muốn tìm kiếm theo ID đơn hàng, tên khách hàng, v.v.) --%>
                        <form action="${pageContext.request.contextPath}/admin/orders" method="get">
                            <div class="input-group">
                                <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm đơn hàng..." value="${fn:escapeXml(keyword)}"/>
                                <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6 text-end">
                        <%-- Không có nút "Add Order" vì đơn hàng được tạo qua checkout --%>
                    </div>
                </div>

                <%-- Tabs lọc theo trạng thái --%>
                <ul class="nav nav-pills nav-fill mb-4">
                    <li class="nav-item">
                        <a class="nav-link ${statusFilter == 'ALL' ? 'active bg-primary text-white' : 'text-dark'}" href="${pageContext.request.contextPath}/admin/orders?status=all">
                            Tất cả <span class="badge bg-light text-dark ms-1">0</span> <%-- Số lượng động sẽ được thêm sau --%>
                        </a>
                    </li>
                    <c:forEach var="status" items="${allStatuses}">
                        <li class="nav-item">
                            <a class="nav-link ${statusFilter == status.name() ? 'active bg-primary text-white' : 'text-dark'}" href="${pageContext.request.contextPath}/admin/orders?status=${status.name()}">
                                <c:choose>
                                    <c:when test="${status == 'PENDING'}">Đang chờ</c:when>
                                    <c:when test="${status == 'COMPLETED'}">Hoàn thành</c:when>
                                    <c:when test="${status == 'CANCELLED'}">Đã hủy</c:when>
                                    <c:when test="${status == 'FAILED'}">Thất bại</c:when>
                                    <c:when test="${status == 'EXPIRED'}">Hết hạn</c:when>
                                    <c:otherwise>${fn:escapeXml(status.name())}</c:otherwise>
                                </c:choose>
                                <span class="badge bg-light text-dark ms-1">0</span>
                            </a>
                        </li>
                    </c:forEach>
                </ul>

               <div class="table-responsive">
                    <table class="table table-bordered table-hover bg-info-light2">
                        <thead class="table-active">
                            <tr>
                                <th>#</th>
                                <th>Mã Đơn</th>
                                <th>Khách hàng</th>
                                <th>Ngày Đặt</th>
                                <th class="text-end">Tổng tiền</th>
                                <th>Trạng thái</th>
                                <th>Thanh toán</th>
                                <th class="text-center">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty orders}">
                                    <tr>
                                        <td colspan="8" class="text-center py-4">
                                            <img src="https://placehold.co/150x100/e0f2f7/2196f3?text=Không+có+đơn+hàng" alt="Không có đơn hàng" class="img-fluid mb-2">
                                            <p class="text-muted">Không tìm thấy đơn hàng nào với trạng thái này.</p>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="order" items="${orders}" varStatus="loop">
                                        <tr>
                                            <td>${loop.count + (currentPage - 1) * pageSize}</td> <%-- Cần currentPage và pageSize từ Controller nếu có phân trang --%>
                                            <td><strong>#${fn:escapeXml(order.id)}</strong></td>
                                            <td>
                                                <c:if test="${not empty order.user}">
                                                    ${fn:escapeXml(order.user.fullName)} <br>
                                                    <small class="text-muted">${fn:escapeXml(order.user.email)}</small>
                                                </c:if>
                                                <c:if test="${empty order.user}">
                                                    <em>Người dùng không tồn tại</em>
                                                </c:if>
                                            </td>
                                            <td><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                            <td class="text-end"><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0" /></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${order.status == 'PENDING'}"><span class="badge bg-info text-dark badge-pill">Đang chờ</span></c:when>
                                                    <c:when test="${order.status == 'COMPLETED'}"><span class="badge bg-success badge-pill">Hoàn thành</span></c:when>
                                                    <c:when test="${order.status == 'CANCELLED'}"><span class="badge bg-danger badge-pill">Đã hủy</span></c:when>
                                                    <c:when test="${order.status == 'EXPIRED'}"><span class="badge bg-warning text-dark badge-pill">Hết hạn</span></c:when>
                                                    <c:when test="${order.status == 'FAILED'}"><span class="badge bg-dark badge-pill">Thất bại</span></c:when>
                                                    <c:otherwise><span class="badge bg-secondary text-dark badge-pill">${fn:escapeXml(order.status)}</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${order.paymentStatus == 'PAID'}"><span class="badge bg-success badge-pill">Đã thanh toán</span></c:when>
                                                    <c:when test="${order.paymentStatus == 'UNPAID'}"><span class="badge bg-danger badge-pill">Chưa thanh toán</span></c:when>
                                                    <c:when test="${order.paymentStatus == 'REFUNDED'}"><span class="badge bg-info text-dark badge-pill">Đã hoàn tiền</span></c:when>
                                                    <c:otherwise><span class="badge bg-secondary text-dark badge-pill">${fn:escapeXml(order.paymentStatus)}</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="text-center">
                                                <%-- Nút xem chi tiết (chuyển hướng đến view_order_details chung) --%>
                                                <a href="${pageContext.request.contextPath}/admin/orders/${order.id}/details" class="btn btn-info btn-sm mb-1 d-block mx-auto">Xem chi tiết <i class="fas fa-eye"></i></a>
                                                
                                                <%-- FORM CẬP NHẬT TRẠNG THÁI --%>
                                                <%-- URL action trỏ đến AdminOrderController --%>
                                                <form action="${pageContext.request.contextPath}/admin/orders/update-status" method="post" class="mt-1 d-block mx-auto">
                                                    <input type="hidden" name="orderId" value="${order.id}">
                                                    <input type="hidden" name="currentStatusFilter" value="${statusFilter}">
                                                    <select name="newStatus" class="form-select form-select-sm" onchange="this.form.submit()">
                                                        <c:forEach var="statusOption" items="${allStatuses}">
                                                            <option value="${statusOption.name()}" ${order.status == statusOption ? 'selected' : ''}>
                                                                <c:choose>
                                                                    <c:when test="${statusOption == 'PENDING'}">Đang chờ</c:when>
                                                                    <c:when test="${statusOption == 'COMPLETED'}">Hoàn thành</c:when>
                                                                    <c:when test="${statusOption == 'CANCELLED'}">Đã hủy</c:when>
                                                                    <c:when test="${statusOption == 'EXPIRED'}">Hết hạn</c:when>
                                                                    <c:when test="${statusOption == 'FAILED'}">Thất bại</c:when>
                                                                    <c:otherwise>${fn:escapeXml(statusOption.name())}</c:otherwise>
                                                                </c:choose>
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
                <%-- Có thể thêm phân trang tương tự như bạn có trong Employer Management --%>
                <%--
                <c:if test="${totalPages > 1}">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/admin/orders?page=${i}&size=${pageSize}&keyword=${fn:escapeXml(keyword)}&status=${fn:escapeXml(statusFilter)}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:if>
                --%>
            </div>
        </div>
    </div>
</div>
