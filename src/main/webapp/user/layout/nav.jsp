<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/trang-chu">
            Sunshine Hotel
        </a>

        <div>
            <!-- Luôn hiển thị -->
            <a href="${pageContext.request.contextPath}/trang-chu" 
               class="nav-link text-white d-inline-block me-3">Trang chủ</a>

            <a href="${pageContext.request.contextPath}/phong" 
               class="nav-link text-white d-inline-block me-3">Đặt phòng</a>

            <!-- Chỉ Admin mới thấy -->
            <c:if test="${sessionScope.tk != null && sessionScope.tk.chucVu == 'Admin'}">
                <a href="${pageContext.request.contextPath}/quan-tri" 
                   class="nav-link text-white d-inline-block me-3">Quản trị</a>
            </c:if>

            <!-- Chỉ người đã đăng nhập mới thấy “Lịch sử” -->
            <c:if test="${sessionScope.tk != null}">
                <a href="${pageContext.request.contextPath}/lich-su" 
                   class="nav-link text-white d-inline-block me-3">Lịch sử</a>
            </c:if>

            <!-- Nếu chưa đăng nhập -->
            <c:if test="${sessionScope.tk == null}">
                <a href="${pageContext.request.contextPath}/TaiKhoanServlet?action=showLogin" 
                   class="nav-link text-white d-inline-block me-3">Đăng nhập</a>
                <a href="${pageContext.request.contextPath}/TaiKhoanServlet?action=showRegister" 
                   class="nav-link text-white d-inline-block me-3">Đăng ký</a>
            </c:if>

            <!-- Nếu đã đăng nhập -->
            <c:if test="${sessionScope.tk != null}">
                <span class="nav-link text-white d-inline-block me-3">
                    Xin chào, <strong>${sessionScope.tk.tenDangNhap}</strong>
                </span>
                <a href="${pageContext.request.contextPath}/TaiKhoanServlet?action=logout" 
                   class="nav-link text-white d-inline-block me-3">Đăng xuất</a>
            </c:if>
        </div>
    </div>
</nav>
