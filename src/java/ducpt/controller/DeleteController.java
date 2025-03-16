/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducpt.controller;

import ducpt.registration.RegistrationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.util.logging.Level;
import java.util.logging.Logger;
import ducpt.registration.user.UserDAO;

/**
 *
 * @author LENOVO-DUCKY
 */
@WebServlet(name = "DeleteController", urlPatterns = {"/DeleteController"})
public class DeleteController extends HttpServlet {
    private final String DELETEERRPAGE = "delerr.html";
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
        String url = "login.jsp";
        
        Cookie[] cookies = request.getCookies();
        boolean isAdmin = false;
        String username = null;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        
        if (username != null) {
            try {
                UserDAO userDAO = new UserDAO();
                isAdmin = userDAO.isAdmin(username);
            } catch (SQLException ex) {
                Logger.getLogger(DeleteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (!isAdmin) {
            String fullRequestURL = request.getRequestURL().toString();
            if (request.getQueryString() != null) {
                fullRequestURL += "?" + request.getQueryString();
            }
            request.getSession().setAttribute("returnUrl", fullRequestURL);
            response.sendRedirect(url);
            return;
        }
        
        url = DELETEERRPAGE;
        try (PrintWriter out = response.getWriter()) {
            String pk = request.getParameter("pk");
            String searchValue = request.getParameter("lastSearchValue");
            try {
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.deleteRecord(pk);
                if (result) {
                    url = "MainController?btAction=search&txtSearchValue=" + searchValue;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                response.sendRedirect(url);
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

}
