<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container mt-5">
    <h2 class="mb-4 text-center">Giỏ hàng của bạn</h2>

    <%-- Hiển thị thông báo (nếu có) --%>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${fn:escapeXml(successMessage)}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${fn:escapeXml(errorMessage)}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty cart.cartItems}">
            <div class="alert alert-info text-center" role="alert">
                Giỏ hàng của bạn đang trống.
            </div>
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/app/service_packages" class="btn btn-primary btn-lg mt-3">Tiếp tục mua sắm</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="thead-light">
                                <tr>
                                    <th scope="col">Gói Dịch Vụ</th>
                                    <th scope="col" class="text-center">Giá</th>
                                    <th scope="col" class="text-center">Số lượng</th>
                                    <th scope="col" class="text-center">Thành tiền</th>
                                    <th scope="col" class="text-center">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="totalCartAmount" value="${0}"/>
                                <c:forEach var="item" items="${cart.cartItems}">
                                    <tr>
                                        <td>
                                            <%-- Kiểm tra null cho servicePackage trước khi truy cập --%>
                                            <c:if test="${not empty item.servicePackage}">
                                                <strong>${fn:escapeXml(item.servicePackage.name)}</strong>
                                                <br>
                                                <small class="text-muted">${fn:escapeXml(item.servicePackage.description)}</small>
                                            </c:if>
                                            <c:if test="${empty item.servicePackage}">
                                                <em>Gói không tồn tại (ID: ${item.packageId})</em>
                                            </c:if>
                                        </td>
                                        <td class="text-center">
                                            <fmt:formatNumber value="${item.priceAtAddition}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                                        </td>
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/app/shop/cart/update-quantity" method="post" class="d-inline-flex align-items-center">
                                                <input type="hidden" name="cartItemId" value="${item.id}">
                                                <input type="number" name="quantity" value="${item.quantity}" min="1" class="form-control form-control-sm text-center" style="width: 70px;">
                                                <button type="submit" class="btn btn-info btn-sm ms-2" title="Cập nhật số lượng">
                                                    <i class="fas fa-sync-alt"></i> <%-- Font Awesome Sync Icon --%>
                                                </button>
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <fmt:formatNumber value="${item.priceAtAddition * item.quantity}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                                            <c:set var="totalCartAmount" value="${totalCartAmount + (item.priceAtAddition * item.quantity)}"/>
                                        </td>
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/app/shop/cart/remove-item" method="post" style="display:inline;">
                                                <input type="hidden" name="cartItemId" value="${item.id}">
                                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa gói này khỏi giỏ hàng?')" title="Xóa sản phẩm">
                                                    <i class="fas fa-trash-alt"></i> <%-- Font Awesome Trash Icon --%>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr class="table-light">
                                    <th colspan="3" class="text-end py-3">Tổng cộng:</th>
                                    <td colspan="2" class="text-center py-3 fs-5 fw-bold text-primary">
                                        <fmt:formatNumber value="${totalCartAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>

            <div class="text-end mt-4">
                <a href="${pageContext.request.contextPath}/app/service_packages" class="btn btn-secondary btn-lg me-3">Tiếp tục mua sắm</a>
                <a href="${pageContext.request.contextPath}/app/shop/checkout" class="btn btn-success btn-lg">Tiến hành Thanh toán <i class="fas fa-arrow-right ms-2"></i></a>
            </div>
        </c:otherwise>
    </c:choose>
</div>