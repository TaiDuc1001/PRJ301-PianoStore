<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ducpt.entity.Course" %>
<%@ page import="ducpt.controller.StoreController" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Course Store</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="container">
            <h1>Piano Music Center</h1>
            
            <div class="nav-links">
                <a href="MainController?btAction=search">Search Courses</a>
                <a href="login.jsp">Login</a>
            </div>
            
            <c:if test="${not empty message}">
                <div class="message success">${message}</div>
            </c:if>
            
            <c:choose>
                <c:when test="${not empty requestScope.COURSE_LIST || not empty sessionScope.ALL_COURSES}">
                    <form action="MainController">
                        <h2>Choose Course</h2>
                        <select name="cboItem">
                            <c:forEach items="${not empty requestScope.COURSE_LIST ? requestScope.COURSE_LIST : sessionScope.ALL_COURSES}" var="course">
                                <option value="${course.courseName}">
                                    ${course.courseName} - $${course.tuitionFee} (${course.category})
                                </option>
                            </c:forEach>
                        </select>
                        <br>
                        <input type="submit" value="add_item_to_cart" name="btAction" />
                        <input type="submit" value="view_cart" name="btAction" />
                    </form>
                </c:when>
                <c:otherwise>
                    <p>No courses available at the moment. Please check back later.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html> 