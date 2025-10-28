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
import model.PhongAnhDAO;
import model.PhongAnh;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "QuanLyChiTietPhongServlet", urlPatterns = {"/QL-CTPhong"})
public class QuanLyChiTietPhongServlet extends HttpServlet {

    private ChiTietPhongDAO ctDAO;
    private PhongAnhDAO anhDAO;

    @Override
    public void init() throws ServletException {
        ctDAO = new ChiTietPhongDAO();
        anhDAO = new PhongAnhDAO();
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

        switch (action) {
            case "view":
                xemChiTietPhong(request, response);
                break;
            default:
                response.sendRedirect("QL-Phong");
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

    private void xemChiTietPhong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int maPhong = Integer.parseInt(request.getParameter("id"));

        // Lấy danh sách tiện nghi của phòng
        List<ChiTietPhong> dsChiTiet = ctDAO.getByPhong(maPhong);

        // Lấy danh sách ảnh của phòng
        List<PhongAnh> dsAnh = anhDAO.getAnhTheoPhong(maPhong);

        // Gửi sang JSP
        request.setAttribute("dsChiTiet", dsChiTiet);
        request.setAttribute("dsAnh", dsAnh);
        request.setAttribute("maPhong", maPhong);

        request.getRequestDispatcher("/admin/quanlychitietphong.jsp").forward(request, response);

    }

}
