<%@page import="java.util.Map"%>
<%@page import="ducpt.entity.Cart"%>
<%@page import="ducpt.entity.Course"%>
<%@page import="ducpt.registration.course.CourseDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Page</title>
        <style>
            .container {
                width: 80%;
                margin: 0 auto;
                padding: 20px;
            }
            .order-summary {
                border: 1px solid #ddd;
                padding: 20px;
                margin-bottom: 20px;
                border-radius: 4px;
            }
            .checkout-form {
                border: 1px solid #ddd;
                padding: 20px;
                border-radius: 4px;
            }
            .form-group {
                margin-bottom: 15px;
            }
            .form-group label {
                display: block;
                margin-bottom: 5px;
            }
            .form-group input {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            .submit-button {
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            .submit-button:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Checkout</h1>
            
            <div class="order-summary">
                <h2>Order Summary</h2>
                <%
                    Cart cart = (Cart) session.getAttribute("CART");
                    CourseDAO courseDAO = new CourseDAO();
                    if (cart != null && cart.getItems() != null) {
                        for (Map.Entry<String, Integer> item : cart.getItems().entrySet()) {
                            Course course = courseDAO.getCourseByName(item.getKey());
                            if (course != null) {
                %>
                <div>
                    <p><strong><%= course.getCourseName() %></strong> x <%= item.getValue() %></p>
                    <p>$<%= String.format("%.2f", course.getTuitionFee() * item.getValue()) %></p>
                </div>
                <%
                            }
                        }
                    }
                %>
                <h3>Total Amount: $<%= String.format("%.2f", request.getAttribute("totalAmount")) %></h3>
            </div>

            <div class="checkout-form">
                <h2>Payment Information</h2>
                <form action="MainController" method="POST">
                    <div class="form-group">
                        <label for="cardName">Name on Card</label>
                        <input type="text" id="cardName" name="cardName" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="cardNumber">Card Number</label>
                        <input type="text" id="cardNumber" name="cardNumber" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="expiryDate">Expiry Date</label>
                        <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="cvv">CVV</label>
                        <input type="text" id="cvv" name="cvv" required>
                    </div>
                    
                    <input type="submit" value="confirm_payment" name="btAction" class="submit-button">
                </form>
            </div>
        </div>
    </body>
</html> 