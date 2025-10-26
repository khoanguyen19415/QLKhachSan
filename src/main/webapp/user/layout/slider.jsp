<%@ page contentType="text/html;charset=UTF-8" %>
<!-- ==================== HERO SLIDER ==================== -->
<div id="heroCarousel" class="carousel slide hero-carousel" data-bs-ride="carousel" data-bs-interval="4000">
  <!-- Indicators -->
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
    <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
    <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
  </div>

  <!-- Slides -->
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="${pageContext.request.contextPath}/img/luxury-room4.jpg" class="d-block w-100" alt="Phòng 1">
      <div class="carousel-caption d-none d-md-block">
        <h1>Chào mừng đến với Khách sạn Sunshine</h1>
        <p>Trải nghiệm không gian nghỉ dưỡng sang trọng và tiện nghi bậc nhất.</p>
        <a href="phong" class="btn btn-primary">Xem phòng ngay</a>
      </div>
    </div>

    <div class="carousel-item">
      <img src="${pageContext.request.contextPath}/img/luxury-room3.jpg" class="d-block w-100" alt="Phòng 2">
      <div class="carousel-caption d-none d-md-block">
        <h1>Dịch vụ cao cấp – Phục vụ tận tâm</h1>
        <p>Đội ngũ nhân viên thân thiện, chuyên nghiệp luôn sẵn sàng hỗ trợ bạn.</p>
        <a href="phong" class="btn btn-primary">Xem phòng ngay</a>
      </div>
    </div>

    <div class="carousel-item">
      <img src="${pageContext.request.contextPath}/img/luxury-room4.jpg    " class="d-block w-100" alt="Phòng 3">
      <div class="carousel-caption d-none d-md-block">
        <h1>Không gian lý tưởng cho mọi chuyến đi</h1>
        <p>Tận hưởng kỳ nghỉ thoải mái, tiện nghi và đẳng cấp.</p>
        <a href="phong" class="btn btn-primary">Xem phòng ngay</a>
      </div>
    </div>
  </div>

  <!-- Controls -->
  <button class="carousel-control-prev" type="button" data-bs-target="#heroCarousel" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Trước</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#heroCarousel" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Sau</span>
  </button>
</div>



