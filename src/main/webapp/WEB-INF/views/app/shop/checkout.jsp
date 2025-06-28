<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container mt-5">
    <h2 class="mb-4 text-center">Xác nhận và Thanh toán</h2>

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

    <div class="row">
        <div class="col-lg-8">
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-light">
                    <h5 class="mb-0">Các gói trong đơn hàng</h5>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-borderless align-middle">
                            <thead>
                                <tr>
                                    <th scope="col">Gói Dịch Vụ</th>
                                    <th scope="col" class="text-center">Giá</th>
                                    <th scope="col" class="text-center">Số lượng</th>
                                    <th scope="col" class="text-end">Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="totalCheckoutAmount" value="${0}"/>
                                <c:forEach var="item" items="${cart.cartItems}">
                                    <tr>
                                        <td>
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
                                            <fmt:formatNumber value="${item.priceAtAddition}" type="currency" currencySymbol="₫"  />
                                        </td>
                                        <td class="text-center">${item.quantity}</td>
                                        <td class="text-end">
                                            <fmt:formatNumber value="${item.priceAtAddition * item.quantity}" type="currency" currencySymbol="₫"  />
                                            <c:set var="totalCheckoutAmount" value="${totalCheckoutAmount + (item.priceAtAddition * item.quantity)}"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr class="table-light">
                                    <th colspan="3" class="text-end py-3">Tổng cộng:</th>
                                    <td class="text-end py-3 fs-5 fw-bold text-primary">
                                        <fmt:formatNumber value="${totalCheckoutAmount}" type="currency" currencySymbol="₫"  />
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-light">
                    <h5 class="mb-0">Thông tin Thanh toán</h5>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/app/shop/place-order" method="post">
                        <div class="mb-3">
                            <label for="paymentMethod" class="form-label">Chọn phương thức thanh toán:</label>
                            <select class="form-select" id="paymentMethod" name="paymentMethod" required>
                                <option value="" selected disabled>-- Chọn phương thức --</option>
                                <option value="Bank Transfer">Chuyển khoản Ngân hàng</option>
                                <option value="Momo">Momo</option>
                                <option value="Credit Card">Thẻ tín dụng (sắp ra mắt)</option>
                                <option value="Cash">Tiền mặt (tại văn phòng)</option>
                            </select>
                        </div>
                        <p class="text-muted small">
                            Lưu ý: Các phương thức "Thẻ tín dụng" và "Tiền mặt" hiện chỉ mang tính chất minh họa.
                            Bạn sẽ cần tích hợp cổng thanh toán thật sự cho chức năng này.
                        </p>
                        <hr>
                        <div class="d-grid gap-2">
                             <button type="submit" class="btn btn-success btn-lg">Xác nhận và Đặt hàng <i class="fas fa-check-circle ms-2"></i></button>
                             <a href="${pageContext.request.contextPath}/app/shop/cart" class="btn btn-outline-secondary btn-lg">Quay lại Giỏ hàng</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>