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
import model.PhongDAO;
import model.Phong;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "QuanLyPhongServlet", urlPatterns = {"/QL-Phong"})
public class QuanLyPhongServlet extends HttpServlet {

    private PhongDAO phongDAO;

    @Override
    public void init() throws ServletException {
        phongDAO = new PhongDAO();
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

        switch (action) {
            case "list":
                var dsPhong = phongDAO.getAllPhong();
                request.setAttribute("dsPhong", dsPhong);
                request.getRequestDispatcher("/admin/quanlyphong.jsp").forward(request, response);
                break;
            case "add":
                XuLyThem(request, response);
                break;
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                phongDAO.delete(id);
                request.setAttribute("success", "Xóa Phòng thành công");
                request.getRequestDispatcher("/QL-Phong?action=list").forward(request, response);
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

    private void XuLyThem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String tenPhong = request.getParameter("tenPhong");
        String loaiPhong = request.getParameter("loaiPhong");
        double gia = Double.parseDouble(request.getParameter("gia"));
        String moTa = request.getParameter("moTa"); 
        String trangThai = request.getParameter("trangThai");

            Phong p = new Phong(0, tenPhong, loaiPhong, gia, moTa, trangThai);
            phongDAO.insert(p);

            request.setAttribute("success", "Thêm phòng thành công");
        } catch (Exception e) {
            request.setAttribute("error", "Thêm phòng thất bại");
        }
        request.getRequestDispatcher("/QL-Phong?action=list").forward(request, response);
    }

}
