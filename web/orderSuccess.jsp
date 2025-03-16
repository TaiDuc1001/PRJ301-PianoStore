<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Success</title>
        <style>
            .success-container {
                text-align: center;
                margin-top: 50px;
            }
            .success-message {
                color: #4CAF50;
                font-size: 24px;
                margin-bottom: 20px;
            }
            .continue-shopping {
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 4px;
                display: inline-block;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <div class="success-container">
            <h1 class="success-message">Order Completed Successfully!</h1>
            <p>Thank you for your purchase. You will receive a confirmation email shortly.</p>
            <a href="MainController?btAction=store" class="continue-shopping">Continue Shopping</a>
        </div>
    </body>
</html> 