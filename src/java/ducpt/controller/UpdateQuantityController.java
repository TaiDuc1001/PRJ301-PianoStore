package ducpt.controller;

import ducpt.entity.Cart;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UpdateQuantityController", urlPatterns = {"/UpdateQuantityController"})
public class UpdateQuantityController extends HttpServlet {
    private final String VIEW_CART_PAGE = "MainController?btAction=view_cart";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null && cart.getItems() != null) {
                    Map<String, Integer> items = cart.getItems();
                    
                    // Get all parameters that start with "quantity_"
                    Enumeration<String> paramNames = request.getParameterNames();
                    while (paramNames.hasMoreElements()) {
                        String paramName = paramNames.nextElement();
                        if (paramName.startsWith("quantity_")) {
                            String courseName = paramName.substring("quantity_".length());
                            String quantityStr = request.getParameter(paramName);
                            
                            try {
                                int quantity = Integer.parseInt(quantityStr);
                                if (quantity > 0 && items.containsKey(courseName)) {
                                    items.put(courseName, quantity);
                                }
                            } catch (NumberFormatException e) {
                                // Skip invalid numbers
                                continue;
                            }
                        }
                    }
                    
                    session.setAttribute("CART", cart);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log the error but continue to redirect
        } finally {
            response.sendRedirect(VIEW_CART_PAGE);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
} 