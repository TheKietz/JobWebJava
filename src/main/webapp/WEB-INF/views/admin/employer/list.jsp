<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="content-wrapper">
    <div class="container-fluid">
        <div class="card-body">
            <!-- Debug Info -->

            <div class="row">
                <div class="col-sm-3">
                    <h5 class="card-title">Employer List</h5>
                </div>                
            </div>
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Company Name</th>
                            <th scope="col">Website</th>
                            <th scope="col">Description</th>
                            <th scope="col">Logo</th>
                            <th scope="col">Function</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="c" items="${employers}" varStatus="status">
                            <tr>
                                <th scope="row">${(currentPage - 1) * pageSize + status.index + 1}</th>
                                <td>${(c.companyName)}</td>
                                <td> ${(c.website)}</td>
                                <td>${c.description}</td>
                                <td>                                                                       
                                    <img src="${c.logoUrl}" alt="logo" style="max-width: 50px; max-height: 50px;">
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/employers/edit/${c.employerID}" class="btn btn-primary btn-sm">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/admin/employers/delete/${c.employerID}" 
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Detele details of user name: \'' , '\'', '\\\'')}' + '\'?')">
                                        Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>