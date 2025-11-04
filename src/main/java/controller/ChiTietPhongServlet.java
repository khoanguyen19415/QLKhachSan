/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ChiTietPhong;
import model.ChiTietPhongDAO;
import model.Phong;
import model.PhongAnh;
import model.PhongAnhDAO;
import model.PhongDAO;

/**
 *
 * @author linhdhdi4
 */
@WebServlet(name = "ChiTietPhongServlet", urlPatterns = {"/chi-tiet-phong"})
public class ChiTietPhongServlet extends HttpServlet {

    private PhongDAO phongDAO;
    private PhongAnhDAO phongAnhDAO;
    private ChiTietPhongDAO chiTietDAO;

    @Override
    public void init() throws ServletException {
        phongDAO = new PhongDAO();
        phongAnhDAO = new PhongAnhDAO();
        chiTietDAO = new ChiTietPhongDAO();
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
            request.setAttribute("error", "Phòng không tồn tại");
            request.getRequestDispatcher("/phong").forward(request, response);
            return;
        }

        // Ảnh và tiện nghi
        List<PhongAnh> dsAnh = phongAnhDAO.getAnhTheoPhong(maPhong);
        List<ChiTietPhong> dsCT = chiTietDAO.getByPhong(maPhong);

        // Normalize image paths: nếu đường dẫn không bắt đầu bằng '/' hoặc 'http' thì thêm contextPath
        if (dsAnh != null) {
            for (PhongAnh a : dsAnh) {
                String src = a.getDuongDanAnh();
                if (src != null) {
                    src = src.trim();
                    if (!src.startsWith("/") && !src.startsWith("http")) {
                        src = request.getContextPath() + "/" + src;
                    }
                    a.setDuongDanAnh(src);
                }
            }
        }

        request.setAttribute("phong", p);
        request.setAttribute("dsAnh", dsAnh);
        request.setAttribute("dsChiTiet", dsCT);

        String trangThai = p.getTrangThai() == null ? "" : p.getTrangThai().trim().toLowerCase();
        boolean isAvailable = trangThai.contains("trống") || trangThai.contains("còn trống");
        request.setAttribute("isAvailable", isAvailable);
        request.getRequestDispatcher("/user/chitietphong.jsp").forward(request, response);
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

}
