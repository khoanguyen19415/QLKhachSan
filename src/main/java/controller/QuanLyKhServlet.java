/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.javadoc.doclet.Reporter;
import model.KhachHang;
import model.KhachHangDAO;
import model.TaiKhoanDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "QuanLyKhServlet", urlPatterns = {"/QL-Khachhang"})
public class QuanLyKhServlet extends HttpServlet {

    private KhachHangDAO khDAO;

    @Override
    public void init() throws ServletException {
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        {
            switch (action) {
                case "list":
                    var dsKH = khDAO.getAll();
                    request.setAttribute("dsKH", dsKH);
                    request.getRequestDispatcher("/admin/quanlykhachhang.jsp").forward(request, response);
                    break;
                case "add":
                    XuLyThem(request, response);
                    break;
                case "search":
                    String keyword = request.getParameter("hoTen");
                    var dskq = khDAO.searchKhachHang(keyword);
                    request.setAttribute("dsKH", dskq);
                    request.getRequestDispatcher("/admin/quanlykhachhang.jsp").forward(request, response);
                    break;
                case "delete":
                    XuLyDelete(request, response);
                    break;
                case "update": // mới: xử lý AJAX cập nhật khách hàng
                    XuLySua(request, response);
                    break;
            }
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

    private void XuLyThem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String hoTen = request.getParameter("hoTen");
            String soDienThoai = request.getParameter("soDienThoai");
            String email = request.getParameter("email");
            String diaChi = request.getParameter("diaChi");

            KhachHang kh = new KhachHang(0, hoTen, soDienThoai, email, diaChi);

            khDAO.insertKhachHang(kh);

            request.setAttribute("success", "Thêm khách hàng thành công");

            request.getRequestDispatcher("/QL-Khachhang?action=list").forward(request, response);

        } catch (Exception ex) {
            try {
                System.out.println("Loi:" + ex.toString());
                request.setAttribute("error", "Thêm khách hàng thất bái");
                request.getRequestDispatcher("/QL-Khachhang?action=list").forward(request, response);
            } catch (Exception ex1) {
            }
        }
    }

    private void XuLySua(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try (PrintWriter out = response.getWriter()) {
            String idStr = request.getParameter("maKH");
            if (idStr == null || idStr.trim().isEmpty()) {
                out.print("{\"status\":\"error\",\"message\":\"Thiếu mã khách hàng\"}");
                return;
            }
            int maKH = Integer.parseInt(idStr);
            String hoTen = request.getParameter("hoTen");
            String soDienThoai = request.getParameter("soDienThoai");
            String email = request.getParameter("email");
            String diaChi = request.getParameter("diaChi");

            KhachHang kh = new KhachHang(maKH, hoTen, soDienThoai, email, diaChi);

            boolean ok = khDAO.updateKhachHang(kh);
            if (ok) {
                out.print("{\"status\":\"success\",\"message\":\"Cập nhật khách hàng thành công\"}");
            } else {
                out.print("{\"status\":\"error\",\"message\":\"Cập nhật thất bại\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"status\":\"error\",\"message\":\"Lỗi khi cập nhật khách hàng\"}");
        }
    }

    private void XuLyDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String makhachhang_tr = request.getParameter("id");
        try {
            int idKH = Integer.parseInt(makhachhang_tr);
            KhachHang kh = khDAO.getById(idKH);
            if (kh == null) {
                request.setAttribute("error", "Không tìm thấy khách hàng");
            } else {
                Integer maTK = kh.getMaTK();
                boolean okDelKH = khDAO.deleteKhachHang(idKH);
                if (okDelKH) {
                    if (maTK != null) {
                        new TaiKhoanDAO().deleteById(maTK);
                    }
                    request.setAttribute("success", "Xóa khách hàng (và tài khoản nếu có) thành công");
                } else {
                    request.setAttribute("error", "Xóa khách hàng thất bại");
                }
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Mã không hợp lệ");
        }
        request.getRequestDispatcher("/QL-Khachhang?action=list").forward(request, response);
    }

}
