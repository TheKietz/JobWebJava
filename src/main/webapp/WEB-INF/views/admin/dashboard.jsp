<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="content-wrapper">
    <div class="container-fluid">
        <!--Start Dashboard Content-->
        <div class="card mt-3">
            <div class="card-content">
                <div class="row row-group m-0">
                    <div class="col-12 col-lg-6 col-xl-3 border-light">
                        <div class="card-body">
                            <h5 class="text-white mb-0">${countCandidates} <span class="float-right"><i class="fa fa-shopping-cart"></i></span></h5>
                            <div class="progress my-3" style="height:3px;">
                                <div class="progress-bar" style="width:55%"></div>
                            </div>
                            <p class="mb-0 text-white small-font">Tổng số ứng viên <span class="float-right">+4.2% <i class="zmdi zmdi-long-arrow-up"></i></span></p>
                        </div>
                    </div>
                    <div class="col-12 col-lg-6 col-xl-3 border-light">
                        <div class="card-body">
                            <h5 class="text-white mb-0">${countEmployers} <span class="float-right"><i class="fa fa-vnv"></i></span></h5>
                            <div class="progress my-3" style="height:3px;">
                                <div class="progress-bar" style="width:55%"></div>
                            </div>
                            <p class="mb-0 text-white small-font">Tổng số nhà tuyển dụng <span class="float-right">+1.2% <i class="zmdi zmdi-long-arrow-up"></i></span></p>
                        </div>
                    </div>
                    <div class="col-12 col-lg-6 col-xl-3 border-light">
                        <div class="card-body">
                            <h5 class="text-white mb-0"><fmt:formatNumber value="${revenueToday}" pattern="#,###"/> VNĐ</h5>
                            <div class="progress my-3" style="height:3px;">
                                <div class="progress-bar" style="width:55%"></div>
                            </div>
                            <p class="mb-0 text-white small-font">Doanh thu hôm nay <span class="float-right">+5.2% <i class="zmdi zmdi-long-arrow-up"></i></span></p>
                        </div>
                    </div>
                    <div class="col-12 col-lg-6 col-xl-3 border-light">
                        <div class="card-body">
                            <h5 class="text-white mb-0">5630 <span class="float-right"><i class="fa fa-envira"></i></span></h5>
                            <div class="progress my-3" style="height:3px;">
                                <div class="progress-bar" style="width:55%"></div>
                            </div>
                            <p class="mb-0 text-white small-font">Messages <span class="float-right">+2.2% <i class="zmdi zmdi-long-arrow-up"></i></span></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>  
        <div class="row">
            <div class="col-12 col-lg-8 col-xl-8">
                <div class="card">
                    <div class="card-header">Thống kê đăng ký người dùng
                        <div class="card-action">
                            <div class="dropdown">
                                <a href="javascript:void();" class="dropdown-toggle dropdown-toggle-nocaret" data-toggle="dropdown">
                                    <i class="icon-options"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <a class="dropdown-item" href="javascript:void();">Action</a>
                                    <a class="dropdown-item" href="javascript:void();">Another action</a>
                                    <a class="dropdown-item" href="javascript:void();">Something else here</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="javascript:void();">Separated link</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="chart-container-1">
                            <canvas id="chart1"
                                    data-labels='${registrationDatesJson}'
                                    data-candidates='${newCandidatesJson}'
                                    data-employers='${newEmployersJson}'></canvas>
                        </div>
                    </div>
                    <div class="row m-0 row-group text-center border-top border-light-3">
                        <div class="col-12 col-lg-4">
                            <div class="p-3">
                                <h5 class="mb-0"><fmt:formatNumber value="${overallVisitors}" pattern="#,###"/> <c:if test="${overallVisitors > 0}">M</c:if></h5>
                                    <small class="mb-0">Overall Visitor <span> <i class="fa fa-arrow-up"></i> 2.43%</span></small>
                                </div>
                            </div>
                            <div class="col-12 col-lg-4">
                                <div class="p-3">
                                    <h5 class="mb-0">${avgVisitDuration}</h5>
                                <small class="mb-0">Visitor Duration <span> <i class="fa fa-arrow-up"></i> 12.65%</span></small>
                            </div>
                        </div>
                        <div class="col-12 col-lg-4">
                            <div class="p-3">
                                <h5 class="mb-0"><fmt:formatNumber value="${avgPagesPerVisit}" pattern="#,###.##"/></h5>
                                <small class="mb-0">Pages/Visit <span> <i class="fa fa-arrow-up"></i> 5.62%</span></small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12 col-lg-4 col-xl-4">
                <div class="card">
                    <div class="card-header">Doanh thu theo gói dịch vụ
                        <div class="card-action">
                            <div class="dropdown">
                                <a href="javascript:void();" class="dropdown-toggle dropdown-toggle-nocaret" data-toggle="dropdown">
                                    <i class="icon-options"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <a class="dropdown-item" href="javascript:void();">Action</a>
                                    <a class="dropdown-item" href="javascript:void();">Another action</a>
                                    <a class="dropdown-item" href="javascript:void();">Something else here</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="javascript:void();">Separated link</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="chart-container-2">
                            <canvas id="chart2"></canvas>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table align-items-center">
                            <tbody>
                                <tr>
                                    <td><i class="fa fa-circle text-white mr-2"></i>Gói nâng cao</td>
                                    <td>$5856</td>
                                    <td>+55%</td>
                                </tr>
                                <tr>
                                    <td><i class="fa fa-circle text-light-1 mr-2"></i>Gói tiêu chuẩn</td>
                                    <td>$2602</td>
                                    <td>+25%</td>
                                </tr>
                                <tr>
                                    <td><i class="fa fa-circle text-light-2 mr-2"></i>Gói cao cấp</td>
                                    <td>$1802</td>
                                    <td>+15%</td>
                                </tr>                                
                            </tbody>
                        </table>
                    </div>
                    <div class="table-responsive">
                        <table class="table align-items-center">
                            <tbody>
                                <c:forEach var="packageName" items="${packageNames}" varStatus="loop">
                                    <c:set var="revenue" value="${packageRevenues[loop.index]}" />
                                    <tr>
                                        <td><i class="fa fa-circle mr-2" style="color: ${loop.index == 0 ? 'white' : loop.index == 1 ? '#f0f0f0' : loop.index == 2 ? '#d0d0d0' : '#b0b0b0'}"></i> ${packageName}</td>
                                        <td><fmt:formatNumber value="${revenue}" pattern="#,###"/> VNĐ</td>
                                        <td>+${loop.index * 10 + 5}%</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div><!--End Row-->
        <div class="row">
            <div class="col-12 col-lg-12">
                <div class="card">
                    <div class="card-header">Top việc làm nổi bật
                        <div class="card-action">
                            <div class="dropdown">
                                <a href="javascript:void();" class="dropdown-toggle dropdown-toggle-nocaret" data-toggle="dropdown">
                                    <i class="icon-options"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <a class="dropdown-item" href="javascript:void();">Action</a>
                                    <a class="dropdown-item" href="javascript:void();">Another action</a>
                                    <a class="dropdown-item" href="javascript:void();">Something else here</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="javascript:void();">Separated link</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table align-items-center table-flush table-borderless">
                            <thead>
                                <tr>
                                    <th>Loại</th>
                                    <th>Tiêu đề</th>
                                    <th>Tổng ứng viên</th>                                   
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="job" items="${top10Jobs}">
                                    <tr>
                                        <td>${job.category}</td>
                                        <td>${job.title}</td>
                                        <td>${job.totalApplications}</td>                                
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div><!--End Row-->

        <!--End Dashboard Content-->

        <!--start overlay-->
        <div class="overlay toggle-menu"></div>
        <!--end overlay-->

    </div>
    <!-- End container-fluid-->

