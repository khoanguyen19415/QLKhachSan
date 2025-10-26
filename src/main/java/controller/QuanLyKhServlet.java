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
                    String makhachhang_tr = request.getParameter("id");
                    int id = Integer.parseInt(makhachhang_tr);

                    khDAO.deleteKhachHang(id);
                    request.setAttribute("success", "Xóa khách hàng thành công");
                    request.getRequestDispatcher("/QL-Khachhang?action=list").forward(request, response);
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

}
