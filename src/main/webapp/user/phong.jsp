<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <title>Danh sách phòng - Sunshine Hotel</title>
  <jsp:include page="layout/header.jsp"/>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/user.css">
</head>
<body>
<jsp:include page="layout/nav.jsp"/>

<div class="container py-4">
  <h2 class="text-center text-primary mb-4">Danh sách phòng</h2>

  <div class="row g-4">
    <!-- Phòng 1 -->
    <div class="col-md-4">
      <div class="card shadow">
        <img src="/images/room1.jpg" class="card-img-top" alt="Phòng đơn">
        <div class="card-body text-center">
          <h5 class="card-title">Phòng Đơn</h5>
          <p>Giá: 500.000đ / đêm</p>
          <a href="dat-phong" class="btn btn-primary">Đặt ngay</a>
        </div>
      </div>
    </div>

    <!-- Phòng 2 -->
    <div class="col-md-4">
      <div class="card shadow">
        <img src="../assets/images/room2.jpg" class="card-img-top" alt="Phòng đôi">
        <div class="card-body text-center">
          <h5 class="card-title">Phòng Đôi</h5>
          <p>Giá: 800.000đ / đêm</p>
          <a href="dat-phong" class="btn btn-primary">Đặt ngay</a>
        </div>
      </div>
    </div>

    <!-- Phòng VIP -->
    <div class="col-md-4">
      <div class="card shadow">
        <img src="../assets/images/room3.jpg" class="card-img-top" alt="Phòng VIP">
        <div class="card-body text-center">
          <h5 class="card-title">Phòng VIP</h5>
          <p>Giá: 1.200.000đ / đêm</p>
          <a href="dat-phong" class="btn btn-primary">Đặt ngay</a>
        </div>
      </div>
    </div>
  </div>
</div>

<jsp:include page="layout/footer.jsp"/>
</body>
</html>
