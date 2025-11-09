<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thống kê - Sunshine Hotel</title>
        <%@ include file="layout/header.jsp" %>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/admin.css">
    </head>
    <body>
        <%@ include file="layout/nav.jsp" %>

        <div class="container-fluid">
            <div class="row">
                <!-- SIDEBAR BÊN TRÁI -->
                <div class="col-md-2 bg-light border-end p-0">
                    <%@ include file="layout/sidebar.jsp" %>
                </div>

                <!-- NỘI DUNG CHÍNH BÊN PHẢI -->
                <div class="col-md-10 p-4">
                    <h3 class="text-primary fw-bold mb-4">
                        <i class="bi bi-bar-chart-fill me-2"></i>Thống kê tổng quan
                    </h3>

                    <!-- 4 Ô THỐNG KÊ NHANH -->
                    <div class="row g-4 mb-4">
                        <div class="col-md-3">
                            <div class="card text-center text-white bg-primary p-3">
                                <div class="card-body">
                                    <i class="bi bi-door-open fs-1 mb-2"></i>
                                    <h4>Phòng đang hoạt động</h4>
                                    <div class="value">${phongHoatDong}</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card text-center text-white bg-success p-3">
                                <div class="card-body">
                                    <i class="bi bi-people-fill fs-1 mb-2"></i>
                                    <h4>Khách đang lưu trú</h4>
                                    <div class="value">${khachDangLuuTru}</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card text-center text-white bg-warning p-3">
                                <div class="card-body">
                                    <i class="bi bi-cash-coin fs-1 mb-2"></i>
                                    <h4>Doanh thu tháng</h4>
                                    <div class="value">
                                        <fmt:formatNumber value="${doanhThuThang}" type="number" groupingUsed="true"/> ₫
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="card text-center text-white bg-danger p-3">
                                <div class="card-body">
                                    <i class="bi bi-exclamation-triangle fs-1 mb-2"></i>
                                    <h4>Phòng cần bảo trì</h4>
                                    <div type="number" class="value">${phongCanBaoTri}</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- BIỂU ĐỒ DOANH THU -->
                    <div class="chart-container mb-4">
                        <h5 class="fw-bold text-primary">
                            <i class="bi bi-graph-up-arrow me-2"></i>Doanh thu 6 tháng gần nhất
                        </h5>
                        <canvas id="chartDoanhThu" height="100"></canvas>
                    </div>

                    <!-- BIỂU ĐỒ TỈ LỆ ĐẶT PHÒNG -->
                    <div class="chart-container">
                        <h5 class="fw-bold text-primary">
                            <i class="bi bi-calendar-week me-2"></i>Tỉ lệ đặt phòng theo loại
                        </h5>
                        <canvas id="chartLoaiPhong" height="100"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <%@ include file="layout/footer.jsp" %>

        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>
<script>
fetch("${pageContext.request.contextPath}/Thong-Ke?action=chart")
    .then(res => res.json())
    .then(data => {
        // Biểu đồ doanh thu
        const ctx1 = document.getElementById('chartDoanhThu');
        new Chart(ctx1, {
            type: 'line',
            data: {
                labels: ['Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10'],
                datasets: [{
                    label: 'Doanh thu (triệu đồng)',
                    data: data.doanhThu,
                    borderColor: '#007bff',
                    backgroundColor: 'rgba(0,123,255,0.1)',
                    tension: 0.3,
                    fill: true
                }]
            }
        });

        // Biểu đồ loại phòng
        const ctx2 = document.getElementById('chartLoaiPhong');
        new Chart(ctx2, {
            type: 'doughnut',
            data: {
                labels: ['Standard', 'Deluxe', 'Suite', 'VIP'],
                datasets: [{
                    data: data.tiLe,
                    backgroundColor: ['#0d6efd', '#198754', '#ffc107', '#dc3545']

                }]
            },
            options: { plugins: { legend: { position: 'bottom' } } }
        });
    });
</script>

    </body>
</html>