</div><!--End content-wrapper-->
<!--Start Back To Top Button-->
<a href="javaScript:void();" class="back-to-top"><i class="fa fa-angle-double-up"></i> </a>
<!--End Back To Top Button-->

<!-- Thêm thư viện Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.3/dist/chart.umd.min.js"></script>
<!-- Thêm script cho biểu đồ -->
<script>
    const packageLabels = ${packageNamesJson};
    const packageRevenues = ${packageRevenuesJson};

    // Biểu đồ 1: Thống kê đăng ký người dùng
    const registrationDates = ${registrationDatesJson};
    const newCandidates = ${newCandidatesJson};
    const newEmployers = ${newEmployersJson};

    const ctx = document.getElementById('chart1').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: registrationDates, // X: ngày
            datasets: [
                {
                    label: 'Ứng viên',
                    data: newCandidates, // Y1
                    borderColor: '#ffffff',
                    backgroundColor: 'transparent',
                    borderWidth: 2
                },
                {
                    label: 'Nhà tuyển dụng',
                    data: newEmployers, // Y2
                    borderColor: 'rgba(255,255,255,0.5)',
                    backgroundColor: 'transparent',
                    borderWidth: 2
                }
            ]
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    ticks: {
                        color: "#ddd"
                    }
                },
                y: {
                    beginAtZero: true,
                    ticks: {
                        color: "#ddd"
                    }
                }
            },
            plugins: {
                legend: {
                    labels: {
                        color: "#ddd"
                    }
                }
            }
        }
    });


    // Biểu đồ 2: Doanh thu theo gói dịch vụ
    const ctx2 = document.getElementById("chart2").getContext('2d');
    new Chart(ctx2, {
        type: 'doughnut',
        data: {
            labels: packageLabels,
            datasets: [{
                    data: packageRevenues,
                    backgroundColor: [
                        '#ffffff',
                        'rgba(255, 255, 255, 0.7)',
                        'rgba(255, 255, 255, 0.4)',
                        'rgba(255, 255, 255, 0.2)'
                    ],
                    borderWidth: 0
                }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        color: '#ddd',
                        boxWidth: 15
                    }
                }
            }
        }
    });
</script>



