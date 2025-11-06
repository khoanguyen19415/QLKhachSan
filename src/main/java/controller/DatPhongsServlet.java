/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DatPhong;
import model.DatPhongDAO;
import model.KhachHang;
import model.Phong;
import model.PhongAnh;
import model.PhongAnhDAO;
import model.PhongDAO;

/**
 *
 * @author linhdhdi4
 */
@WebServlet(name = "DatPhongsServlet", urlPatterns = {"/dat-phong"})
public class DatPhongsServlet extends HttpServlet {

    private PhongDAO phongDAO;
    private DatPhongDAO dpDAO;
    private PhongAnhDAO phongAnhDAO;

    @Override
    public void init() throws ServletException {
        phongDAO = new PhongDAO();
        dpDAO = new DatPhongDAO();
        phongAnhDAO = new PhongAnhDAO();
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
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }
        if (action.equals("view")) {
            showForm(request, response);
        } else if (action.equals("book")) {
            doBook(request, response);
        } else {
            showForm(request, response);
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

    private void showForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String maPhongStr = request.getParameter("maPhong");
        if (maPhongStr == null || maPhongStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/phong");
            return;
        }

        int maPhong;
        try {
            maPhong = Integer.parseInt(maPhongStr);
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/phong");
            return;
        }

        Phong p = phongDAO.getById(maPhong);
        if (p == null) {
            response.sendRedirect(request.getContextPath() + "/phong");
            return;
        }

        System.out.println("===> showForm: Phòng " + maPhong + " trạng thái hiện tại là: " + p.getTrangThai());

        // Lấy ảnh
        List<PhongAnh> dsAnh = phongAnhDAO.getAnhTheoPhong(maPhong);
        String firstImg = null;
        if (dsAnh != null && !dsAnh.isEmpty()) {
            firstImg = dsAnh.get(0).getDuongDanAnh();
            if (firstImg != null) {
                firstImg = firstImg.trim();
                if (!firstImg.startsWith("/") && !firstImg.startsWith("http")) {
                    firstImg = request.getContextPath() + "/" + firstImg;
                }
            }
        }

        Map<Integer, String> firstImages = new HashMap<>();
        firstImages.put(maPhong, firstImg);

        String trangThai = p.getTrangThai() == null ? "" : p.getTrangThai().trim().toLowerCase();
        boolean isAvailable = trangThai.contains("trống") || trangThai.contains("còn trống");

        request.setAttribute("phong", p);
        request.setAttribute("isAvailable", isAvailable);
        request.setAttribute("firstImages", firstImages);

        request.getRequestDispatcher("/user/datphong.jsp").forward(request, response);
    }

    private void doBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            HttpSession session = request.getSession(false);
            KhachHang kh = (session != null) ? (KhachHang) session.getAttribute("kh") : null;

            if (kh == null) {
                response.sendRedirect(request.getContextPath() + "/TaiKhoanServlet?action=showLogin");
                return;
            }

            String maPhongStr = request.getParameter("maPhong");
            String ngayNhanStr = request.getParameter("ngayNhan");
            String ngayTraStr = request.getParameter("ngayTra");

            if (maPhongStr == null || ngayNhanStr == null || ngayTraStr == null) {
                request.setAttribute("error", "Dữ liệu đặt phòng thiếu");
                showForm(request, response);
                return;
            }

            int maPhong = Integer.parseInt(maPhongStr);
            int maKH = kh.getMaKH();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date utilNgayNhan = sdf.parse(ngayNhanStr);
            Date utilNgayTra = sdf.parse(ngayTraStr);

            Phong p = phongDAO.getById(maPhong);
            if (p == null) {
                request.setAttribute("error", "Không tìm thấy phòng");
                showForm(request, response);
                return;
            }

            // Kiểm tra phòng có sẵn
            if (!"Trống".equalsIgnoreCase(p.getTrangThai()) && !"Còn trống".equalsIgnoreCase(p.getTrangThai())) {
                request.setAttribute("error", "Phòng hiện không còn trống, không thể đặt");
                request.setAttribute("phong", p);
                request.setAttribute("isAvailable", false);
                request.getRequestDispatcher("/user/datphong.jsp").forward(request, response);
                return;
            }

            // ✅ Tạo đơn đặt phòng
            DatPhong dp = new DatPhong();
            dp.setMaKH(maKH);
            dp.setMaPhong(maPhong);
            dp.setTenPhong(p.getTenPhong()); // ✅ Lưu tên phòng vào DatPhong
            dp.setNgayDat(new Date());
            dp.setNgayNhan(new java.sql.Date(utilNgayNhan.getTime()));
            dp.setNgayTra(new java.sql.Date(utilNgayTra.getTime()));
            dp.setTrangThai("Chờ xác nhận");

            boolean okInsert = dpDAO.insert(dp);

            if (okInsert) {
                phongDAO.updateStatus(maPhong, "Đã đặt");
                request.setAttribute("success", "Đặt phòng thành công! Đơn của bạn đang chờ xác nhận.");
            } else {
                request.setAttribute("error", "Đặt phòng thất bại, vui lòng thử lại sau.");
            }
            request.setAttribute("ngayNhan", ngayNhanStr);
            request.setAttribute("ngayTra", ngayTraStr);

            showForm(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Lỗi khi đặt phòng: " + ex.getMessage());
            showForm(request, response);
        }
    }

    private String normalizeStatus(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.trim().toLowerCase();
    }

}
