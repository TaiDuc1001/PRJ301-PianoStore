/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducpt.controller;

import ducpt.registration.course.CourseDAO;
import ducpt.registration.course.CourseInsertError;
import ducpt.entity.Course;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO-DUCKY
 */
@WebServlet(name = "CreateCourseController", urlPatterns = {"/CreateCourseController"})
public class CreateCourseController extends HttpServlet {
    private final String SEARCHPAGE = "search.jsp";
    private final String CREATECOURSE = "createCourse.jsp";

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
        System.out.println("\n=== CreateCourseController Processing ===");
        try {
            String url = CREATECOURSE;
            String name = request.getParameter("txtName");
            String description = request.getParameter("txtDescription");
            String tuitionFeeStr = request.getParameter("txtTuitionFee");
            String startDate = request.getParameter("txtStartDate");
            String endDate = request.getParameter("txtEndDate");
            String category = request.getParameter("txtCategory");

            System.out.println("Received parameters:");
            System.out.println("Name: " + name);
            System.out.println("Description: " + description);
            System.out.println("Tuition Fee: " + tuitionFeeStr);
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("Category: " + category);

            boolean hasErrors = false;
            CourseInsertError errors = new CourseInsertError();
            CourseDAO dao = new CourseDAO();

            // Check for duplicate name
            if (name != null && !name.trim().isEmpty()) {
                if (dao.isDuplicateName(name.trim())) {
                    hasErrors = true;
                    errors.setDuplicateNameErr("Course name already exists");
                    System.out.println("Duplicate name validation failed");
                }
            }

            // Name validation
            if (name == null || name.trim().length() < 3 || name.trim().length() > 100) {
                hasErrors = true;
                errors.setNameLengthErr("Course name must be between 3 and 100 characters");
                System.out.println("Name validation failed");
            }

            // Tuition fee validation
            double tuitionFee = 0;
            try {
                tuitionFee = Double.parseDouble(tuitionFeeStr);
                if (tuitionFee < 0) {
                    hasErrors = true;
                    errors.setTuitionFeeErr("Tuition fee cannot be negative");
                    System.out.println("Tuition fee validation failed: negative value");
                }
            } catch (NumberFormatException e) {
                hasErrors = true;
                errors.setTuitionFeeErr("Invalid tuition fee format");
                System.out.println("Tuition fee validation failed: " + e.getMessage());
            }

            // Category validation
            if (category == null || 
                !(category.equals("Piano") || 
                  category.equals("Guitar") || 
                  category.equals("Drawing"))) {
                hasErrors = true;
                errors.setCategoryErr("Category must be Piano, Guitar, or Drawing");
                System.out.println("Category validation failed");
            }

            // Date validation
            try {
                java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
                java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);
                System.out.println("Date validation passed");
                System.out.println("SQL Start Date: " + sqlStartDate);
                System.out.println("SQL End Date: " + sqlEndDate);
            } catch (IllegalArgumentException e) {
                hasErrors = true;
                errors.setDateErr("Invalid date format. Use YYYY-MM-DD");
                System.out.println("Date validation failed: " + e.getMessage());
            }

            if (hasErrors) {
                System.out.println("Validation errors found, returning to create course page");
                request.setAttribute("INSERTERRORS", errors);
                url = CREATECOURSE;
            } else {
                System.out.println("Validation passed, attempting to add course");  
                System.out.println("\n=== Course Creation Process ===");
                java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
                java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);
                
                System.out.println("Creating course with values:");
                System.out.println("Name: " + name);
                System.out.println("Description: " + description);
                System.out.println("Tuition Fee: " + tuitionFee);
                System.out.println("Start Date: " + sqlStartDate);
                System.out.println("End Date: " + sqlEndDate);
                System.out.println("Category: " + category);
                
                Course course = new Course(name, description, tuitionFee, 
                                        startDate, endDate, category);
                boolean result = dao.addCourse(course);
                System.out.println("Course addition result: " + result);
                
                if (result) {
                    System.out.println("Course added successfully!");
                    url = "MainController?btAction=search";
                    System.out.println("Redirecting to search page: " + url);
                } else {
                    System.out.println("Failed to add course!");
                    errors.setNameLengthErr("Failed to create course. Please try again.");
                    request.setAttribute("INSERTERRORS", errors);
                    url = CREATECOURSE;
                }
            }
            
            System.out.println("Final URL for forwarding: " + url);
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            System.out.println("Forward completed");
            
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            ex.printStackTrace();
            Logger.getLogger(CreateCourseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
            ex.printStackTrace();
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
