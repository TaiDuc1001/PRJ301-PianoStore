package ducpt.controller;

import ducpt.registration.course.CourseDAO;
import ducpt.entity.Cart;
import ducpt.entity.Course;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CheckoutController", urlPatterns = {"/CheckoutController"})
public class CheckoutController extends HttpServlet {
    private final String CHECKOUT_PAGE = "checkout.jsp";
    private final String ERROR_PAGE = "error.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR_PAGE;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
                    CourseDAO courseDAO = new CourseDAO();
                    double totalAmount = 0;
                    
                    // Calculate total and validate stock
                    for (Map.Entry<String, Integer> item : cart.getItems().entrySet()) {
                        Course course = courseDAO.getCourseByName(item.getKey());
                        if (course != null) {
                            totalAmount += course.getTuitionFee() * item.getValue();
                        }
                    }
                    
                    request.setAttribute("totalAmount", totalAmount);
                    url = CHECKOUT_PAGE;
                } else {
                    request.setAttribute("errorMessage", "Your cart is empty!");
                    url = ERROR_PAGE;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error occurred!");
            url = ERROR_PAGE;
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