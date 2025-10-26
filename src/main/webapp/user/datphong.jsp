<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đặt phòng</title>
  <jsp:include page="layout/header.jsp"/>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/user.css">
</head>
<body>
  <!-- Header -->
<jsp:include page="layout/nav.jsp"/>

  <!-- Nội dung chính -->
  <div class="container py-5">
    <h2 class="text-center text-primary mb-4">Đặt phòng - Phòng Đôi</h2>

    <div class="row g-4 justify-content-center">
      <!-- Thông tin phòng -->
      <div class="col-md-5">
        <div class="card shadow-sm">
          <img src="images/room2.jpg" class="card-img-top" alt="Phòng Đôi">
          <div class="card-body text-center">
            <h5 class="card-title text-primary">Phòng Đôi</h5>
            <p class="card-text fw-semibold">Giá: 800.000đ / đêm</p>
            <p class="text-muted">Phòng đầy đủ tiện nghi, sạch sẽ và thoải mái. Phù hợp cho kỳ nghỉ hoặc công tác.</p>
          </div>
        </div>
      </div>

      <!-- Form đặt phòng -->
      <div class="col-md-6">
        <form class="shadow p-4 bg-light rounded">
          <div class="mb-3">
            <label class="form-label">Họ tên</label>
            <input type="text" class="form-control" required>
          </div>

          <div class="mb-3">
            <label class="form-label">Số điện thoại</label>
            <input type="tel" class="form-control" required>
          </div>

          <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" class="form-control" required>
          </div>

          <div class="mb-3">
            <label class="form-label">Ngày nhận</label>
            <input type="date" class="form-control" required>
          </div>

          <div class="mb-3">
            <label class="form-label">Ngày trả</label>
            <input type="date" class="form-control" required>
          </div>

          <div class="text-center">
            <button type="submit" class="btn btn-primary px-4">Xác nhận đặt phòng</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- Footer -->
<jsp:include page="layout/footer.jsp"/>
</body>
</html>
