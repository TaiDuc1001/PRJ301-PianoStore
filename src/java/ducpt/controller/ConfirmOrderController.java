package ducpt.controller;

import ducpt.registration.course.CourseDAO;
import ducpt.entity.Cart;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ConfirmOrderController", urlPatterns = {"/ConfirmOrderController"})
public class ConfirmOrderController extends HttpServlet {
    private final String SUCCESS_PAGE = "orderSuccess.jsp";
    private final String ERROR_PAGE = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR_PAGE;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null && cart.getItems() != null) {
                    CourseDAO courseDAO = new CourseDAO();
                    
                    // Process payment (in a real application, you'd integrate with a payment gateway)
                    boolean paymentSuccess = true; // Simulate payment success
                    
                    if (paymentSuccess) {
                        // Clear the cart after successful purchase
                        session.removeAttribute("CART");
                        url = SUCCESS_PAGE;
                    } else {
                        request.setAttribute("errorMessage", "Payment failed!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during checkout!");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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