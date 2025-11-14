/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.KhachHang;
import model.KhachHangDAO;
import model.TaiKhoan;
import model.TaiKhoanDAO;

/**
 *
 * @author linhdhdi4
 */
@WebServlet(name = "TaiKhoanServlet", urlPatterns = {"/TaiKhoanServlet"})
public class TaiKhoanServlet extends HttpServlet {

    private TaiKhoanDAO tkDAO;
    private KhachHangDAO khDAO;

    @Override
    public void init() throws ServletException {
        tkDAO = new TaiKhoanDAO();
        khDAO = new KhachHangDAO();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            String path = request.getServletPath();
            if ("/dang-nhap".equals(path)) {
                action = "showLogin";
            } else if ("/dang-ky".equals(path)) {
                action = "showRegister";
            } else if ("/dang-xuat".equals(path)) {
                action = "logout";
            } else {
                action = "showLogin";
            }
        }

        switch (action) {
            case "showLogin":
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                break;

            case "login":
                XuLyDangNhap(request, response);
                break;

            case "logout":
                HttpSession s = request.getSession(false);
                if (s != null) {
                    s.invalidate();
                }
                response.sendRedirect(request.getContextPath() + "/trang-chu");
                break;

            case "showRegister":
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                break;

            case "register":
                XuLyDangKy(request, response);
                break;

            default:
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void XuLyDangNhap(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username") != null ? request.getParameter("username").trim() : "";
        String password = request.getParameter("password") != null ? request.getParameter("password").trim() : "";

        if (username.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập username và mật khẩu.");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            return;
        }

        try {
            TaiKhoan tk = tkDAO.findByUsername(username);
            if (tk == null || !password.equals(tk.getMatKhau())) {
                request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("tk", tk);

            if ("KhachHang".equalsIgnoreCase(tk.getChucVu())) {
                KhachHang kh = khDAO.getByMaTK(tk.getMaTK());
                if (kh != null) {
                    session.setAttribute("kh", kh);
                }
            }

            if ("Admin".equalsIgnoreCase(tk.getChucVu())) {
                response.sendRedirect(request.getContextPath() + "/quan-tri");
            } else {
                response.sendRedirect(request.getContextPath() + "/trang-chu");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Lỗi khi đăng nhập: " + ex.getMessage());
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        }
    }

    private void XuLyDangKy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username") != null ? request.getParameter("username").trim() : "";
        String password = request.getParameter("password") != null ? request.getParameter("password").trim() : "";
        String password2 = request.getParameter("password2") != null ? request.getParameter("password2").trim() : "";

        String hoTen = request.getParameter("hoTen") != null ? request.getParameter("hoTen").trim() : "";
        String soDienThoai = request.getParameter("soDienThoai") != null ? request.getParameter("soDienThoai").trim() : "";
        String email = request.getParameter("email") != null ? request.getParameter("email").trim() : "";
        String diaChi = request.getParameter("diaChi") != null ? request.getParameter("diaChi").trim() : "";

        if (username.isEmpty() || password.isEmpty() || hoTen.isEmpty()) {
            request.setAttribute("error", "Username, mật khẩu và họ tên là bắt buộc.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }
        if (!password.equals(password2)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        try {
            if (tkDAO.findByUsername(username) != null) {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại, chọn tên khác.");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                return;
            }

            TaiKhoan tk = new TaiKhoan();
            tk.setTenDangNhap(username);
            tk.setMatKhau(password);
            tk.setChucVu("KhachHang");

            int newMaTK = tkDAO.insertAndGetId(tk); 
            if (newMaTK <= 0) {
                request.setAttribute("error", "Đăng ký thất bại (không thể tạo tài khoản).");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                return;
            }

            KhachHang kh = new KhachHang();
            kh.setHoTen(hoTen);
            kh.setSoDienThoai(soDienThoai);
            kh.setEmail(email);
            kh.setDiaChi(diaChi);

            boolean ok = khDAO.insertKhachHangWithMaTK(kh, newMaTK); 
            if (!ok) {
                tkDAO.deleteById(newMaTK); 
                request.setAttribute("error", "Đăng ký thất bại khi lưu thông tin khách hàng.");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                return;
            }

            request.setAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Lỗi khi đăng ký: " + ex.getMessage());
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
        }
    }

}
