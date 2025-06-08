<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<main>
    <!-- Hero Area Start -->
    <div class="slider-area ">
        <div class="single-slider section-overly slider-height2 d-flex align-items-center" data-background="${pageContext.request.contextPath}/template/assets/img/hero/about.jpg">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="hero-cap text-center">
                            <h2>${job.title}</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Hero Area End -->

    <!-- Job Post Company Start -->
    <div class="job-post-company pt-120 pb-120">
        <div class="container">
            <div class="row justify-content-between">
                <!-- Left Content -->
                <div class="col-xl-7 col-lg-8">
                    <!-- Job Single -->
                    <div class="single-job-items mb-50">
                        <div class="job-items">
                            <div class="company-img company-img-details">
                                <a href="#"><img src="${pageContext.request.contextPath}/template/assets/img/icon/job-list1.png" alt=""></a>
                            </div>
                            <div class="job-tittle">
                                <a href="#">
                                    <h4>${job.title}</h4>
                                </a>
                                <ul>
                                    <li>Employer ID: ${job.employerID}</li>
                                    <li><i class="fas fa-map-marker-alt"></i> ${job.location}</li>
                                    <li>${job.salaryRange}</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <!-- Job Description -->
                    <div class="job-post-details">
                        <div class="post-details1 mb-50">
                            <div class="small-section-tittle">
                                <h4>Job Description</h4>
                            </div>
                            <p>${job.description}</p>
                        </div>
                    </div>
                </div>

                <!-- Right Content -->
                <div class="col-xl-4 col-lg-4">
                    <div class="post-details3  mb-50">
                        <div class="small-section-tittle">
                            <h4>Job Overview</h4>
                        </div>
                        <ul>
                            <li>Posted date: 
                                <span><fmt:formatDate value="${job.postedAt}" pattern="dd MMM yyyy" /></span>
                            </li>
                            <li>Location: <span>${job.location}</span></li>
                            <li>Job nature: <span>${job.jobType}</span></li>
                            <li>Salary: <span>${job.salaryRange}</span></li>
                            <li>Application deadline: 
                                <span><fmt:formatDate value="${job.expiryDate}" pattern="dd MMM yyyy" /></span>
                            </li>
                        </ul>
                        <div class="apply-btn2">
                            <a href="#" class="btn">Apply Now</a>
                        </div>
                    </div>

                    <!-- Placeholder company info -->
                    <div class="post-details4  mb-50">
                        <div class="small-section-tittle">
                            <h4>Company Information</h4>
                        </div>
                        <span>Company #${job.employerID}</span>
                        <p>(Ch?a có thông tin chi ti?t công ty ? b?n có th? tích h?p sau)</p>
                        <ul>
                            <li>Name: <span>Employer ${job.employerID}</span></li>
                            <li>Web: <span>example.com</span></li>
                            <li>Email: <span>contact@example.com</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
