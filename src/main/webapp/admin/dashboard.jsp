<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bảng điều khiển - Quản trị khách sạn</title>
    <%@ include file="layout/header.jsp" %>

    <!-- CSS riêng cho admin -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin.css">
    <style>
        /* Một vài tinh chỉnh nhỏ cho cards cho đẹp như ảnh */
        .stats-row .card {
            border-radius: 12px;
            padding: 18px 14px;
            min-height: 120px;
            display:flex;
            align-items:center;
            justify-content:space-between;
            gap:12px;
        }
        .stats-row .card .icon {
            width:64px;
            height:64px;
            display:flex;
            align-items:center;
            justify-content:center;
            border-radius:8px;
            font-size:28px;
        }
        .card-border-primary { border:1.5px solid #0d6efd; }
        .card-border-success { border:1.5px solid #198754; }
        .card-border-warning { border:1.5px solid #ffc107; }
        .card-border-danger  { border:1.5px solid #dc3545; }
        .stat-title { margin:0; color:#555; font-weight:600; }
        .stat-number { margin:0; font-size:28px; font-weight:800; }
    </style>
</head>
<body>
    <%@ include file="layout/nav.jsp" %>

<%
    // Lấy dữ liệu từ request (nếu chưa có -> dùng giá trị mặc định giống ảnh mẫu)
    Integer totalRooms = (Integer) request.getAttribute("totalRooms");
    Integer guestsStaying = (Integer) request.getAttribute("guestsStaying");
    Integer bookingsCount = (Integer) request.getAttribute("bookingsCount");
    Integer cancelledCount = (Integer) request.getAttribute("cancelledCount");

    if (totalRooms == null) totalRooms = 45;
    if (guestsStaying == null) guestsStaying = 28;
    if (bookingsCount == null) bookingsCount = 12;
    if (cancelledCount == null) cancelledCount = 3;
%>

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
      <div class="row text-center g-4 mt-4 stats-row">
        <div class="col-md-3">
          <div class="card card-border-primary shadow-sm">
            <div style="display:flex; align-items:center; width:100%; justify-content:space-between;">
                <div style="text-align:left;">
                    <div class="icon text-primary"><i class="bi bi-door-open"></i></div>
                    <h5 class="stat-title mt-2">Tổng số phòng</h5>
                    <p class="stat-number text-primary"><%= totalRooms %></p>
                </div>
                <div style="width:32px;"></div>
            </div>
          </div>
        </div>

        <div class="col-md-3">
          <div class="card card-border-success shadow-sm">
            <div style="display:flex; align-items:center; width:100%; justify-content:space-between;">
                <div style="text-align:left;">
                    <div class="icon text-success"><i class="bi bi-person-check"></i></div>
                    <h5 class="stat-title mt-2">Khách đang ở</h5>
                    <p class="stat-number text-success"><%= guestsStaying %></p>
                </div>
                <div style="width:32px;"></div>
            </div>
          </div>
        </div>

        <div class="col-md-3">
          <div class="card card-border-warning shadow-sm">
            <div style="display:flex; align-items:center; width:100%; justify-content:space-between;">
                <div style="text-align:left;">
                    <div class="icon text-warning"><i class="bi bi-calendar-check"></i></div>
                    <h5 class="stat-title mt-2">Đơn đặt phòng</h5>
                    <p class="stat-number text-warning"><%= bookingsCount %></p>
                </div>
                <div style="width:32px;"></div>
            </div>
          </div>
        </div>

        <div class="col-md-3">
          <div class="card card-border-danger shadow-sm">
            <div style="display:flex; align-items:center; width:100%; justify-content:space-between;">
                <div style="text-align:left;">
                    <div class="icon text-danger"><i class="bi bi-x-circle"></i></div>
                    <h5 class="stat-title mt-2">Hủy phòng</h5>
                    <p class="stat-number text-danger"><%= cancelledCount %></p>
                </div>
                <div style="width:32px;"></div>
            </div>
          </div>
        </div>
      </div> <!-- row -->
    </div>
  </div>
</div>

<%@ include file="layout/footer.jsp" %>

<!-- JS Bootstrap (nếu header.jsp chưa có) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
