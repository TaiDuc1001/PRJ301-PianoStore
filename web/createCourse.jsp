<%-- 
    Document   : createCourse
    Created on : Mar 4, 2025, 10:46:38 AM
    Author     : LENOVO-DUCKY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ducpt.registration.course.CourseInsertError"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Course Page</title>
    </head>
    <body>
        <div class="container">
            <h1>Create New Course</h1>
            <a href="MainController?btAction=search" class="btn btn-secondary mb-3">Back to Search</a>
            <form action="MainController" method="POST">
                Course Name: <input type="text" name="txtName" value="" />
                <%
                    CourseInsertError errors = (CourseInsertError) request.getAttribute("INSERTERRORS");
                    if (errors != null) {
                        if (errors.getDuplicateNameErr() != null) {
                %>
                            <font color="red"><%= errors.getDuplicateNameErr() %></font>
                <%
                        }
                        if (errors.getNameLengthErr() != null) {
                %>
                            <font color="red"><%= errors.getNameLengthErr() %></font>
                <%
                        }
                    }
                %>
                <br><br>

                Description: <textarea name="txtDescription" rows="4" cols="50"></textarea>
                <br><br>

                Tuition Fee: <input type="number" step="0.01" name="txtTuitionFee" value="" />
                <%
                    if (errors != null && errors.getTuitionFeeErr() != null) {
                %>
                    <font color="red"><%= errors.getTuitionFeeErr() %></font>
                <%
                    }
                %>
                <br><br>

                Start Date: <input type="date" name="txtStartDate" value="" />
                <%
                    if (errors != null && errors.getDateErr() != null) {
                %>
                    <font color="red"><%= errors.getDateErr() %></font>
                <%
                    }
                %>
                <br><br>

                End Date: <input type="date" name="txtEndDate" value="" />
                <br><br>

                Category: 
                <select name="txtCategory">
                    <option value="Piano">Piano</option>
                    <option value="Guitar">Guitar</option>
                    <option value="Drawing">Drawing</option>
                </select>
                <%
                    if (errors != null && errors.getCategoryErr() != null) {
                %>
                    <font color="red"><%= errors.getCategoryErr() %></font>
                <%
                    }
                %>
                <br><br>

                <input type="submit" value="create_course" name="btAction" />
                <input type="reset" value="reset" />
            </form>
        </div>
    </body>
</html>

