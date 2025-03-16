/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducpt.controller;

import ducpt.log.Logger;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LENOVO-DUCKY
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController", "/search"})
public class MainController extends HttpServlet {
    private final String INVALIDPAGE = "invalid.html";
    private final String LOGINCONTROLLER = "LoginController";
    private final String SEARCHCONTROLLER = "SearchController";
    private final String UPDATECONTROLLER = "UpdateController";
    private final String DELETECONTROLLER = "DeleteController";
    private final String ADDTOCARTCONTROLLER = "AddToCartController";
    private final String VIEWCARTPAGE = "viewCart.jsp";
    private final String REMOVEITEMCONTROLLER = "RemoveItemController";
    private final String CREATECOURSECONTROLLER = "CreateCourseController";
    private final String NULLCONTROLLER = "NullController";
    private final String LOGOUTCONTROLLER = "LogoutController";
    private final String CREATECOURSE = "createCourse.jsp";
    private final String STORECONTROLLER = "StoreController";
    private final String ADDITEMCONTROLLER = "AddItemController";
    private final String CHECKOUTCONTROLLER = "CheckoutController";
    private final String CONFIRMORDERCONTROLLER = "ConfirmOrderController";

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
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("btAction");
            System.out.println("\n=== MainController Processing ===");
            System.out.println("Action received: " + action);
            String url = SEARCHCONTROLLER;
            
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("USERNAME");
            System.out.println("Current username: " + username);
            
            if (action != null) {
                if (action.equals("login")){
                    url = LOGINCONTROLLER;
                } else if (action.equalsIgnoreCase("search")){
                    url = SEARCHCONTROLLER;
                } else if (action.equalsIgnoreCase("update")) {
                    url = UPDATECONTROLLER;
                    Logger.logAction("UPDATE", username, "Updated record: " + request.getParameter("id"));
                } else if (action.equalsIgnoreCase("del")) {
                    url = DELETECONTROLLER;
                    Logger.logAction("DELETE", username, "Deleted record: " + request.getParameter("id"));
                } else if (action.equalsIgnoreCase("add_item_to_cart")){
                    url = ADDTOCARTCONTROLLER;
                } else if (action.equalsIgnoreCase("view_cart")){
                    url = VIEWCARTPAGE;
                } else if (action.equalsIgnoreCase("remove_item_from_cart")){
                    url = REMOVEITEMCONTROLLER;
                } else if (action.equalsIgnoreCase("create_course")){
                    url = CREATECOURSECONTROLLER;
                    System.out.println("Forwarding to CreateCourseController");
                } else if (action.equalsIgnoreCase("logout")) {
                    url = LOGOUTCONTROLLER;
                } else if (action.equalsIgnoreCase("Create Course")) {
                    url = CREATECOURSE;
                } else if (action.equalsIgnoreCase("store")) {
                    url = STORECONTROLLER;
                } else if (action.equalsIgnoreCase("add_item_to_cart")) {
                    url = ADDITEMCONTROLLER;
                } else if (action.equalsIgnoreCase("checkout")) {
                    url = CHECKOUTCONTROLLER;
                } else if (action.equalsIgnoreCase("confirm_payment")) {
                    url = CONFIRMORDERCONTROLLER;
                } else if (action.equalsIgnoreCase("update_quantity")) {
                    url = "UpdateQuantityController";
                }
            }
            System.out.println("Final URL for forwarding: " + url);
            request.getRequestDispatcher(url).forward(request, response);
            System.out.println("Forward completed");
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
    }

    @Override
    public void init() throws ServletException {
        super.init();
        Logger.setServletContext(getServletContext());
    }
    // </editor-fold>

}
