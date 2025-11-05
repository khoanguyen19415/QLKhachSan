/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Phong;
import model.PhongAnh;
import model.PhongAnhDAO;
import model.PhongDAO;

/**
 *
 * @author linhdhdi4
 */
@WebServlet(name = "PhongServlet", urlPatterns = {"/phong"})
public class PhongServlet extends HttpServlet {

    private PhongDAO phongDAO;
    private PhongAnhDAO phongAnhDAO;
    
    @Override
    public void init() throws ServletException {
        phongDAO = new PhongDAO();
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
        
        List<Phong> dsPhong = phongDAO.getAllPhong();

        Map<Integer, String> firstImages = new HashMap<>();
        if (dsPhong != null) {
            for (Phong p : dsPhong) {
                try {
                    int id = p.getMaPhong();
                    List<PhongAnh> imgs = phongAnhDAO.getAnhTheoPhong(id);
                    if (imgs != null && !imgs.isEmpty()) {
                        String src = imgs.get(0).getDuongDanAnh();
                        if (src != null) {
                            src = src.trim();
                            if (!src.startsWith("/") && !src.startsWith("http")) {
                                src = request.getContextPath() + "/" + src;
                            }
                        }
                        firstImages.put(id, src);
                    } else {
                        firstImages.put(id, null);
                    }
                } catch (Exception ex) {
                    firstImages.put(p.getMaPhong(), null);
                }
            }
        }

        request.setAttribute("dsPhong", dsPhong);
        request.setAttribute("firstImages", firstImages);

        request.getRequestDispatcher("/user/phong.jsp").forward(request, response);
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
