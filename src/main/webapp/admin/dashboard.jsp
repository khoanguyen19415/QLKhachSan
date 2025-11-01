<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bảng điều khiển - Quản trị khách sạn</title>
    <%@ include file="layout/header.jsp" %>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin.css">
</head>
<body>
    <%@ include file="layout/nav.jsp" %>

<div class="container-fluid">
  <div class="row">
    <!-- Sidebar -->
    <div class="col-md-2 bg-light border-end p-0">
      <%@ include file="layout/sidebar.jsp" %>
    </div>
    
    <!-- Nội dung chính -->
    <div class="col-md-10 mt-4">
      <h2 class="fw-bold text-primary mb-3">
        <i class="bi bi-speedometer2 me-2"></i>Bảng điều khiển
      </h2>
      <hr>

      <p>Chào mừng <strong>Quản trị viên</strong> đến với hệ thống quản lý khách sạn Sunshine!</p>

      <!-- 4 thẻ thống kê -->
      <div class="row text-center g-4 mt-4">
        <div class="col-12">
          <div class="stats-row">
            <div class="card card-border-primary">
              <div>
                <h5 class="stat-title">Tổng số phòng</h5>
                <p class="stat-number">${totalRooms}</p>
              </div>
              <div class="icon">
                <i class="bi bi-door-open"></i>
              </div>
            </div>

            <div class="card card-border-success">
              <div>
                <h5 class="stat-title">Khách đang ở</h5>
                <p class="stat-number">${guests}</p>
              </div>
              <div class="icon">
                <i class="bi bi-person-check"></i>
              </div>
            </div>

            <div class="card card-border-warning">
              <div>
                <h5 class="stat-title">Đơn đặt phòng</h5>
                <p class="stat-number">${bookings}</p>
              </div>
              <div class="icon">
                <i class="bi bi-calendar-check"></i>
              </div>
            </div>

            <div class="card card-border-danger">
              <div>
                <h5 class="stat-title">Hủy phòng</h5>
                <p class="stat-number">${cancellations}</p>
              </div>
              <div class="icon">
                <i class="bi bi-x-circle"></i>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</div>

<%@ include file="layout/footer.jsp" %>

<!-- JS Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
