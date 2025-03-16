<%-- 
    Document   : viewCart
    Created on : Feb 28, 2025, 10:29:29 AM
    Author     : LENOVO-DUCKY
--%>

<%@page import="java.util.Map"%>
<%@page import="ducpt.entity.Cart"%>
<%@page import="ducpt.entity.Course"%>
<%@page import="ducpt.registration.course.CourseDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
        <style>
            .cart-table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }
            .cart-table th, .cart-table td {
                padding: 10px;
                border: 1px solid #ddd;
            }
            .cart-table th {
                background-color: #f5f5f5;
            }
            .total-row {
                font-weight: bold;
                background-color: #f9f9f9;
            }
            .action-buttons {
                margin-top: 20px;
            }
            .action-buttons input[type="submit"], .action-buttons a {
                padding: 8px 15px;
                margin-right: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            .quantity-input {
                width: 60px;
                padding: 5px;
            }
        </style>
        <script>
            function confirmRemove() {
                return confirm("Are you sure you want to remove the selected items from your cart?");
            }
            
            function updateQuantity(input, originalValue) {
                const newValue = parseInt(input.value);
                if (isNaN(newValue) || newValue < 1) {
                    alert("Please enter a valid quantity (minimum 1)");
                    input.value = originalValue;
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <h1>Your Cart Items</h1>
        <%
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
                    CourseDAO courseDAO = new CourseDAO();
                    double totalAmount = 0;
        %>
        <form action="MainController" method="POST" onsubmit="return confirmRemove();">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Course Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        Map<String, Integer> items = cart.getItems();
                        int count = 0;
                        for (Map.Entry<String, Integer> item : items.entrySet()) {
                            Course course = courseDAO.getCourseByName(item.getKey());
                            if (course != null) {
                                double itemTotal = course.getTuitionFee() * item.getValue();
                                totalAmount += itemTotal;
                    %>
                    <tr>
                        <td><%= ++count %></td>
                        <td><%= course.getCourseName() %></td>
                        <td><%= course.getDescription() %></td>
                        <td>$<%= String.format("%.2f", course.getTuitionFee()) %></td>
                        <td>
                            <input type="number" 
                                   name="quantity_<%= course.getCourseName() %>" 
                                   value="<%= item.getValue() %>" 
                                   min="1" 
                                   class="quantity-input"
                                   onchange="updateQuantity(this, '<%= item.getValue() %>')"/>
                        </td>
                        <td>$<%= String.format("%.2f", itemTotal) %></td>
                        <td>
                            <input type="checkbox" name="chkItem" value="<%= item.getKey() %>" />
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    <tr class="total-row">
                        <td colspan="5" style="text-align: right">Total Amount:</td>
                        <td>$<%= String.format("%.2f", totalAmount) %></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
            
            <div class="action-buttons">
                <a href="MainController?btAction=store" class="btn-continue">Continue Shopping</a>
                <input type="submit" value="update_quantity" name="btAction" class="btn-update" />
                <input type="submit" value="remove_item_from_cart" name="btAction" class="btn-remove" />
            </div>
        </form>

        <form action="MainController" method="POST" style="display: inline;">
            <div class="action-buttons">
                <input type="submit" value="checkout" name="btAction" class="btn-checkout" />
            </div>
        </form>
        <%
                } else {
        %>
        <div style="text-align: center; margin-top: 50px;">
            <h2>Your cart is empty!</h2>
            <p>
                <a href="MainController?btAction=store" class="btn-continue">
                    Start Shopping
                </a>
            </p>
        </div>
        <%
                }
            }
        %>
    </body>
</html>
