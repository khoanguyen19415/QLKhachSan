<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <title>Sunshine Hotel - Trang chủ</title>
  <jsp:include page="layout/header.jsp"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
</head>
<body>

<!-- Banner -->
<!-- Banner có slideshow -->
<jsp:include page="layout/nav.jsp"/>
<jsp:include page="layout/slider.jsp"/>

<!-- Giới thiệu dịch vụ -->
<div class="container py-5">
  <div class="row text-center g-4">

    <!-- Phòng sang trọng -->
    <div class="col-md-4">
      <div class="card border-0 shadow-sm p-3 h-100">
        <img src="${pageContext.request.contextPath}/img/luxury-room.jpg" class="rounded mb-3" alt="Phòng sang trọng">
        <h4 class="text-primary fw-bold">Phòng sang trọng</h4>
        <p>Phòng được thiết kế hiện đại, tiện nghi và sạch sẽ. Mang lại không gian thoải mái cho bạn.</p>
      </div>
    </div>

    <!-- Dịch vụ 24/7 -->
    <div class="col-md-4">
      <div class="card border-0 shadow-sm p-3 h-100">
        <img src="${pageContext.request.contextPath}/img/service-247.jpg" class="rounded mb-3" alt="Dịch vụ 24/7">
        <h4 class="text-primary fw-bold">Dịch vụ 24/7</h4>
        <p>Đội ngũ nhân viên chuyên nghiệp, thân thiện, sẵn sàng phục vụ bạn mọi lúc.</p>
      </div>
    </div>

    <!-- Giá ưu đãi -->
    <div class="col-md-4">
      <div class="card border-0 shadow-sm p-3 h-100">
        <img src="${pageContext.request.contextPath}/img/best-price.jpg" class="rounded mb-3" alt="Giá ưu đãi">
        <h4 class="text-primary fw-bold">Giá ưu đãi</h4>
        <p>Giá phòng cạnh tranh, nhiều khuyến mãi hấp dẫn quanh năm cho khách hàng thân thiết.</p>
      </div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="layout/footer.jsp"/>
</body>
</html>
