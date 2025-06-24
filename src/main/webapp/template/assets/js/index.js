$(function () {
    "use strict";

    // chart 1

    var ctx = document.getElementById('chart1').getContext('2d');

    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct"],
            datasets: [{
                    label: 'User Registrations',
                    data: [6, 0, 0, 0, 0, 6, 0, 0, 0, 0], // 6 cho Jan, 6 cho Jun
                    backgroundColor: '#fff',
                    borderColor: '#ffffff',
                    pointRadius: 3,
                    borderWidth: 2
                }]
        },
        options: {
            maintainAspectRatio: false,
            legend: {
                display: true,
                labels: {
                    fontColor: '#ddd',
                    boxWidth: 40
                }
            },
            scales: {
                xAxes: [{
                        ticks: {
                            fontColor: '#ddd'
                        },
                        gridLines: {
                            color: "rgba(221, 221, 221, 0.08)"
                        }
                    }],
                yAxes: [{
                        ticks: {
                            beginAtZero: true,
                            fontColor: '#ddd'
                        },
                        gridLines: {
                            color: "rgba(221, 221, 221, 0.08)"
                        }
                    }]
            }
        }
    });



    // chart 2

    var ctx = document.getElementById("chart2").getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ["Gói Tiêu Chuẩn", "Gói Nâng Cao"],
            datasets: [{
                    backgroundColor: [
                        "#ffffff",
                        "rgba(255, 255, 255, 0.5)"
                    ],
                    data: [99.99, 299.99],
                    borderWidth: [0, 0]
                }]
        },
        options: {
            maintainAspectRatio: false,
            legend: {
                position: "bottom",
                display: true,
                labels: {
                    fontColor: '#ddd',
                    boxWidth: 15
                }
            },
            tooltips: {
                displayColors: false
            }
        }
    });


document.addEventListener("DOMContentLoaded", function () {
    const chart1Canvas = document.getElementById("chart1");

    if (chart1Canvas) {
        const labels = JSON.parse(chart1Canvas.getAttribute("data-labels"));
        const candidates = JSON.parse(chart1Canvas.getAttribute("data-candidates"));
        const employers = JSON.parse(chart1Canvas.getAttribute("data-employers"));

        new Chart(chart1Canvas, {
            type: 'line',
            data: {
                labels: labels, // ngày/tháng/năm
                datasets: [
                    {
                        label: 'Ứng viên',
                        data: candidates,
                        backgroundColor: 'rgba(255,255,255,0.4)',
                        borderColor: '#ffffff',
                        borderWidth: 2,
                        fill: true,
                        tension: 0.4
                    },
                    {
                        label: 'Nhà tuyển dụng',
                        data: employers,
                        backgroundColor: 'rgba(200,200,200,0.4)',
                        borderColor: '#cccccc',
                        borderWidth: 2,
                        fill: true,
                        tension: 0.4
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        labels: {
                            color: '#fff'
                        }
                    }
                },
                scales: {
                    x: {
                        ticks: { color: '#fff' },
                        grid: { color: 'rgba(255,255,255,0.1)' }
                    },
                    y: {
                        beginAtZero: true,
                        ticks: { color: '#fff' },
                        grid: { color: 'rgba(255,255,255,0.1)' }
                    }
                }
            }
        });
    }
});


});
   