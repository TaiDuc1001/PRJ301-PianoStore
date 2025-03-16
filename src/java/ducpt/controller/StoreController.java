package ducpt.controller;

import ducpt.registration.course.CourseDAO;
import ducpt.entity.Course;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "StoreController", urlPatterns = {"/StoreController"})
public class StoreController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\n=== StoreController Processing ===");
        
        try {
            // Check if courses are already in session
            HttpSession session = request.getSession();
            List<Course> courses = (List<Course>) session.getAttribute("ALL_COURSES");
            System.out.println("Courses in session: " + courses);
            
            // If not in session or empty, fetch from database
            if (courses == null || courses.isEmpty()) {
                System.out.println("Courses not found in session, fetching from database");
                CourseDAO courseDAO = new CourseDAO();
                courses = courseDAO.getAllCourses();
                
                // Save to session if courses were found
                if (courses != null && !courses.isEmpty()) {
                    session.setAttribute("ALL_COURSES", courses);
                    System.out.println("Saved " + courses.size() + " courses to session");
                }
            } else {
                System.out.println("Using " + courses.size() + " courses from session");
            }
            
            // Set for request scope as well
            request.setAttribute("COURSE_LIST", courses);
            
            if (courses == null || courses.isEmpty()) {
                System.out.println("No courses found - setting message");
                request.setAttribute("message", "No courses available. Please add some courses first.");
            } else {
                System.out.println("Found " + courses.size() + " courses");
            }
            
            request.getRequestDispatcher("store.jsp").forward(request, response);
            
        } catch (SQLException e) {
            System.out.println("Database error in StoreController: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("message", "Error loading courses: " + e.getMessage());
            request.getRequestDispatcher("store.jsp").forward(request, response);
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