<%-- 
    Document   : login
    Created on : Mar 16, 2025, 12:57:30 PM
    Author     : LENOVO-DUCKY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Login Page</h1>
        <form action="MainController" method="POST">
            <%
            String errorMessage = (String) request.getAttribute("ERROR_MESSAGE");
            if(errorMessage != null) {
            %>
            <font color="red"><%= errorMessage %></font>
            <%
                }
            %>
            <br>
            Username: <input type="text" name="txtUserName" value="" /><br>
            Password: <input type="password" name="txtPassWord" value="" /><br>
            <input type="submit" value="login" name="btAction" />
        </form>
        <a href="createCourse.jsp">Create New Course</a>
    </body>
</html>
