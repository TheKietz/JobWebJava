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
                            <th scope="col">Resume Url</th>
                            <th scope="col">Bio</th>
                            <th scope="col">Skills</th>
                            <th scope="col">Function</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="c" items="${candidates}" varStatus="status">
                            <tr>
                                <th scope="row">${(currentPage - 1) * pageSize + status.index + 1}</th>
                                <td>${(c.resumeUrl)}</td>
                                <td> ${(c.bio)}</td>
                                <td>${c.skills}</td>                                
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/candidates/edit/${c.candidateID}" class="btn btn-primary btn-sm">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/admin/candidates/delete/${c.candidateID}" 
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