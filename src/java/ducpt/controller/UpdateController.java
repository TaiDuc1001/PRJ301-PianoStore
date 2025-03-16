/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducpt.controller;

import ducpt.registration.course.CourseDAO;
import ducpt.registration.user.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO-DUCKY
 */
@WebServlet(name = "UpdateController", urlPatterns = {"/UpdateController"})
public class UpdateController extends HttpServlet {
    private final String UPDATEERRORPAGE = "updateErr.html";
    private final String LOGIN_PAGE = "login.jsp";
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
        String url = LOGIN_PAGE;
        
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
                Logger.getLogger(UpdateController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        url = UPDATEERRORPAGE;
        try {
            String name = request.getParameter("txtName");
            String description = request.getParameter("txtDescription");
            double tuitionFee = Double.parseDouble(request.getParameter("txtTuitionFee"));
            String startDate = request.getParameter("txtStartDate");
            String endDate = request.getParameter("txtEndDate");
            String category = request.getParameter("txtCategory");
            String searchValue = request.getParameter("lastSearchValue");
            
            System.out.println("\nUpdate Parameters:");
            System.out.println("Name: " + name);
            System.out.println("Description: " + description);
            System.out.println("Tuition Fee: " + tuitionFee);
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("Category: '" + category + "'");
            System.out.println("Search Value: " + searchValue);
            
            // Validate category
            if (!category.equals("Piano") && !category.equals("Guitar") && !category.equals("Drawing")) {
                System.out.println("Category validation failed!");
                throw new IllegalArgumentException("Invalid category. Must be exactly 'Piano', 'Guitar', or 'Drawing'");
            }
            System.out.println("Category validation passed");
            
            // Validate dates
            try {
                java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
                java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);
                System.out.println("Date validation passed");
                System.out.println("SQL Start Date: " + sqlStartDate);
                System.out.println("SQL End Date: " + sqlEndDate);
            } catch (IllegalArgumentException e) {
                System.out.println("Date validation failed: " + e.getMessage());
                throw e;
            }
            
            CourseDAO dao = new CourseDAO();
            System.out.println("Attempting database update...");
            boolean result = dao.updateCourse(name, description, tuitionFee, 
                                           startDate, endDate, category);
            
            System.out.println("Update result: " + result);
            
            if (result) {
                url = "MainController?btAction=search&txtSearchValue=" + searchValue;
                System.out.println("Redirecting to: " + url);
            } else {
                System.out.println("Update failed, redirecting to error page");
            }
            
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            System.out.println("Number Format Error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            System.out.println("Validation Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            System.out.println("Final redirect to: " + url);
            response.sendRedirect(url);
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
