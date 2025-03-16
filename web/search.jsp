<%-- 
    Document   : search
    Created on : Mar 6, 2025, 6:38:03 PM
    Author     : LENOVO-DUCKY
--%>

<%@page import="java.util.List"%>
<%@page import="ducpt.entity.Course"%>
<%@page import="ducpt.controller.SearchController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search Page</title>
</head>
<body>
    <%
        Cookie[] cookies = request.getCookies();
        String username = "";
        boolean isLoggedIn = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                    isLoggedIn = true;
                    break;
                }
            }
        }
    %>

    <% if (isLoggedIn) { %>
        <font color="red">Welcome, <%= username %></font>
        <form action="MainController" method="POST">
            <input type="submit" value="logout" name="btAction" />
        </form>
    <% } else { %>
        <a href="login.jsp">Login</a> | 
        <a href="createCourse.jsp">Create New Course</a>
    <% } %>

    <div class="container">
        <h1>Course Search</h1>
        <a href="createCourse.jsp" class="btn btn-primary mb-3">Create New Course</a>
        <form action="MainController">
            <%
                String searchValue = request.getParameter("txtSearchValue");
            %>
            Course Name: <input type="text" name="txtSearchValue" value="<%= searchValue != null ? searchValue : "" %>" />
            <br>
            <input type="submit" value="search" name="btAction" />
        </form>

        <%
            List<Course> result = (List<Course>) request.getAttribute("SEARCHRESULT");
            if (result != null && !result.isEmpty()) {
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Tuition Fee</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Category</th>
                    <th>Delete</th>
                    <th>Update</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int startIndex = (Integer) request.getAttribute("startIndex");
                    for (Course c : result) {
                %>
                <tr>
                    <form action="MainController">
                        <td><%= ++startIndex %></td>
                        <td>
                            <%= c.getCourseName() %>
                            <input type="hidden" name="txtName" value="<%= c.getCourseName() %>" />
                        </td>
                        <td><input type="text" name="txtDescription" value="<%= c.getDescription() %>" /></td>
                        <td><input type="text" name="txtTuitionFee" value="<%= c.getTuitionFee() %>" /></td>
                        <td><input type="text" name="txtStartDate" value="<%= c.getStartDate() %>" /></td>
                        <td><input type="text" name="txtEndDate" value="<%= c.getEndDate() %>" /></td>
                        <td><input type="text" name="txtCategory" value="<%= c.getCategory() %>" /></td>
                        <td>
                            <a href="MainController?btAction=del&pk=<%= java.net.URLEncoder.encode(c.getCourseName(), "UTF-8") %>&lastSearchValue=<%= java.net.URLEncoder.encode(searchValue != null ? searchValue : "", "UTF-8") %>">Delete</a>
                        </td>
                        <td>
                            <input type="hidden" name="lastSearchValue" value="<%= searchValue %>" />
                            <input type="submit" value="update" name="btAction" />
                        </td>
                    </form>
                </tr>
                <% } %>
            </tbody>
        </table>

        <%
            Integer currentPage = (Integer) request.getAttribute("currentPage");
            Integer totalPages = (Integer) request.getAttribute("totalPages");
            if (currentPage != null && totalPages != null) {
        %>
        <div class="pagination">
            <% if (currentPage > 1) { %>
                <a href="MainController?btAction=search&page=<%= currentPage - 1 %>&txtSearchValue=<%= searchValue != null ? searchValue : "" %>">Previous</a>
            <% } %>
            
            <% for (int i = 1; i <= totalPages; i++) { %>
                <% if (i == currentPage) { %>
                    <span><%= i %></span>
                <% } else { %>
                    <a href="MainController?btAction=search&page=<%= i %>&txtSearchValue=<%= searchValue != null ? searchValue : "" %>"><%= i %></a>
                <% } %>
            <% } %>
            
            <% if (currentPage < totalPages) { %>
                <a href="MainController?btAction=search&page=<%= currentPage + 1 %>&txtSearchValue=<%= searchValue != null ? searchValue : "" %>">Next</a>
            <% } %>
        </div>
        <% } %>

        <% } else if (result != null) { %>
            <h2>No record is matched!</h2>
        <% } %>

        <a href="MainController?btAction=store">Go to Piano Music Center</a>
    </div>
</body>
</html>
