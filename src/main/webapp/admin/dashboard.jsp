<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bảng điều khiển - Quản trị khách sạn</title>
    <%@ include file="layout/header.jsp" %>

    <!-- CSS riêng cho admin -->
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
        <div class="col-md-3">
          <div class="card border-primary shadow-sm">
            <div class="card-body">
              <i class="bi bi-door-open fs-2 text-primary"></i>
              <h5 class="mt-2">Tổng số phòng</h5>
              <p class="fs-4 fw-bold text-primary">45</p>
            </div>
          </div>
        </div>

        <div class="col-md-3">
          <div class="card border-success shadow-sm">
            <div class="card-body">
              <i class="bi bi-person-check fs-2 text-success"></i>
              <h5 class="mt-2">Khách đang ở</h5>
              <p class="fs-4 fw-bold text-success">28</p>
            </div>
          </div>
        </div>

        <div class="col-md-3">
          <div class="card border-warning shadow-sm">
            <div class="card-body">
              <i class="bi bi-calendar-check fs-2 text-warning"></i>
              <h5 class="mt-2">Đơn đặt phòng</h5>
              <p class="fs-4 fw-bold text-warning">12</p>
            </div>
          </div>
        </div>

        <div class="col-md-3">
          <div class="card border-danger shadow-sm">
            <div class="card-body">
              <i class="bi bi-x-circle fs-2 text-danger"></i>
              <h5 class="mt-2">Hủy phòng</h5>
              <p class="fs-4 fw-bold text-danger">3</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Biểu đồ thống kê (placeholder) -->
      <div class="mt-5">
        <h4 class="fw-bold text-secondary mb-3"><i class="bi bi-graph-up-arrow me-2"></i>Thống kê đặt phòng gần đây</h4>
        <div class="p-5 bg-light border rounded text-center text-muted">
          (Sẽ hiển thị biểu đồ doanh thu hoặc lượt đặt phòng tại đây)
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
