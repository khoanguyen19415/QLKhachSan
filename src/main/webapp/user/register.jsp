<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đăng ký - Sunshine Hotel</title>
  <%@ include file="layout/header.jsp" %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
  <%@ include file="../thongbao.jsp" %>
  <%@ include file="layout/nav.jsp" %>

  <div class="container py-5">
    <div class="row justify-content-center">
      <div class="col-md-7 col-lg-6">
        <div class="card shadow-sm">
          <div class="card-body">
            <h4 class="card-title text-center mb-3">Tạo tài khoản</h4>

            <c:if test="${not empty error}">
              <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
              <div class="alert alert-success">${success}</div>
            </c:if>

            <!-- Gửi về TaiKhoanServlet -->
            <form id="registerForm" action="${pageContext.request.contextPath}/TaiKhoanServlet" method="post" novalidate>
              <input type="hidden" name="action" value="register" />

              <div class="mb-3">
                <label class="form-label">Tên tài khoản (username)</label>
                <input name="username" type="text" class="form-control" required>
              </div>

              <div class="mb-3">
                <label class="form-label">Họ và tên</label>
                <input name="hoTen" type="text" class="form-control" required>
              </div>

              <div class="mb-3">
                <label class="form-label">Số điện thoại</label>
                <input name="soDienThoai" type="tel" class="form-control">
              </div>

              <div class="mb-3">
                <label class="form-label">Email</label>
                <input name="email" type="email" class="form-control">
              </div>

              <div class="mb-3">
                <label class="form-label">Địa chỉ</label>
                <input name="diaChi" type="text" class="form-control">
              </div>

              <div class="mb-3">
                <label class="form-label">Mật khẩu</label>
                <input id="pwd" name="password" type="password" class="form-control" required>
              </div>

              <div class="mb-3">
                <label class="form-label">Xác nhận mật khẩu</label>
                <input id="pwd2" name="password2" type="password" class="form-control" required>
              </div>

              <div id="pwError" class="text-danger small mb-2" style="display:none;">Mật khẩu không khớp.</div>

              <div class="d-grid">
                <button id="btnRegister" type="submit" class="btn btn-success">Đăng ký</button>
              </div>
            </form>

            <hr/>
            <div class="text-center small">
              Đã có tài khoản? 
              <a href="${pageContext.request.contextPath}/TaiKhoanServlet?action=showLogin">Đăng nhập</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <%@ include file="layout/footer.jsp" %>

  <script>
    document.getElementById('registerForm').addEventListener('submit', function (e) {
      var p1 = document.getElementById('pwd').value;
      var p2 = document.getElementById('pwd2').value;
      if (p1 !== p2) {
        e.preventDefault();
        document.getElementById('pwError').style.display = 'block';
      } else {
        document.getElementById('pwError').style.display = 'none';
      }
    });
  </script>
</body>
</html>
