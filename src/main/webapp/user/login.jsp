<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đăng nhập - Sunshine Hotel</title>
  <%@ include file="layout/header.jsp" %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
  <%@ include file="../thongbao.jsp" %>
  <%@ include file="layout/nav.jsp" %>

  <div class="container py-5">
    <div class="row justify-content-center">
      <div class="col-md-6 col-lg-5">
        <div class="card shadow-sm">
          <div class="card-body">
            <h4 class="card-title text-center mb-3">Đăng nhập</h4>

            <c:if test="${not empty error}">
              <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
              <div class="alert alert-success">${success}</div>
            </c:if>

            <!-- Chú ý: action trỏ về TaiKhoanServlet -->
            <form action="${pageContext.request.contextPath}/TaiKhoanServlet" method="post" novalidate>
              <input type="hidden" name="action" value="login" />
              <div class="mb-3">
                <label for="username" class="form-label">Tên đăng nhập</label>
                <input id="username" name="username" type="text" class="form-control" required autofocus>
              </div>

              <div class="mb-3">
                <label for="password" class="form-label">Mật khẩu</label>
                <input id="password" name="password" type="password" class="form-control" required>
              </div>

              <div class="d-grid">
                <button type="submit" class="btn btn-primary">Đăng nhập</button>
              </div>
            </form>

            <hr/>
            <div class="text-center small">
              Bạn chưa có tài khoản? 
              <a href="${pageContext.request.contextPath}/TaiKhoanServlet?action=showRegister">Đăng ký</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <%@ include file="layout/footer.jsp" %>
</body>
</html>
